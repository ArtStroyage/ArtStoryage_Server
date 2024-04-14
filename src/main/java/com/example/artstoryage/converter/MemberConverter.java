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
import com.example.artstoryage.dto.request.MemberRequestDto.SignUpMemberRequest;
import com.example.artstoryage.dto.response.MemberResponseDto.SignUpMemberResponse;
import com.example.artstoryage.dto.response.MemberResponseDto.TokenResponse;

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
        .socialType(SocialType.Common)
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
}