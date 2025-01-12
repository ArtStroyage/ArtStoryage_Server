package com.example.artstoryage.security.provider;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.example.artstoryage.exception.GlobalErrorCode;
import com.example.artstoryage.exception.custom.TokenException;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtAuthProvider {

  private final SecretKey secretKey;
  private final long accessTokenValidityMilliseconds;
  private final long refreshTokenValidityMilliseconds;
  private final RedisTemplate<String, String> redisTemplate;

  public JwtAuthProvider(
      @Value("${jwt.secret}") final String secretKey,
      @Value("${jwt.access-token-validity}") final long accessTokenValidityMilliseconds,
      @Value("${jwt.refresh-token-validity}") final long refreshTokenValidityMilliseconds,
      RedisTemplate<String, String> redisTemplate) {
    this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    this.accessTokenValidityMilliseconds = accessTokenValidityMilliseconds;
    this.refreshTokenValidityMilliseconds = refreshTokenValidityMilliseconds;
    this.redisTemplate = redisTemplate;
  }

  public String generateAccessToken(Long userId) {
    return generateToken(userId, accessTokenValidityMilliseconds);
  }

  public String generateRefreshToken(Long userId) {
    String refreshToken = generateToken(userId, refreshTokenValidityMilliseconds);
    redisTemplate
        .opsForValue()
        .set(
            userId.toString(),
            refreshToken,
            refreshTokenValidityMilliseconds,
            TimeUnit.MILLISECONDS);
    return refreshToken;
  }

  private String generateToken(Long userId, long validityMilliseconds) {
    Claims claims = Jwts.claims();

    ZonedDateTime now = ZonedDateTime.now();
    ZonedDateTime tokenValidity = now.plusSeconds(validityMilliseconds / 1000);

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(userId.toString())
        .setIssuedAt(Date.from(now.toInstant()))
        .setExpiration(Date.from(tokenValidity.toInstant()))
        .setIssuer("ArtStoryage")
        .signWith(secretKey, SignatureAlgorithm.HS256)
        .compact();
  }

  public Long getSubject(String token) {
    return Long.valueOf(getClaims(token).getBody().getSubject());
  }

  private Jws<Claims> getClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
  }

  public boolean isTokenValid(String token) {
    try {
      Jws<Claims> claims = getClaims(token);
      Date expiredDate = claims.getBody().getExpiration();
      return expiredDate.after(new Date());
    } catch (ExpiredJwtException e) {
      throw new RuntimeException();
    } catch (SecurityException
        | MalformedJwtException
        | UnsupportedJwtException
        | IllegalArgumentException e) {
      throw new RuntimeException();
    }
  }

  public Long parseRefreshToken(String token) {
    if (isTokenValid(token)) {
      Claims claims = getClaims(token).getBody();
      return Long.parseLong(claims.getSubject());
    }
    throw new TokenException(GlobalErrorCode.INVALID_TOKEN);
  }
}
