package com.example.artstoryage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.artstoryage.domain.Term;

public interface TermRepository extends JpaRepository<Term, Long> {}
