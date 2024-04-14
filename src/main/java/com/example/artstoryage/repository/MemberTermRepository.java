package com.example.artstoryage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.artstoryage.domain.mapping.MemberTerm;

public interface MemberTermRepository extends JpaRepository<MemberTerm, Long> {}
