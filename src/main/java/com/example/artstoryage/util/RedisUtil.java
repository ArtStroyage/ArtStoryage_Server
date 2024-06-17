package com.example.artstoryage.util;

import java.time.Duration;
import java.util.Random;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.example.artstoryage.domain.member.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class RedisUtil {

  private final String PREFIX = "sms:"; // key값이 중복되지 않도록 상수 선언
  private final int LIMIT_TIME = 3 * 60; // 인증번호 유효 시간

  private final StringRedisTemplate stringRedisTemplate;

  public String generateRandomNumber(int length) {
    Random random = new Random();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      sb.append(random.nextInt(10));
    }
    return sb.toString();
  }

  public void createSmsCertification(String phone, String certificationNumber) {
    stringRedisTemplate
        .opsForValue()
        .set(PREFIX + phone, certificationNumber, Duration.ofSeconds(LIMIT_TIME));
  }

  public String getSmsCertification(String phone) {
    return stringRedisTemplate.opsForValue().get(PREFIX + phone);
  }

  public void deleteSmsCertification(String phone) {
    stringRedisTemplate.delete(PREFIX + phone);
  }

  public void createEmailCertification(String email, String code) {
    stringRedisTemplate.opsForValue().set(PREFIX + email, code, Duration.ofSeconds(LIMIT_TIME));
  }

  public String getEmailCertification(String email) {
    return stringRedisTemplate.opsForValue().get(PREFIX + email);
  }

  public void deleteEmailCertification(String email) {
    stringRedisTemplate.delete(PREFIX + email);
  }

  public Boolean hasKey(String phone) {
    return stringRedisTemplate.hasKey(PREFIX + phone);
  }

  public String getMemberByToken(String token) {
    return stringRedisTemplate.opsForValue().get(token);
  }

  public void createFindPasswordToken(String token, Member member) {
    stringRedisTemplate.opsForValue().set(token, member.getEmail(), LIMIT_TIME);
  }

  public void deleteFindPasswordToken(String email) {
    stringRedisTemplate.delete(email);
  }
}
