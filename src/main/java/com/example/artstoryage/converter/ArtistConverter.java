package com.example.artstoryage.converter;

import org.springframework.stereotype.Component;

import com.example.artstoryage.domain.Artist;
import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.dto.request.ArtistRequestDto.*;
import com.example.artstoryage.dto.response.ArtistResponseDto.*;

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

  public static GetArtistResponse toGetArtistResponse(Artist artist) {
    return GetArtistResponse.builder()
        .artistId(artist.getId())
        .name(artist.getName())
        .profileImageLink(artist.getProfileImageLink())
        .genre(artist.getGenre())
        .introduction(artist.getIntroduction())
        .history(artist.getHistory())
        .artistUrls(artist.getArtistUrls())
        .build();
  }
}
