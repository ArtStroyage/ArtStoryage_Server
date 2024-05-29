package com.example.artstoryage.service.impl;

import static com.example.artstoryage.converter.ArtWorkConverter.*;

import java.time.LocalDateTime;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.artstoryage.domain.ArtWork;
import com.example.artstoryage.domain.Artist;
import com.example.artstoryage.domain.mapping.ArtWorkPrice;
import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.dto.request.ArtWorkRequestDto.*;
import com.example.artstoryage.exception.GlobalErrorCode;
import com.example.artstoryage.exception.custom.ArtWorkException;
import com.example.artstoryage.exception.custom.ArtistException;
import com.example.artstoryage.repository.ArtWorkRepository;
import com.example.artstoryage.repository.ArtistRepository;
import com.example.artstoryage.service.ArtWorkCommandService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ArtWorkCommandServiceImpl implements ArtWorkCommandService {

  private final ArtWorkRepository artWorkRepository;
  private final ArtistRepository artistRepository;

  @Override
  public ArtWork regArtWork(Member member, RegArtWorkRequest request) {
    Artist artist =
        artistRepository
            .findByMember(member)
            .orElseThrow(() -> new ArtistException(GlobalErrorCode.ARTIST_NOT_FOUND));

    return artWorkRepository.save(toArtWork(request, artist));
  }

  @Override
  public ArtWork approveArtWork(Long artWorkId) {
    ArtWork artWork =
        artWorkRepository
            .findById(artWorkId)
            .orElseThrow(() -> new ArtWorkException(GlobalErrorCode.ARTWORK_NOT_FOUND));

    artWork.allowArtWork();

    return artWork;
  }

  @Override
  public ArtWork updateRegArtWork(Long artWorkId, UpdateArtWorkRequest request) {
    ArtWork artWork =
        artWorkRepository
            .findById(artWorkId)
            .orElseThrow(() -> new ArtWorkException(GlobalErrorCode.ARTWORK_NOT_FOUND));

    artWork.updateArtWork(request);

    return artWork;
  }

  @Override
  public void deleteArtWork(Long artWorkId) {
    if (!artWorkRepository.existsById(artWorkId)) {
      throw new ArtWorkException(GlobalErrorCode.ARTWORK_NOT_FOUND);
    }

    artWorkRepository.deleteById(artWorkId);
  }

  @Override
  public ArtWork regAuctionArtWork(Long artWorkId, RegAuctionArtWorkRequest request) {
    ArtWork artWork =
        artWorkRepository
            .findById(artWorkId)
            .orElseThrow(() -> new ArtWorkException(GlobalErrorCode.ARTIST_NOT_FOUND));

    if (!artWork.getIsReg()) {
      throw new ArtWorkException(GlobalErrorCode.ARTWORK_NOT_APPROVED);
    }

    artWork.regAuctionArtWork(request);

    return artWork;
  }

  @Override
  public void cancelAuctionArtWork(Long artWorkId) {
    ArtWork artWork =
        artWorkRepository
            .findById(artWorkId)
            .orElseThrow(() -> new ArtWorkException(GlobalErrorCode.ARTWORK_NOT_FOUND));

    artWork.cancelAuctionArtWork();
  }

  @Override
  public ArtWork bidAuctionArtWork(Long artWorkId, BidAuctionRequest request, Member member) {
    ArtWork artWork =
        artWorkRepository
            .findById(artWorkId)
            .orElseThrow(() -> new ArtWorkException(GlobalErrorCode.ARTWORK_NOT_FOUND));

    if (!artWork.getIsAuction()) {
      throw new ArtWorkException(GlobalErrorCode.ARTWORK_AUCTION_NOT_STARTED);
    } else if (LocalDateTime.now().isAfter(artWork.getAuctionClosingTime())) {
      throw new ArtWorkException(GlobalErrorCode.ARTWORK_AUCTION_CLOSED);
    } else if (request.getBidPrice() % 10000 != 0) {
      throw new ArtWorkException(GlobalErrorCode.ARTWORK_AUCTION_UNIT_INCORRECT);
    } else if (request.getBidPrice() <= artWork.getAuctionStartPrice()) {
      throw new ArtWorkException(GlobalErrorCode.ARTWORK_AUCTION_BIDPRICE_LESS);
    }

    ArtWorkPrice artWorkPrice = toArtWorkPrice(request, artWork, member);
    artWork.bidAuctionArtWork(artWorkPrice);

    return artWork;
  }
}
