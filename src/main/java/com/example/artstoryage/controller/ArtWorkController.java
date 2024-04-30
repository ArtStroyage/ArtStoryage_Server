package com.example.artstoryage.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.artstoryage.annotation.auth.AuthMember;
import com.example.artstoryage.common.BaseResponse;
import com.example.artstoryage.converter.ArtWorkConverter;
import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.dto.request.ArtWorkRequestDto.*;
import com.example.artstoryage.dto.response.ArtWorkResponseDto.*;
import com.example.artstoryage.exception.GlobalErrorCode;
import com.example.artstoryage.service.impl.ArtWorkCommandServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/artworks")
@Tag(name = "🖼️ ArtWork", description = "예술 작품 관련 API")
public class ArtWorkController {

  private final ArtWorkCommandServiceImpl artWorkCommandService;

  @Operation(summary = "입점 신청 API", description = "입점 신청을 진행합니다")
  @ApiResponses({@ApiResponse(responseCode = "201", description = "성공")})
  @PostMapping("/registration")
  @ResponseStatus(HttpStatus.CREATED)
  public BaseResponse<RegArtWorkResponse> regArtWork(
      @Parameter(hidden = true) @AuthMember Member member, @RequestBody RegArtWorkRequest request) {
    return BaseResponse.onSuccess(
        GlobalErrorCode.CREATED,
        ArtWorkConverter.toRegArtWorkResponse(artWorkCommandService.regArtWork(member, request)));
  }

  @Operation(summary = "입점 신청 삭제 API", description = "입점 신청을 삭제합니다")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "성공")})
  @DeleteMapping("/registration/{artWorkId}")
  @ResponseStatus(HttpStatus.OK)
  public BaseResponse<GlobalErrorCode> deleteArtwork(@Parameter @PathVariable Long artWorkId) {
    artWorkCommandService.deleteArtWork(artWorkId);
    return BaseResponse.onSuccess(GlobalErrorCode.DELETED);
  }
}
