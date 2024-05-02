package com.example.artstoryage.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.artstoryage.common.BaseResponse;
import com.example.artstoryage.converter.MemberConverter;
import com.example.artstoryage.dto.request.MemberRequestDto.LoginMemberRequest;
import com.example.artstoryage.dto.request.MemberRequestDto.ReissueRequest;
import com.example.artstoryage.dto.request.MemberRequestDto.SignUpMemberRequest;
import com.example.artstoryage.dto.response.MemberResponseDto.SignUpMemberResponse;
import com.example.artstoryage.dto.response.MemberResponseDto.TokenResponse;
import com.example.artstoryage.exception.GlobalErrorCode;
import com.example.artstoryage.kakao.KakaoLoginParams;
import com.example.artstoryage.service.MemberCommandService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/members")
@Tag(name = "😎 Member", description = "사용자 관련 API")
public class MemberController {

  private final MemberCommandService memberCommandService;

  @Operation(summary = "회원가입 API", description = "회원가입을 진행합니다")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "성공"),
  })
  @PostMapping("/signup")
  @ResponseStatus(HttpStatus.CREATED)
  public BaseResponse<SignUpMemberResponse> signUpMember(@RequestBody SignUpMemberRequest request) {
    return BaseResponse.onSuccess(
        GlobalErrorCode.CREATED,
        MemberConverter.toSignUpMemberResponse(memberCommandService.signUpMember(request)));
  }

  @Operation(summary = "로그인 API", description = "이메일, 비밀번호를 사용한 로그인을 진행합니다")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "성공"),
  })
  @PostMapping("/login")
  @ResponseStatus(HttpStatus.CREATED)
  public BaseResponse<TokenResponse> loginMember(@RequestBody LoginMemberRequest request) {
    return BaseResponse.onSuccess(GlobalErrorCode.CREATED, memberCommandService.login(request));
  }

  @Operation(summary = "reissue API", description = "토큰을 재발급합니다.")
  @ApiResponse(responseCode = "200", description = "성공")
  @PostMapping("/reissue")
  public BaseResponse<TokenResponse> reissue(@RequestBody ReissueRequest request) {
    return BaseResponse.onSuccess(memberCommandService.reissue(request));
  }

  @Operation(summary = "카카오 API", description = "카카오로그인을 합니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "성공"),
  })
  @PostMapping("/kakao")
  @ResponseStatus(HttpStatus.CREATED)
  public BaseResponse<TokenResponse> loginKakao(@RequestBody KakaoLoginParams params) {
    return BaseResponse.onSuccess(
        MemberConverter.toKakaoLogin(memberCommandService.loginKakao(params)));
  }
}
