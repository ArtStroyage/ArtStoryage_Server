package com.example.artstoryage.dto.response;

import com.example.artstoryage.domain.enums.PostCategory;

import lombok.*;

public class PostResponseDto {

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class CreatePostResponse {
    Long postId;
    String title;
    PostCategory postCategory;
  }
}
