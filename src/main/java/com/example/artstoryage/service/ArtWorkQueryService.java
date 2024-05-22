package com.example.artstoryage.service;

import java.util.List;

import com.example.artstoryage.domain.ArtWork;

public interface ArtWorkQueryService {
  ArtWork getArtWork(Long artWorkId);

  List<ArtWork> getArtWorksByArtist(Long artWorkId);

  List<ArtWork> getApprovedArtWorksByArtist(Long artistId);

  List<ArtWork> getArtWorksByContainsKeyWord(String keyword);

  List<ArtWork> getApprovedArtWorksByContainsKeyWord(String keyword);
}
