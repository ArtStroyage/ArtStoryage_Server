package com.example.artstoryage.dto.request;

import lombok.Builder;
import lombok.Getter;

public class ArtistRequestDto {

  @Getter
  @Builder
  public static class CreateArtistRequest {
    String name;
    String profileImageLink;
    String genre;
    String introduction;
    String history;
    String bankBookLink;
  }
}
