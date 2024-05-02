package com.example.artstoryage.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
import com.example.artstoryage.dto.request.MemberRequestDto.LoginMemberRequest;
import com.example.artstoryage.dto.request.MemberRequestDto.ReissueRequest;
import com.example.artstoryage.dto.request.MemberRequestDto.SignUpMemberRequest;
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

import lombok.RequiredArgsConstructor;

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

  @Value("${jwt.refresh-token-validity}")
  private Long refreshTokenValidityMilliseconds;

  @Value("${virtual.password}")
  private String password;

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
  public Member createOrGetKakaoMember(OAuthInfoResponse oAuthInfoResponse) {
    List<Long> termList = Arrays.asList(1L, 2L);

    // ToDO - 나중에 카카오에서 비즈앱 승인하면 이메일로 검색
    Optional<Member> findMember = memberRepository.findByNickName(oAuthInfoResponse.getNickname());
    if (findMember.isPresent()) {
      return findMember.get();
    } else {
      return signUpMember(
          MemberConverter.toMemberKakaoRequest(oAuthInfoResponse, termList, password));
    }
  }

  @Override
  public AuthToken loginKakao(OAuthLoginParams params) {
    OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
    Long userId = createOrGetKakaoMember(oAuthInfoResponse).getId();
    return authTokensGenerator.generate(userId);
  }
}
