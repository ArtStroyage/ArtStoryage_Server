package com.example.artstoryage.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class Password {
  private static final String PASSWORD_REGEX =
      "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+-=\\[\\]{}|;':\",./<>?~`\\\\])[A-Za-z\\d!@#$%^&*()_+\\-=\\[\\]{}|;':\",./<>?~`\\\\]{9,16}";

  @Column(name = "password", nullable = false, columnDefinition = "TEXT")
  private String encryptedPassword;
}
