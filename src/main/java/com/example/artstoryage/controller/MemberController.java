package com.example.artstoryage.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.artstoryage.common.BaseResponse;
import com.example.artstoryage.converter.MemberConverter;
import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.dto.request.MemberRequestDto.*;
import com.example.artstoryage.dto.response.MemberResponseDto.*;
import com.example.artstoryage.exception.GlobalErrorCode;
import com.example.artstoryage.kakao.KakaoLoginParams;
import com.example.artstoryage.naver.NaverLoginParams;
import com.example.artstoryage.service.MemberCommandService;
import com.example.artstoryage.service.MemberQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/members")
@Tag(name = "😎 Member", description = "사용자 관련 API")
public class MemberController {

  private final MemberCommandService memberCommandService;
  private final MemberQueryService memberQueryService;

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

  @Operation(summary = "카카오 로그인 API", description = "카카오 로그인을 합니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "성공"),
  })
  @PostMapping("/kakao")
  @ResponseStatus(HttpStatus.CREATED)
  public BaseResponse<TokenResponse> loginKakao(@RequestBody KakaoLoginParams params) {
    return BaseResponse.onSuccess(
        MemberConverter.toSocialLogin(memberCommandService.loginSoical(params)));
  }

  @Operation(summary = "네이버 로그인 API", description = "네이버 로그인을 합니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "성공"),
  })
  @PostMapping("/naver")
  public BaseResponse<TokenResponse> loginNaver(@RequestBody NaverLoginParams params) {
    return BaseResponse.onSuccess(
        MemberConverter.toSocialLogin(memberCommandService.loginSoical(params)));
  }

  @Operation(summary = "문자 전송 API", description = "전화번호 인증 문자를 전송합니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "성공"),
  })
  @PostMapping("/send-one")
  public SingleMessageSentResponse sendMessage(@RequestBody PhoneNumberRequest request) {
    return memberCommandService.sendMessage(request);
  }

  @Operation(summary = "인증번호 확인 API", description = "인증번호를 검증합니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "성공"),
  })
  @PostMapping("/checkcode")
  public BaseResponse<Optional<Member>> isVerifyNumber(
      @RequestBody VerifyPhoneNumberRequest request) {
    return BaseResponse.onSuccess(memberCommandService.isVerifyNumber(request));
  }

  @Operation(summary = "이메일 중복 체크 API", description = "이메일을 중복 확인합니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "성공"),
  })
  @PostMapping("/email-check")
  public BaseResponse<Boolean> isDuplicateNickName(@RequestBody IsDuplicateEmailRequest request) {
    return BaseResponse.onSuccess(memberQueryService.isDuplicateEmail(request.getEmail()));
  }

  @Operation(summary = "닉네임 중복 체크 API", description = "닉네임을 중복 확인합니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "성공"),
  })
  @PostMapping("/nickname-check")
  public BaseResponse<Boolean> isDuplicateNickName(
      @RequestBody IsDuplicateNickNameRequest request) {
    return BaseResponse.onSuccess(memberQueryService.isDuplicateNickName(request.getNickName()));
  }

  @Operation(summary = "아이디 찾기 문자 전송 API", description = "이름과 휴대폰 번호를 통해 아이디를 찾습니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "성공"),
  })
  @PostMapping("/find-id")
  public SingleMessageSentResponse SendEmailByNameAndPhoneNumber(
      @RequestBody FindEmailByNameAndPhoneNumberRequest request) {
    return memberCommandService.findEmailCodeSender(request);
  }

  @Operation(summary = "아이디 찾기 API", description = "이름과 휴대폰 번호를 통해 아이디를 찾습니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "성공"),
  })
  @GetMapping("/find-id")
  public BaseResponse<FindEmailResponse> FindEmailByNameAndPhoneNumber(Optional<Member> member) {
    return BaseResponse.onSuccess(memberCommandService.findEmail(member));
  }

  @Operation(summary = "비밀번호 찾기 문자 전송 API", description = "이름과 휴대폰 번호를 통해 아이디를 찾습니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "성공"),
  })
  @PostMapping("/find-password")
  public SingleMessageSentResponse SendPasswordByNameAndEmailAndPhoneNumber(
      @RequestBody FindPasswordByNameAndEmailAndPhoneNumberRequest request) {
    return memberCommandService.findPasswordCodeSender(request);
  }

  @Operation(summary = "비밀번호 찾기 인증번호 검증 API", description = "문자를 통해 받은 인증번호를 검증합니다.")
  @ApiResponse(responseCode = "201", description = "성공")
  @PostMapping("/check-code")
  public BaseResponse<String> checkCode(@RequestBody VerifyCodeRequest request) throws Exception {
    return BaseResponse.onSuccess(memberCommandService.isVerifiedNumber(request));
  }

  @Operation(summary = "비밀번호 변경 API", description = "새 비밀번호로 변경합니다.")
  @ApiResponse(responseCode = "201", description = "성공")
  @PutMapping("/change-password")
  public BaseResponse<ChangePasswordResponse> changePassword(
      @RequestHeader String token, @RequestBody ChangePasswordRequest request) throws Exception {
    return BaseResponse.onSuccess(
        MemberConverter.toChangePasswordResponse(
            memberCommandService.findPassword(token, request)));
  }
}
