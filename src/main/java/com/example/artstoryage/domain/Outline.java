package com.example.artstoryage.domain;

import jakarta.persistence.*;

import com.example.artstoryage.domain.common.BaseEntity;

import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Outline extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String startDate;

  private String endDate;

  private String siteUrl;

  private String businessHours;

  private String address;

  private String tags;
}
