package com.example.artstoryage.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.artstoryage.domain.ArtWork;
import com.example.artstoryage.exception.GlobalErrorCode;
import com.example.artstoryage.exception.custom.ArtWorkException;
import com.example.artstoryage.repository.ArtWorkRepository;
import com.example.artstoryage.service.ArtWorkQueryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArtWorkQueryServiceImpl implements ArtWorkQueryService {

  private final ArtWorkRepository artWorkRepository;

  @Override
  public ArtWork getArtWork(Long artWorkId) {
    return artWorkRepository
        .findById(artWorkId)
        .orElseThrow(() -> new ArtWorkException(GlobalErrorCode.ARTWORK_NOT_FOUND));
  }

  @Override
  public List<ArtWork> getArtWorksByArtist(Long artistId) {
    final List<ArtWork> artWorkList = artWorkRepository.findByArtistId(artistId);

    if (artWorkList.isEmpty()) {
      throw new ArtWorkException(GlobalErrorCode.ARTWORK_NOT_FOUND);
    }

    return artWorkList;
  }

  @Override
  public List<ArtWork> getApprovedArtWorksByArtist(Long artistId) {
    final List<ArtWork> artWorkList = artWorkRepository.findByArtistIdAndIsRegTrue(artistId);

    if (artWorkList.isEmpty()) {
      throw new ArtWorkException(GlobalErrorCode.ARTWORK_NOT_FOUND);
    }

    return artWorkList;
  }

  @Override
  public List<ArtWork> getArtWorksByContainsKeyWord(String keyword) {
    List<ArtWork> artWorkList = artWorkRepository.findByTitleContains(keyword);

    if (artWorkList.isEmpty()) {
      throw new ArtWorkException(GlobalErrorCode.ARTWORK_NOT_FOUND);
    }

    return artWorkList;
  }

  @Override
  public List<ArtWork> getApprovedArtWorksByContainsKeyWord(String keyword) {
    List<ArtWork> artWorkList = artWorkRepository.findByTitleContainsAndIsRegTrue(keyword);

    if (artWorkList.isEmpty()) {
      throw new ArtWorkException(GlobalErrorCode.ARTWORK_NOT_FOUND);
    }

    return artWorkList;
  }
}
