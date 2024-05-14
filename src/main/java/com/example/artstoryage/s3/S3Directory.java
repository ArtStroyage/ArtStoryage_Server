package com.example.artstoryage.s3;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum S3Directory {
  ARTWORK("artwork"), // 입점 신청
  BANKBOOK("bankbook"), // 통장 사본
  EXNOTICE("exnotice"), // 전시회 안내
  ARTIST("artist"), // 작가 이미지
  EXART("exart"), // 전시회 참가
  POST("post"); // 게시글

  private final String directory;
}
