package com.example.artstoryage.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.artstoryage.domain.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByEmail(String email);

  Optional<Member> findByNickName(String nickName);

  Optional<Member> findByNameAndPhoneNumber(String name, String phoneNumber);

  Optional<Member> findByNameAndEmailAndPhoneNumber(String name, String email, String phoneNumber);

  Optional<Member> findByPhoneNumber(String phoneNumber);
}
