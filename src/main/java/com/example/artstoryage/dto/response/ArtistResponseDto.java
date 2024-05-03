package com.example.artstoryage.dto.response;

import java.util.List;

import com.example.artstoryage.domain.ArtistUrl;

import lombok.*;

public class ArtistResponseDto {

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class CreateArtistResponse {
    Long artistId;
    String name;
  }

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class GetArtistResponse {
    Long artistId;
    String name;
    String profileImageLink;
    String genre;
    String introduction;
    String history;
    List<ArtistUrl> artistUrls;
  }
}
