package com.example.artstoryage.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.example.artstoryage.domain.common.BaseEntity;
import com.example.artstoryage.domain.member.Member;

import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Artist extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String profileImageLink;

  private String genre;

  private String introduction;

  private String history;

  private String bankBookLink;

  @Builder.Default private Boolean isAccountCertified = false;

  @OneToOne(fetch = FetchType.LAZY)
  private Member member;

  @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
  private List<ArtistUrl> artistUrls = new ArrayList<>();
}
