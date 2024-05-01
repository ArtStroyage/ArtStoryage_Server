package com.example.artstoryage.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.artstoryage.annotation.auth.AuthMember;
import com.example.artstoryage.common.BaseResponse;
import com.example.artstoryage.converter.PostConverter;
import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.dto.request.PostRequestDto.*;
import com.example.artstoryage.dto.response.PostResponseDto.*;
import com.example.artstoryage.exception.GlobalErrorCode;
import com.example.artstoryage.service.PostCommandService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
@Tag(name = "📋 Post", description = "전시회/게시판 관련 API")
public class PostController {

  private final PostCommandService postCommandService;

  @Operation(summary = "게시글 등록 API", description = "게시글 정보를 등록합니다")
  @ApiResponses({@ApiResponse(responseCode = "201", description = "성공")})
  @PostMapping("/")
  @ResponseStatus(HttpStatus.CREATED)
  public BaseResponse<CreatePostResponse> createArtist(
      @Parameter(hidden = true) @AuthMember Member member, @RequestBody CreatePostRequest request) {
    return BaseResponse.onSuccess(
        GlobalErrorCode.CREATED,
        PostConverter.toCreatePostResponse(postCommandService.createPost(request, member)));
  }

  @Operation(summary = "전시회 등록 API", description = "전시회 정보를 등록합니다")
  @ApiResponses({@ApiResponse(responseCode = "201", description = "성공")})
  @PostMapping("/exhibit")
  @ResponseStatus(HttpStatus.CREATED)
  public BaseResponse<CreatePostResponse> createExhibition(
      @Parameter(hidden = true) @AuthMember Member member,
      @RequestBody CreateExhibitionRequest request) {
    return BaseResponse.onSuccess(
        GlobalErrorCode.CREATED,
        PostConverter.toCreatePostResponse(postCommandService.createExhibition(request, member)));
  }
}
