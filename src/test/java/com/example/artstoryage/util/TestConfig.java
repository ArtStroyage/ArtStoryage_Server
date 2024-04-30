package com.example.artstoryage.util;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.example.artstoryage.security.filter.JwtAuthExceptionHandlingFilter;
import com.example.artstoryage.security.filter.JwtRequestFilter;
import com.example.artstoryage.security.handler.JwtAccessDeniedHandler;
import com.example.artstoryage.security.handler.JwtAuthenticationEntryPoint;
import com.example.artstoryage.security.provider.JwtAuthProvider;
import com.example.artstoryage.security.service.MemberDetailsService;
import com.example.artstoryage.service.MemberQueryService;

@TestConfiguration
public class TestConfig {

  @Bean
  public MemberQueryService memberQueryService() {
    return Mockito.mock(MemberQueryService.class);
  }

  @Bean
  public JwtAuthExceptionHandlingFilter jwtAuthExceptionHandlingFilter() {
    return Mockito.mock(JwtAuthExceptionHandlingFilter.class);
  }

  @Bean
  public JwtRequestFilter jwtRequestFilter() {
    return Mockito.mock(JwtRequestFilter.class);
  }

  @Bean
  public JwtAccessDeniedHandler jwtAccessDeniedHandler() {
    return Mockito.mock(JwtAccessDeniedHandler.class);
  }

  @Bean
  public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
    return Mockito.mock(JwtAuthenticationEntryPoint.class);
  }

  @Bean
  public JwtAuthProvider jwtAuthProvider() {
    return Mockito.mock(JwtAuthProvider.class);
  }

  @Bean
  public MemberDetailsService memberDetailsService() {
    return Mockito.mock(MemberDetailsService.class);
  }
}
