package com.example.artstoryage.converter;

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

  public static RegArtWorkResponse toRegArtWorkResponse(ArtWork artWork) {
    return RegArtWorkResponse.builder()
        .artWorkId(artWork.getId())
        .title(artWork.getTitle())
        .isReg(artWork.getIsReg())
        .build();
  }

  public static AllowArtWorkResponse toAllowArtWorkResponse(ArtWork artWork) {
    return AllowArtWorkResponse.builder()
        .title(artWork.getTitle())
        .isReg(artWork.getIsReg())
        .build();
  }
}
