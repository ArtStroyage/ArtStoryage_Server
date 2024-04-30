package com.example.artstoryage.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.artstoryage.domain.Artist;
import com.example.artstoryage.domain.member.Member;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
  Optional<Artist> findByMember(Member member);
}
