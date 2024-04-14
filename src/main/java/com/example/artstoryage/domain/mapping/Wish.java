package com.example.artstoryage.domain.mapping;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.example.artstoryage.domain.member.Member;

import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Wish {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  private Member member;

  @OneToMany(mappedBy = "wish", cascade = CascadeType.ALL)
  private List<ArtWorkWish> artWorkWishes = new ArrayList<>();
}
