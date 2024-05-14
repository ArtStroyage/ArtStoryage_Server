package com.example.artstoryage.service;

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

  Boolean isVerifyNumber(VerifyPhoneNumberRequest request);
}
