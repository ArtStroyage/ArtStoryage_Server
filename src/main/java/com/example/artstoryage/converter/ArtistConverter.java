package com.example.artstoryage.converter;

import org.springframework.stereotype.Component;

import com.example.artstoryage.domain.Artist;
import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.dto.request.ArtistRequestDto.CreateArtistRequest;
import com.example.artstoryage.dto.response.ArtistResponseDto.CreateArtistResponse;

@Component
public class ArtistConverter {

  public static Artist toArtist(CreateArtistRequest request, Member member) {
    return Artist.builder()
        .name(request.getName())
        .profileImageLink(request.getProfileImageLink())
        .genre(request.getGenre())
        .introduction(request.getIntroduction())
        .history(request.getHistory())
        .bankBookLink(request.getBankBookLink())
        .member(member)
        .build();
  }

  public static CreateArtistResponse toCreateArtistResponse(Artist artist) {
    return CreateArtistResponse.builder().artistId(artist.getId()).name(artist.getName()).build();
  }
}
