package com.example.artstoryage.service.impl;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.artstoryage.converter.ArtWorkConverter;
import com.example.artstoryage.domain.ArtWork;
import com.example.artstoryage.domain.Artist;
import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.dto.request.ArtWorkRequestDto.*;
import com.example.artstoryage.exception.GlobalErrorCode;
import com.example.artstoryage.exception.custom.MemberException;
import com.example.artstoryage.repository.ArtWorkRepository;
import com.example.artstoryage.service.ArtWorkCommandService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ArtWorkCommandServiceImpl implements ArtWorkCommandService {

  private final ArtWorkRepository artWorkRepository;

  @Override
  public ArtWork regArtWork(Member member, RegArtWorkRequest request) {
    if (member.getArtist() != null) {
      Artist artist = member.getArtist();
      return artWorkRepository.save(ArtWorkConverter.toArtWork(request, artist));
    }
    throw new MemberException(GlobalErrorCode.MEMBER_INFO_NOT_FOUND);
  }
}
