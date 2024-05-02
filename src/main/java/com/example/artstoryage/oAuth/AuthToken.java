package com.example.artstoryage.oAuth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthToken {
  private String accessToken;
  private String refreshToken;

  public static AuthToken of(String accessToken, String refreshToken) {
    return new AuthToken(accessToken, refreshToken);
  }
}
