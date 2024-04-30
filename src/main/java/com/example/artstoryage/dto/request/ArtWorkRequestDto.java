package com.example.artstoryage.dto.request;

import java.util.Date;

import lombok.Getter;

public class ArtWorkRequestDto {
  @Getter
  public static class RegArtWorkRequest {
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
  }
}
