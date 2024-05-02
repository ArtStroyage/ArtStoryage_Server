package com.example.artstoryage.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.artstoryage.domain.Artist;
import com.example.artstoryage.exception.GlobalErrorCode;
import com.example.artstoryage.exception.custom.ArtistException;
import com.example.artstoryage.repository.ArtistRepository;
import com.example.artstoryage.service.ArtistQueryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArtistQueryServiceImpl implements ArtistQueryService {

  private final ArtistRepository artistRepository;

  @Override
  public Artist getArtist(Long artistId) {
    return artistRepository
        .findById(artistId)
        .orElseThrow(() -> new ArtistException(GlobalErrorCode.ARTIST_NOT_FOUND));
  }
}
