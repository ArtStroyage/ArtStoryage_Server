package com.example.artstoryage.service;

import com.example.artstoryage.domain.Artist;
import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.dto.request.ArtistRequestDto.CreateArtistRequest;

public interface ArtistCommandService {

  Artist createArtist(Member member, CreateArtistRequest request);
}
