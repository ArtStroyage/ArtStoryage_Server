package com.example.artstoryage.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

import com.example.artstoryage.domain.common.BaseEntity;
import com.example.artstoryage.domain.mapping.ArtWorkPrice;
import com.example.artstoryage.domain.mapping.ArtWorkWish;
import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.dto.request.ArtWorkRequestDto.BidAuctionRequest;
import com.example.artstoryage.dto.request.ArtWorkRequestDto.RegAuctionArtWorkRequest;
import com.example.artstoryage.dto.request.ArtWorkRequestDto.UpdateArtWorkRequest;

import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ArtWork extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

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

  private LocalDateTime auctionClosingTime;

  private Boolean isReg;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "artist_id")
  private Artist artist;

  @OneToMany(mappedBy = "artWork", cascade = CascadeType.ALL)
  private List<ArtWorkPrice> artWorkPrices = new ArrayList<>();

  @OneToMany(mappedBy = "artWork", cascade = CascadeType.ALL)
  private List<ArtWorkWish> artWorkWishes = new ArrayList<>();

  public void allowArtWork() {
    this.isReg = true;
  }

  public void regAuctionArtWork(RegAuctionArtWorkRequest request) {
    if (request.getDay() < 1 || request.getDay() > 10) {
      throw new IllegalArgumentException("경매 기간은 최소 1일, 최대 10일 입니다.");
    }

    this.isAuction = true;
    this.auctionClosingTime = LocalDateTime.now().plusDays(request.getDay());
  }

  public void cancelAuctionArtWork() {
    this.isAuction = false;
    this.auctionClosingTime = null;
  }

  public void updateArtWork(UpdateArtWorkRequest request) {
    this.imageLink = request.getImageLink();
    this.title = request.getTitle();
    this.artWorkCreatedAt = request.getArtWorkCreatedAt();
    this.sizeWide = request.getSizeWide();
    this.sizeHeight = request.getSizeHeight();
    this.isFrame = request.getIsFrame();
    this.description = request.getDescription();
    this.intention = request.getIntention();
    this.auctionStartPrice = request.getAuctionStartPrice();
  }

  public void bidAuctionArtWork(BidAuctionRequest request, Member member) {
    ArtWorkPrice artWorkPrice =
        ArtWorkPrice.builder().price(request.getBidPrice()).member(member).artWork(this).build();

    artWorkPrices.add(artWorkPrice);
    auctionStartPrice = request.getBidPrice();
  }
}
