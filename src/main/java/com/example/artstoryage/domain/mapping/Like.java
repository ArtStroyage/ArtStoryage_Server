package com.example.artstoryage.domain.mapping;

import jakarta.persistence.*;

import com.example.artstoryage.domain.Post;
import com.example.artstoryage.domain.member.Member;

public class Like {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  private Post post;
}
