package com.example.artstoryage.kakao;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.artstoryage.oAuth.OAuthLoginParams;
import com.example.artstoryage.oAuth.OAuthProvider;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoLoginParams implements OAuthLoginParams {
  private String authorizationCode;

  @Override
  public OAuthProvider oAuthProvider() {
    return OAuthProvider.Kakao;
  }

  @Override
  public MultiValueMap<String, String> makeBody() {
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("code", authorizationCode);
    return body;
  }
}
