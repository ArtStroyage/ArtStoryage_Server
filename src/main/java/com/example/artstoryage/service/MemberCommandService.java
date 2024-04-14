package com.example.artstoryage.service;

import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.dto.request.MemberRequestDto;
import com.example.artstoryage.dto.request.MemberRequestDto.SignUpMemberRequest;
import com.example.artstoryage.dto.response.MemberResponseDto;

public interface MemberCommandService {

  Member signUpMember(SignUpMemberRequest request);

  MemberResponseDto.TokenResponse login(MemberRequestDto.LoginMemberRequest request);
}
