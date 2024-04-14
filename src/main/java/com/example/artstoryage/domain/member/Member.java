package com.example.artstoryage.domain.member;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.example.artstoryage.domain.Artist;
import com.example.artstoryage.domain.common.BaseEntity;
import com.example.artstoryage.domain.enums.SocialType;
import com.example.artstoryage.domain.mapping.Comment;
import com.example.artstoryage.domain.mapping.MemberTerm;
import com.example.artstoryage.domain.mapping.Wish;

import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Member extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String nickName;

  private String email;

  @Embedded private Password password;

  private String phoneNumber;

  @Enumerated(EnumType.STRING)
  private SocialType socialType;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "artist_id")
  private Artist artist;

  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
  private List<MemberTerm> memberTerms = new ArrayList<>();

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "wish_id")
  private Wish wish;

  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
  private List<Comment> comments = new ArrayList<>();
}
