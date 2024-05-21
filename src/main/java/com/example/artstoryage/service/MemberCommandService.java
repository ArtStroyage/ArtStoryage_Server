package com.example.artstoryage.service;

import java.util.Optional;

import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.dto.request.MemberRequestDto.*;
import com.example.artstoryage.dto.request.MemberRequestDto.SignUpMemberRequest;
import com.example.artstoryage.dto.response.MemberResponseDto.*;
import com.example.artstoryage.oAuth.AuthToken;
import com.example.artstoryage.oAuth.OAuthInfoResponse;
import com.example.artstoryage.oAuth.OAuthLoginParams;

import net.nurigo.sdk.message.response.SingleMessageSentResponse;

public interface MemberCommandService {

  Member signUpMember(SignUpMemberRequest request);

  TokenResponse login(LoginMemberRequest request);

  TokenResponse reissue(ReissueRequest request);

  Member createOrGetSocialMember(OAuthInfoResponse oAuthInfoResponse);

  AuthToken loginSoical(OAuthLoginParams params);

  SingleMessageSentResponse sendMessage(PhoneNumberRequest request);

  Optional<Member> isVerifyNumber(VerifyPhoneNumberRequest request);

  SingleMessageSentResponse findEmailCodeSender(FindEmailByNameAndPhoneNumberRequest request);

  FindEmailResponse findEmail(Optional<Member> member);

  SingleMessageSentResponse findPasswordCodeSender(
      FindPasswordByNameAndEmailAndPhoneNumberRequest request);

  String findPassword(Optional<Member> member, ChangePasswordRequest request);
}
