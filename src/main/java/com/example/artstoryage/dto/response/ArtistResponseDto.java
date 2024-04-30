package com.example.artstoryage.dto.response;

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
}
