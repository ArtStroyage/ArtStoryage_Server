package com.example.artstoryage.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.artstoryage.converter.MemberConverter;
import com.example.artstoryage.domain.Term;
import com.example.artstoryage.domain.mapping.MemberTerm;
import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.dto.request.MemberRequestDto.SignUpMemberRequest;
import com.example.artstoryage.exception.GlobalErrorCode;
import com.example.artstoryage.exception.custom.TermException;
import com.example.artstoryage.repository.MemberRepository;
import com.example.artstoryage.repository.MemberTermRepository;
import com.example.artstoryage.repository.TermRepository;
import com.example.artstoryage.service.MemberCommandService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandServiceImpl implements MemberCommandService {

  private final MemberRepository memberRepository;
  private final TermRepository termRepository;
  private final MemberTermRepository memberTermRepository;

  @Override
  public Member signUpMember(SignUpMemberRequest request) {
    List<Long> requiredTerms = Arrays.asList(1L, 2L);

    if (!request.getAgreedTerms().containsAll(requiredTerms)) {
      throw new TermException(GlobalErrorCode.NOT_AGREED_TERMS);
    }

    Member member = memberRepository.save(MemberConverter.toMember(request));

    List<Term> agreedTerms = termRepository.findAllById(request.getAgreedTerms());

    List<MemberTerm> memberTerms = MemberConverter.toMemberTermList(agreedTerms, member);

    memberTermRepository.saveAll(memberTerms);

    member.setMemberTerms(memberTerms);

    return member;
  }
}
