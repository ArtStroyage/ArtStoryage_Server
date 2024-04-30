package com.example.artstoryage.dto.response;

import lombok.*;

public class ArtWorkResponseDto {

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class RegArtWorkResponse {
    Long artWorkId;
    String title;
    boolean isReg;
  }

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class AllowArtWorkResponse {
    String title;
    boolean isReg;
  }
}
