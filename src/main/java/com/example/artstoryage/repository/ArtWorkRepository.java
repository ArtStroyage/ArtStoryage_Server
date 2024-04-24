package com.example.artstoryage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.artstoryage.domain.ArtWork;

public interface ArtWorkRepository extends JpaRepository<ArtWork, Long> {}
