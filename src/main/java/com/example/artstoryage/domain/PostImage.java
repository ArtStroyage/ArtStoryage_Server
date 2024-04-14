package com.example.artstoryage.domain;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PostImage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String imageLink;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post")
  private Post post;
}
