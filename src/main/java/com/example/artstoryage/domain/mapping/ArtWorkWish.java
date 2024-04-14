package com.example.artstoryage.domain.mapping;

import jakarta.persistence.*;

import com.example.artstoryage.domain.ArtWork;

import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ArtWorkWish {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "wish_id")
  private Wish wish;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "art_work_id")
  private ArtWork artWork;
}
