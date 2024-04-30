package com.example.artstoryage.util;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtil {

  private static final long EXPIRATION_TIME = 900_000; // 15 minutes
  private static final String SECRET = "TestSecret"; // 실제 환경에서는 보안을 위해 복잡한 값 사용
  private static final String TOKEN_PREFIX = "Bearer ";
  private static final String HEADER_STRING = "Authorization";

  public static String createTestToken(String username) {
    String token =
        JWT.create()
            .withSubject(username)
            .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .sign(Algorithm.HMAC512(SECRET.getBytes()));
    return TOKEN_PREFIX + token;
  }

  public static String toJson(Object object) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(object);
  }
}
