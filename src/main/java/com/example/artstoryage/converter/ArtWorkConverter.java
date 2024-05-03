package com.example.artstoryage.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.artstoryage.domain.ArtWork;
import com.example.artstoryage.domain.Artist;
import com.example.artstoryage.dto.request.ArtWorkRequestDto.*;
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
        .isAuction(request.getIsAuction())
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

  public static List<ArtWorkByArtistResponse> toRegArtWorkResponseList(List<ArtWork> artWorkList) {
    List<ArtWorkByArtistResponse> responseList = new ArrayList<>();

    for (ArtWork artWork : artWorkList) {
      responseList.add(
          ArtWorkByArtistResponse.builder()
              .artWorkId(artWork.getId())
              .title(artWork.getTitle())
              .isReg(artWork.getIsReg())
              .build());
    }

    return responseList;
  }

  public static ArtWorkResponse toApprovedArtWorkResponse(ArtWork artWork) {
    return ArtWorkResponse.builder().title(artWork.getTitle()).isReg(artWork.getIsReg()).build();
  }
}
