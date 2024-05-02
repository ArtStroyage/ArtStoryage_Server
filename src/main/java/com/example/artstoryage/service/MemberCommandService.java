package com.example.artstoryage.service;

import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.dto.request.MemberRequestDto.*;
import com.example.artstoryage.dto.request.MemberRequestDto.SignUpMemberRequest;
import com.example.artstoryage.dto.response.MemberResponseDto.*;
import com.example.artstoryage.oAuth.AuthToken;
import com.example.artstoryage.oAuth.OAuthInfoResponse;
import com.example.artstoryage.oAuth.OAuthLoginParams;

public interface MemberCommandService {

  Member signUpMember(SignUpMemberRequest request);

  TokenResponse login(LoginMemberRequest request);

  TokenResponse reissue(ReissueRequest request);

  Member createOrGetKakaoMember(OAuthInfoResponse oAuthInfoResponse);

  AuthToken loginKakao(OAuthLoginParams params);
}
