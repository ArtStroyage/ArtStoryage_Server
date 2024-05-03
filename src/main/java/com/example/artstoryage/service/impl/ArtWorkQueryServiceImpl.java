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
  public List<ArtWork> getArtWorksByArtist(Long artistId) {
    final List<ArtWork> artWorkList = artWorkRepository.findByArtistId(artistId);

    if (artWorkList.isEmpty()) {
      throw new ArtWorkException(GlobalErrorCode.ARTWORK_NOT_FOUND);
    }

    return artWorkList;
  }

  @Override
  public ArtWork getApprovedArtWork(Long artWorkId) {
    ArtWork artWork =
        artWorkRepository
            .findById(artWorkId)
            .orElseThrow(() -> new ArtWorkException(GlobalErrorCode.ARTWORK_NOT_FOUND));

    if (!artWork.getIsReg()) {
      throw new ArtWorkException(GlobalErrorCode.ARTWORK_NOT_APPROVED);
    }

    return artWork;
  }
}
