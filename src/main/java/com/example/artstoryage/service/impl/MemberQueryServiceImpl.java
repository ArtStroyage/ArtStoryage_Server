package com.example.artstoryage.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.exception.GlobalErrorCode;
import com.example.artstoryage.exception.custom.MemberException;
import com.example.artstoryage.repository.MemberRepository;
import com.example.artstoryage.service.MemberQueryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryServiceImpl implements MemberQueryService {
  private final MemberRepository memberRepository;

  @Override
  public Member findMemberById(Long memberId) {
    return memberRepository
        .findById(memberId)
        .orElseThrow(() -> new MemberException(GlobalErrorCode.MEMBER_NOT_FOUND));
  }

  @Override
  public Boolean isDuplicateEmail(String email) {
    return !memberRepository.findByEmail(email).isPresent();
  }

  @Override
  public Boolean isDuplicateNickName(String nickName) {
    return !memberRepository.findByNickName(nickName).isPresent();
  }
}
