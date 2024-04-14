package com.example.artstoryage.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.example.artstoryage.domain.mapping.MemberTerm;

import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Term {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String content;

  @OneToMany(mappedBy = "term", cascade = CascadeType.ALL)
  private List<MemberTerm> memberTerms = new ArrayList<>();
}
