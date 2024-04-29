package com.example.artstoryage.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

import com.example.artstoryage.domain.common.BaseEntity;
import com.example.artstoryage.domain.mapping.ArtWorkPrice;
import com.example.artstoryage.domain.mapping.ArtWorkWish;

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
}
