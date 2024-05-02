package com.example.artstoryage.oAuth;

public interface OAuthApiClient {
  // ToDo - 비즈앱 전환 시 동의 항목 Member 요소 추가
  OAuthProvider oAuthProvider();

  String requestAccessToken(OAuthLoginParams params);

  OAuthInfoResponse requestOauthInfo(String accessToken);
}
