package com.example.artstoryage.domain.member;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.example.artstoryage.domain.Artist;
import com.example.artstoryage.domain.Post;
import com.example.artstoryage.domain.common.BaseEntity;
import com.example.artstoryage.domain.enums.MemberRole;
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

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String nickName;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  @Embedded
  private Password password;

  @Column(nullable = false)
  private String phoneNumber;

  @Enumerated(EnumType.STRING)
  private SocialType socialType;

  @Enumerated(EnumType.STRING)
  private MemberRole memberRole;

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

  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
  private List<Post> posts = new ArrayList<>();

  public void setMemberTerms(List<MemberTerm> memberTerms) {
    this.memberTerms = memberTerms;
  }

  public void setArtist(Artist artist) {
    this.artist = artist;
  }
}
