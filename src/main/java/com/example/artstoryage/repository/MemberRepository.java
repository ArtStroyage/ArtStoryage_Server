package com.example.artstoryage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.artstoryage.domain.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {}
