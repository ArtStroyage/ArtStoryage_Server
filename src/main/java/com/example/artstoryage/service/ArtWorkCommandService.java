package com.example.artstoryage.service;

import com.example.artstoryage.domain.ArtWork;
import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.dto.request.ArtWorkRequestDto.*;

public interface ArtWorkCommandService {

  ArtWork regArtWork(Member member, RegArtWorkRequest request);

  ArtWork approveArtWork(Long artWorkId);

  ArtWork updateRegArtWork(Long artWorkId, UpdateArtWorkRequest request);

  void deleteArtWork(Long artWorkId);

  ArtWork regAuctionArtWork(Long artWorkId, RegAuctionArtWorkRequest request);

  void cancelAuctionArtWork(Long artWorkId);

  ArtWork bidAuctionArtWork(Long artWorkId, BidAuctionRequest request, Member member);
}
