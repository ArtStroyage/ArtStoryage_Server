package com.example.artstoryage.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.artstoryage.converter.MemberConverter;
import com.example.artstoryage.domain.Term;
import com.example.artstoryage.domain.mapping.MemberTerm;
import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.domain.member.Password;
import com.example.artstoryage.dto.request.MemberRequestDto.*;
import com.example.artstoryage.dto.response.MemberResponseDto.FindEmailResponse;
import com.example.artstoryage.dto.response.MemberResponseDto.TokenResponse;
import com.example.artstoryage.exception.GlobalErrorCode;
import com.example.artstoryage.exception.custom.MemberException;
import com.example.artstoryage.exception.custom.TermException;
import com.example.artstoryage.oAuth.*;
import com.example.artstoryage.repository.MemberRepository;
import com.example.artstoryage.repository.MemberTermRepository;
import com.example.artstoryage.repository.TermRepository;
import com.example.artstoryage.security.provider.JwtAuthProvider;
import com.example.artstoryage.service.MemberCommandService;
import com.example.artstoryage.service.MemberQueryService;
import com.example.artstoryage.util.RedisUtil;

import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandServiceImpl implements MemberCommandService {

  private final MemberRepository memberRepository;
  private final TermRepository termRepository;
  private final MemberTermRepository memberTermRepository;
  private final JwtAuthProvider jwtAuthProvider;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final RedisTemplate<String, String> redisTemplate;
  private final AuthTokenGenerator authTokensGenerator;
  private final RequestOAuthInfoService requestOAuthInfoService;
  private final DefaultMessageService defaultMessageService;
  private final RedisUtil redisUtil;
  private final MemberQueryService memberQueryService;

  @Value("${jwt.refresh-token-validity}")
  private Long refreshTokenValidityMilliseconds;

  @Value("${virtual.password}")
  private String password;

  @Value("${coolsms.fromnumber}")
  private String phoneNumber;

  @Override
  public Member signUpMember(SignUpMemberRequest request) {
    List<Long> requiredTerms = Arrays.asList(1L, 2L);

    if (!request.getAgreedTerms().containsAll(requiredTerms)) {
      throw new TermException(GlobalErrorCode.NOT_AGREED_TERMS);
    }

    Member member = memberRepository.save(MemberConverter.toMember(request));

    List<Term> agreedTerms = termRepository.findAllById(request.getAgreedTerms());

    List<MemberTerm> memberTerms = MemberConverter.toMemberTermList(agreedTerms, member);

    memberTermRepository.saveAll(memberTerms);

    member.setMemberTerms(memberTerms);

    return member;
  }

  @Override
  public TokenResponse login(LoginMemberRequest request) {
    Member member =
        memberRepository
            .findByEmail(request.getEmail())
            .orElseThrow(() -> new MemberException(GlobalErrorCode.MEMBER_NOT_FOUND));

    if (!(member.getPassword().isSamePassword(request.getPassword(), bCryptPasswordEncoder))) {
      throw new MemberException(GlobalErrorCode.PASSWORD_MISMATCH);
    }

    String accessToken = jwtAuthProvider.generateAccessToken(member.getId());
    String refreshToken = jwtAuthProvider.generateRefreshToken(member.getId());

    return MemberConverter.toLoginMemberResponse(member.getId(), accessToken, refreshToken);
  }

  @Override
  public TokenResponse reissue(ReissueRequest request) {
    String refreshToken = request.getRefreshToken();

    Long memberId = jwtAuthProvider.parseRefreshToken(refreshToken);

    if (!refreshToken.equals(redisTemplate.opsForValue().get(memberId.toString()))) {
      throw new MemberException(GlobalErrorCode.INVALID_TOKEN);
    }

    Member member =
        memberRepository
            .findById(memberId)
            .orElseThrow(() -> new MemberException(GlobalErrorCode.MEMBER_NOT_FOUND));

    String newAccessToken = jwtAuthProvider.generateAccessToken(member.getId());
    String newRefreshToken = jwtAuthProvider.generateRefreshToken(member.getId());

    redisTemplate
        .opsForValue()
        .set(
            member.getId().toString(),
            newRefreshToken,
            refreshTokenValidityMilliseconds,
            TimeUnit.MILLISECONDS);

    return MemberConverter.toReissueResponse(memberId, newAccessToken, newRefreshToken);
  }

  @Override
  public Member createOrGetSocialMember(OAuthInfoResponse oAuthInfoResponse) {
    List<Long> termList = Arrays.asList(1L, 2L);

    // ToDO - 나중에 카카오, 네이버에서 비즈앱 승인하면 이메일로 검색
    Optional<Member> findMember = memberRepository.findByNickName(oAuthInfoResponse.getNickname());
    if (findMember.isPresent()) {
      return findMember.get();
    } else {
      return signUpMember(
          MemberConverter.toMemberSocialRequest(oAuthInfoResponse, termList, password));
    }
  }

  @Override
  public AuthToken loginSoical(OAuthLoginParams params) {
    OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
    Long userId = createOrGetSocialMember(oAuthInfoResponse).getId();
    return authTokensGenerator.generate(userId);
  }

  @Override
  public SingleMessageSentResponse sendMessage(PhoneNumberRequest request) {

    if (redisUtil.hasKey(request.getPhoneNumber())) {
      redisUtil.deleteSmsCertification(request.getPhoneNumber());
    }

    String randomNum = redisUtil.generateRandomNumber(6);

    Message message = new Message();
    message.setFrom(phoneNumber);
    message.setTo(request.getPhoneNumber());
    message.setText("[ArtStroyage]" + "인증번호는 " + randomNum + "입니다.");

    SingleMessageSentResponse response =
        this.defaultMessageService.sendOne(new SingleMessageSendingRequest(message));

    redisUtil.createSmsCertification(request.getPhoneNumber(), randomNum);

    return response;
  }

  @Override
  public Optional<Member> isVerifyNumber(VerifyPhoneNumberRequest request) {
    if (!((redisUtil.hasKey(request.getPhoneNumber()))
        && redisUtil.getSmsCertification(request.getPhoneNumber()).equals(request.getCode()))) {

      throw new MemberException(GlobalErrorCode.NUMBER_NOT_MATCH);
    }

    redisUtil.deleteSmsCertification(request.getPhoneNumber());

    return memberQueryService.findMemberByPhoneNumber(request.getPhoneNumber());
  }

  @Override
  public SingleMessageSentResponse findEmailCodeSender(
      FindEmailByNameAndPhoneNumberRequest request) {
    if (memberQueryService
        .findMemberByNameAndPhoneNumber(request.getName(), request.getPhoneNumber())
        .isPresent()) {
      return sendMessage(MemberConverter.toPhoneNumberRequest(request.getPhoneNumber()));
    }

    throw new MemberException(GlobalErrorCode.MEMBER_NOT_FOUND);
  }

  @Override
  public FindEmailResponse findEmail(Optional<Member> member) {
    if (member.isPresent()) {
      return MemberConverter.toFindEmailResponse(member.get().getEmail());
    }
    throw new MemberException(GlobalErrorCode.VERIFIED_NOT_DONE);
  }

  @Override
  public SingleMessageSentResponse findPasswordCodeSender(
      FindPasswordByNameAndEmailAndPhoneNumberRequest request) {
    if (memberQueryService
        .findMemberByNameAndEmailAndPhoneNumber(
            request.getName(), request.getEmail(), request.getPhoneNumber())
        .isPresent()) {
      return sendMessage(MemberConverter.toPhoneNumberRequest(request.getPhoneNumber()));
    }
    throw new MemberException(GlobalErrorCode.MEMBER_NOT_FOUND);
  }

  @Override
  public String isVerifiedNumber(VerifyCodeRequest request) {
    if (!((redisUtil.hasKey(request.getEmail())))
        && redisUtil.getEmailCertification(request.getEmail()).equals(request.getCode())) {
      throw new MemberException(GlobalErrorCode.NUMBER_NOT_MATCH);
    }

    redisUtil.deleteEmailCertification(request.getEmail());

    Member member =
        memberRepository
            .findByEmail(request.getEmail())
            .orElseThrow(() -> new MemberException(GlobalErrorCode.MEMBER_NOT_FOUND));

    String token = UUID.randomUUID().toString();

    redisUtil.createFindPasswordToken(token, member);
    return token;
  }

  @Override
  public Member findPassword(String token, ChangePasswordRequest request) {

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    Member member =
        memberRepository
            .findByEmail(redisUtil.getMemberByToken(token).trim())
            .orElseThrow(() -> new MemberException(GlobalErrorCode.MEMBER_NOT_FOUND));

    redisUtil.deleteFindPasswordToken(member.getEmail());

    member.setPassword(Password.encrypt(request.getPassword(), encoder));

    return memberRepository.save(member);
  }
}
