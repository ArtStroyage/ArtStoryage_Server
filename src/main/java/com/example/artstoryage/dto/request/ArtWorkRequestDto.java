package com.example.artstoryage.dto.request;

import java.util.Date;

import lombok.Getter;

public class ArtWorkRequestDto {
  @Getter
  public static class RegArtWorkRequest {
    private String imageLink;
    private String title;
    private Date artWorkCreatedAt;
    private Integer sizeWide;
    private Integer sizeHeight;
    private Boolean isFrame;
    private String description;
    private String intention;
    private Boolean isAuction;
    private Integer auctionStartPrice;
  }
}
