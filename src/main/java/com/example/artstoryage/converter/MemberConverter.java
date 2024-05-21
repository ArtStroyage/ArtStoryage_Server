package com.example.artstoryage.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.artstoryage.domain.Term;
import com.example.artstoryage.domain.enums.MemberRole;
import com.example.artstoryage.domain.enums.SocialType;
import com.example.artstoryage.domain.mapping.MemberTerm;
import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.domain.member.Password;
import com.example.artstoryage.dto.request.MemberRequestDto.PhoneNumberRequest;
import com.example.artstoryage.dto.request.MemberRequestDto.SignUpMemberRequest;
import com.example.artstoryage.dto.response.MemberResponseDto.FindEmailResponse;
import com.example.artstoryage.dto.response.MemberResponseDto.SignUpMemberResponse;
import com.example.artstoryage.dto.response.MemberResponseDto.TokenResponse;
import com.example.artstoryage.oAuth.AuthToken;
import com.example.artstoryage.oAuth.OAuthInfoResponse;

@Component
public class MemberConverter {

  public static Member toMember(SignUpMemberRequest request) {

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    return Member.builder()
        .name(request.getName())
        .nickName(request.getNickName())
        .email(request.getEmail())
        .password(Password.encrypt(request.getPassword(), encoder))
        .phoneNumber(request.getPhoneNumber())
        .socialType(SocialType.COMMON)
        .memberRole(MemberRole.USER)
        .build();
  }

  public static SignUpMemberResponse toSignUpMemberResponse(Member member) {
    return SignUpMemberResponse.builder()
        .memberId(member.getId())
        .name(member.getName())
        .email(member.getEmail())
        .build();
  }

  public static List<MemberTerm> toMemberTermList(List<Term> agreeTerms, Member member) {
    return agreeTerms.stream()
        .map(term -> MemberConverter.toMemberTerm(member, term))
        .collect(Collectors.toList());
  }

  public static MemberTerm toMemberTerm(Member member, Term term) {
    return MemberTerm.builder().member(member).term(term).build();
  }

  public static TokenResponse toLoginMemberResponse(
      Long memberId, String accessToken, String refreshToken) {
    return TokenResponse.builder()
        .memberId(memberId)
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

  public static TokenResponse toReissueResponse(
      Long memberId, String newAccessToken, String newRefreshToken) {
    return TokenResponse.builder()
        .memberId(memberId)
        .accessToken(newAccessToken)
        .refreshToken(newRefreshToken)
        .build();
  }

  public static TokenResponse toSocialLogin(AuthToken authToken) {
    return TokenResponse.builder()
        .accessToken(authToken.getAccessToken())
        .refreshToken(authToken.getRefreshToken())
        .build();
  }

  // ToDo - 나중에 비즈앱으로 전환 시 다른 Member 요소 추가
  public static SignUpMemberRequest toMemberSocialRequest(
      OAuthInfoResponse oAuthInfoResponse, List<Long> termList, String password) {
    return SignUpMemberRequest.builder()
        .nickName(oAuthInfoResponse.getNickname())
        .password(password)
        .agreedTerms(termList)
        .build();
  }

  public static PhoneNumberRequest toPhoneNumberRequest(String phoneNumber) {
    return PhoneNumberRequest.builder().phoneNumber(phoneNumber).build();
  }

  public static FindEmailResponse toFindEmailResponse(String email) {
    return FindEmailResponse.builder().email(email).build();
  }
}
