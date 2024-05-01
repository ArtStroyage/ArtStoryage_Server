package com.example.artstoryage.service;

import java.util.List;

import com.example.artstoryage.domain.ArtWork;

public interface ArtWorkQueryService {
  List<ArtWork> getArtWorksByArtist(Long artistId);
}
