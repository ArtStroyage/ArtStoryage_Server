package com.example.artstoryage.domain.mapping;

import jakarta.persistence.*;

import com.example.artstoryage.domain.Term;
import com.example.artstoryage.domain.common.BaseEntity;
import com.example.artstoryage.domain.member.Member;

import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MemberTerm extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "term_id")
  private Term term;
}
