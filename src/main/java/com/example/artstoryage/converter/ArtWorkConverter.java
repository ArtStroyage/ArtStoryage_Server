package com.example.artstoryage.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.artstoryage.domain.ArtWork;
import com.example.artstoryage.domain.Artist;
import com.example.artstoryage.dto.request.ArtWorkRequestDto.*;
import com.example.artstoryage.dto.response.ArtWorkResponseDto;
import com.example.artstoryage.dto.response.ArtWorkResponseDto.*;

@Component
public class ArtWorkConverter {

  public static ArtWork toArtWork(RegArtWorkRequest request, Artist artist) {
    return ArtWork.builder()
        .artist(artist)
        .imageLink(request.getImageLink())
        .title(request.getTitle())
        .artWorkCreatedAt(request.getArtWorkCreatedAt())
        .sizeWide(request.getSizeWide())
        .sizeHeight(request.getSizeHeight())
        .isFrame(request.getIsFrame())
        .description(request.getDescription())
        .intention(request.getIntention())
        .auctionStartPrice(request.getAuctionStartPrice())
        .isReg(false)
        .build();
  }

  public static ArtWorkResponse toRegArtWorkResponse(ArtWork artWork) {
    return ArtWorkResponse.builder()
        .artWorkId(artWork.getId())
        .title(artWork.getTitle())
        .isReg(artWork.getIsReg())
        .build();
  }

  public static List<ArtWorksByArtistResponse> toArtWorksByArtistResponseList(
      List<ArtWork> artWorkList) {
    List<ArtWorksByArtistResponse> responseList = new ArrayList<>();

    for (ArtWork artWork : artWorkList) {
      responseList.add(
          ArtWorksByArtistResponse.builder()
              .artWorkId(artWork.getId())
              .title(artWork.getTitle())
              .isReg(artWork.getIsReg())
              .build());
    }

    return responseList;
  }

  public static List<ArtWorksByKeyWordResponse> toArtWorksByKeyWordResponseList(
      List<ArtWork> artWorkList) {
    List<ArtWorksByKeyWordResponse> responseList = new ArrayList<>();

    for (ArtWork artWork : artWorkList) {
      responseList.add(
          ArtWorksByKeyWordResponse.builder()
              .artWorkId(artWork.getId())
              .title(artWork.getTitle())
              .build());
    }

    return responseList;
  }

  public static List<ArtWorkResponseDto.ArtWorksResponse> toArtWorksResponseList(
      List<ArtWork> artWorkList) {
    List<ArtWorksResponse> responseList = new ArrayList<>();

    for (ArtWork artWork : artWorkList) {
      responseList.add(
          ArtWorksResponse.builder().artWorkId(artWork.getId()).title(artWork.getTitle()).build());
    }

    return responseList;
  }

  public static ArtWorkResponse toApprovedArtWorkResponse(ArtWork artWork) {
    return ArtWorkResponse.builder().title(artWork.getTitle()).build();
  }

  public static ArtWorkResponse toArtWorkResponse(ArtWork artWork) {
    return ArtWorkResponse.builder().title(artWork.getTitle()).build();
  }

  public static ArtWorkResponse toUpdatedArtWorkResponse(ArtWork artWork) {
    return ArtWorkResponse.builder()
        .imageLink(artWork.getImageLink())
        .title(artWork.getTitle())
        .artWorkCreatedAt(artWork.getArtWorkCreatedAt())
        .sizeWide(artWork.getSizeWide())
        .sizeHeight(artWork.getSizeHeight())
        .isFrame(artWork.getIsFrame())
        .description(artWork.getDescription())
        .intention(artWork.getIntention())
        .auctionStartPrice(artWork.getAuctionStartPrice())
        .build();
  }

  public static ArtWorkResponse toRegAuctionArtWorkResponse(ArtWork artWork) {
    return ArtWorkResponse.builder()
        .title(artWork.getTitle())
        .auctionStartPrice(artWork.getAuctionStartPrice())
        .auctionClosingTime(artWork.getAuctionClosingTime())
        .build();
  }

  public static ArtWorkResponse toBidAuctionResponse(ArtWork artWork) {
    return ArtWorkResponse.builder()
        .title(artWork.getTitle())
        .auctionStartPrice(artWork.getAuctionStartPrice())
        .build();
  }
}
