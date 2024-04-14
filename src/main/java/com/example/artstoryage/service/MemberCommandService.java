package com.example.artstoryage.service;

import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.dto.request.MemberRequestDto.SignUpMemberRequest;

public interface MemberCommandService {

  Member signUpMember(SignUpMemberRequest request);
}
