package com.example.artstoryage.service;

import java.util.Optional;

import com.example.artstoryage.domain.member.Member;

public interface MemberQueryService {
  Member findMemberById(Long memberId);

  Boolean isDuplicateEmail(String email);

  Boolean isDuplicateNickName(String nickName);

  Optional<Member> findMemberByNameAndPhoneNumber(String name, String phoneNumber);

  Optional<Member> findMemberByNameAndEmailAndPhoneNumber(
      String name, String email, String phoneNumber);
}
