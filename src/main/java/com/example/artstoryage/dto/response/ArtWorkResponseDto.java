package com.example.artstoryage.dto.response;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.*;

public class ArtWorkResponseDto {

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class ArtWorkResponse {
    Long artWorkId;
    String imageLink;
    String title;
    Date artWorkCreatedAt;
    Integer sizeWide;
    Integer sizeHeight;
    Boolean isFrame;
    String description;
    String intention;
    Boolean isAuction;
    Integer auctionStartPrice;
    LocalDateTime auctionClosingTime;
    Boolean isReg;
  }

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class ArtWorksByKeyWordResponse {
    Long artWorkId;
    String title;
  }

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class ArtWorksByArtistResponse {
    Long artWorkId;
    String title;
    Boolean isReg;
  }
}
