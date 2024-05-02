package com.example.artstoryage.kakao;

import com.example.artstoryage.oAuth.OAuthInfoResponse;
import com.example.artstoryage.oAuth.OAuthProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoInfoResponse implements OAuthInfoResponse {

  // ToDo - 비즈앱 전환 시 동의 항목 Member 요소 추가
  @JsonProperty("kakao_account")
  private KakaoAccount kakaoAccount;

  @Getter
  @JsonIgnoreProperties(ignoreUnknown = true)
  static class KakaoAccount {
    private KakaoProfile profile;
  }

  @Getter
  @JsonIgnoreProperties(ignoreUnknown = true)
  static class KakaoProfile {
    private String nickname;
  }

  @Override
  public String getNickname() {
    return kakaoAccount.profile.nickname;
  }

  @Override
  public OAuthProvider getOAuthProvider() {
    return OAuthProvider.Kakao;
  }
}
