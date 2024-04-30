package com.example.artstoryage.service.impl;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.artstoryage.converter.ArtWorkConverter;
import com.example.artstoryage.domain.ArtWork;
import com.example.artstoryage.domain.Artist;
import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.dto.request.ArtWorkRequestDto.*;
import com.example.artstoryage.exception.GlobalErrorCode;
import com.example.artstoryage.exception.custom.ArtWorkException;
import com.example.artstoryage.exception.custom.ArtistException;
import com.example.artstoryage.repository.ArtWorkRepository;
import com.example.artstoryage.repository.ArtistRepository;
import com.example.artstoryage.service.ArtWorkCommandService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ArtWorkCommandServiceImpl implements ArtWorkCommandService {

  private final ArtWorkRepository artWorkRepository;
  private final ArtistRepository artistRepository;

  @Override
  public ArtWork regArtWork(Member member, RegArtWorkRequest request) {
    Artist artist = artistRepository.findByMemberId(member.getId());

    if (artist != null) {
      return artWorkRepository.save(ArtWorkConverter.toArtWork(request, artist));
    }
    throw new ArtistException(GlobalErrorCode.ARTIST_NOT_FOUND);
  }

  @Override
  public void deleteArtWork(Long artWorkId) {
    if (!artWorkRepository.existsById(artWorkId)) {
      throw new ArtWorkException(GlobalErrorCode.ARTWORK_NOT_FOUND);
    }

    artWorkRepository.deleteById(artWorkId);
  }
}
