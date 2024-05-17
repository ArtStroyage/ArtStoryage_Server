package com.example.artstoryage.service;

import com.example.artstoryage.domain.member.Member;

public interface MemberQueryService {
  Member findMemberById(Long memberId);

  Boolean isDuplicateNickName(String nickName);
}
