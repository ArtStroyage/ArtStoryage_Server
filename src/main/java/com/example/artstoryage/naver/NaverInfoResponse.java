package com.example.artstoryage.naver;

import com.example.artstoryage.oAuth.OAuthInfoResponse;
import com.example.artstoryage.oAuth.OAuthProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverInfoResponse implements OAuthInfoResponse {

  @JsonProperty("response")
  private Response response;

  @Getter
  @JsonIgnoreProperties(ignoreUnknown = true)
  static class Response {
    private String nickname;
  }

  @Override
  public String getNickname() {
    return response.nickname;
  }

  @Override
  public OAuthProvider getOAuthProvider() {
    return OAuthProvider.NAVER;
  }
}
