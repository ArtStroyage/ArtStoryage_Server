package com.example.artstoryage.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.artstoryage.domain.ArtWork;
import com.example.artstoryage.domain.Artist;
import com.example.artstoryage.domain.mapping.ArtWorkPrice;
import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.dto.request.ArtWorkRequestDto.*;
import com.example.artstoryage.dto.response.ArtWorkResponseDto.ArtWorkListResponse;
import com.example.artstoryage.dto.response.ArtWorkResponseDto.ArtWorkResponse;

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


  public static List<ArtWorkListResponse> toArtWorkListResponse(List<ArtWork> artWorkList) {
    return artWorkList.stream()
        .map(
            artWork ->
                ArtWorkListResponse.builder()
                    .artWorkId(artWork.getId())
                    .title(artWork.getTitle())
                    .isReg(artWork.getIsReg())
                    .build())
        .collect(Collectors.toList());

  }

  public static ArtWorkResponse toArtWorkResponse(ArtWork artWork) {
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

  public static ArtWorkResponse toArtWorkAuctionResponse(ArtWork artWork) {
    return ArtWorkResponse.builder()
        .title(artWork.getTitle())
        .auctionStartPrice(artWork.getAuctionStartPrice())
        .auctionClosingTime(artWork.getAuctionClosingTime())
        .build();
  }

  public static ArtWorkPrice toArtWorkPrice(
      BidAuctionRequest request, ArtWork artWork, Member member) {
    return ArtWorkPrice.builder()
        .price(request.getBidPrice())
        .member(member)
        .artWork(artWork)
        .build();
  }
}
