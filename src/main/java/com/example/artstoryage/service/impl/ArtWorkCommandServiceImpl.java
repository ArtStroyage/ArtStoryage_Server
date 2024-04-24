package com.example.artstoryage.service.impl;

import jakarta.transaction.Transactional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.artstoryage.converter.ArtWorkConverter;
import com.example.artstoryage.domain.ArtWork;
import com.example.artstoryage.dto.request.ArtWorkRequestDto.*;
import com.example.artstoryage.repository.ArtWorkRepository;
import com.example.artstoryage.repository.MemberRepository;
import com.example.artstoryage.security.domain.MemberDetails;
import com.example.artstoryage.service.ArtWorkCommandService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ArtWorkCommandServiceImpl implements ArtWorkCommandService {

  private final ArtWorkRepository artWorkRepository;
  private final MemberRepository memberRepository;

  @Override
  public ArtWork regArtWork(RegArtWorkRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();
    String email = memberDetails.getUsername();

    return artWorkRepository.save(ArtWorkConverter.toArtWork(request, artist));
  }
}
