package com.example.artstoryage.naver;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.artstoryage.oAuth.OAuthLoginParams;
import com.example.artstoryage.oAuth.OAuthProvider;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NaverLoginParams implements OAuthLoginParams {
  private String authorizationCode;
  private String state;

  @Override
  public OAuthProvider oAuthProvider() {
    return OAuthProvider.NAVER;
  }

  @Override
  public MultiValueMap<String, String> makeBody() {
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("code", authorizationCode);
    body.add("state", state);
    return body;
  }
}
