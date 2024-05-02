package com.example.artstoryage.oAuth;

public interface OAuthInfoResponse {
  String getNickname();

  // ToDo - 비즈앱 전환 시 동의 항목 Member 요소 추가

  OAuthProvider getOAuthProvider();
}
