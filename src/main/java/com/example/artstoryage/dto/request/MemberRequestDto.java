package com.example.artstoryage.dto.request;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

public class MemberRequestDto {

  @Getter
  @Builder
  public static class SignUpMemberRequest {

    String name;
    String nickName;
    String email;
    String password;
    String phoneNumber;
    List<Long> agreedTerms;
  }

  @Getter
  public static class LoginMemberRequest {

    String email;
    String password;
  }

  @Getter
  public static class ReissueRequest {

    String refreshToken;
  }

  @Getter
  public static class IsDuplicateNickNameRequest {
    String nickName;
  }
}
