package com.example.artstoryage.dto.request;

import java.util.List;

import lombok.Getter;

public class MemberRequestDto {

  @Getter
  public static class SignUpMemberRequest {

    String name;
    String nickName;
    String email;
    String password;
    String phoneNumber;
    List<Long> agreedTerms;
  }
}
