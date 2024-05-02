package com.example.artstoryage.service;

import com.example.artstoryage.domain.Artist;

public interface ArtistQueryService {
  Artist getArtist(Long artistId);
}
