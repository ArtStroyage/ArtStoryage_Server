package com.example.artstoryage.service.impl;

import java.util.Optional;

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

  @Override
  public Optional<Member> findMemberByNameAndPhoneNumber(String name, String phoneNumber) {
    return memberRepository.findByNameAndPhoneNumber(name, phoneNumber);
  }

  @Override
  public Optional<Member> findMemberByNameAndEmailAndPhoneNumber(
      String name, String email, String phoneNumber) {
    return memberRepository.findByNameAndEmailAndPhoneNumber(name, email, phoneNumber);
  }
}
