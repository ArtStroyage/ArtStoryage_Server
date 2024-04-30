package com.example.artstoryage.service;

import java.util.List;

import com.example.artstoryage.domain.ArtWork;
import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.dto.request.ArtWorkRequestDto.*;

public interface ArtWorkCommandService {

  ArtWork regArtWork(Member member, RegArtWorkRequest request);

  ArtWork allowArtWork(Long artWorkId);

  List<ArtWork> getArtWorksByArtist(Long artistId);

  void deleteArtWork(Long artWorkId);
}
