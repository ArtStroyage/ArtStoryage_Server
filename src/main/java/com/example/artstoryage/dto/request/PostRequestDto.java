package com.example.artstoryage.dto.request;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

public class PostRequestDto {

  @Getter
  @SuperBuilder
  public static class CreatePostRequest {
    String title;
    String content;
  }

  @Getter
  @SuperBuilder
  public static class CreateExhibitionRequest extends CreatePostRequest {
    String startDate;
    String endDate;
    String siteUrl;
    String businessHours;
    String address;
    String tags;
  }
}
