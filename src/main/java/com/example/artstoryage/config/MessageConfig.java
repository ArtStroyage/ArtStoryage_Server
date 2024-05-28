package com.example.artstoryage.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Configuration
public class MessageConfig {

  @Value("${coolsms.apikey}")
  private String smsApiKey;

  @Value("${coolsms.apisecret}")
  private String smsSecret;

  @Bean
  public DefaultMessageService defaultMessageService() {
    return NurigoApp.INSTANCE.initialize(smsApiKey, smsSecret, "https://api.coolsms.co.kr");
  }
}
