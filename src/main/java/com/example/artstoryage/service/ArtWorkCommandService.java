package com.example.artstoryage.service;

import com.example.artstoryage.domain.ArtWork;
import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.dto.request.ArtWorkRequestDto.*;

public interface ArtWorkCommandService {

  ArtWork regArtWork(Member member, RegArtWorkRequest request);

  ArtWork allowArtWork(Long artWorkId);

  void deleteArtWork(Long artWorkId);
}
