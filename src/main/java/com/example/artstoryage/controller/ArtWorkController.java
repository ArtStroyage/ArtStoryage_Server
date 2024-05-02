package com.example.artstoryage.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.artstoryage.annotation.auth.AuthMember;
import com.example.artstoryage.common.BaseResponse;
import com.example.artstoryage.converter.ArtWorkConverter;
import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.dto.request.ArtWorkRequestDto.*;
import com.example.artstoryage.dto.response.ArtWorkResponseDto.*;
import com.example.artstoryage.exception.GlobalErrorCode;
import com.example.artstoryage.service.ArtWorkCommandService;
import com.example.artstoryage.service.ArtWorkQueryService;

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

  private final ArtWorkCommandService artWorkCommandService;
  private final ArtWorkQueryService artWorkQueryService;

  @Operation(summary = "작품 등록 요청 API", description = "작품 등록 요청을 진행합니다")
  @ApiResponses({@ApiResponse(responseCode = "201", description = "성공")})
  @PostMapping("/registration")
  @ResponseStatus(HttpStatus.CREATED)
  public BaseResponse<ArtWorkResponse> regArtWork(
      @Parameter(hidden = true) @AuthMember Member member, @RequestBody RegArtWorkRequest request) {
    return BaseResponse.onSuccess(
        GlobalErrorCode.CREATED,
        ArtWorkConverter.toRegArtWorkResponse(artWorkCommandService.regArtWork(member, request)));
  }

  @Operation(summary = "작품 등록 승인 API", description = "작품 등록을 승인합니다")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "성공")})
  @PutMapping("/approve/{artWorkId}")
  @ResponseStatus(HttpStatus.OK)
  public BaseResponse<ArtWorkResponse> regArtWork(@Parameter @PathVariable Long artWorkId) {
    return BaseResponse.onSuccess(
        GlobalErrorCode.UPDATED,
        ArtWorkConverter.toApprovedArtWorkResponse(
            artWorkCommandService.approveArtWork(artWorkId)));
  }

  @Operation(summary = "승인된 작품 정보 조회 API", description = "승인된 작품의 정보 조회")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "성공")})
  @GetMapping("/approved/{artWorkId}")
  @ResponseStatus(HttpStatus.OK)
  public BaseResponse<ArtWorkResponse> getArtWork(@Parameter @PathVariable Long artWorkId) {
    return BaseResponse.onSuccess(
        ArtWorkConverter.toApprovedArtWorkResponse(
            artWorkQueryService.getApprovedArtWork(artWorkId)));
  }

  @Operation(summary = "작가별 작품 목록 조회 API", description = "작가별 작품 목록을 조회합니다")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "성공")})
  @GetMapping("/artist/{artistId}")
  @ResponseStatus(HttpStatus.OK)
  public BaseResponse<List<ArtWorkByArtistResponse>> getArtWorksByArtist(
      @Parameter @PathVariable Long artistId) {
    return BaseResponse.onSuccess(
        ArtWorkConverter.toRegArtWorkResponseList(
            artWorkQueryService.getArtWorksByArtist(artistId)));
  }

  @Operation(summary = "작품 등록 취소 API", description = "작품 등록을 취소합니다")
  @ApiResponses({@ApiResponse(responseCode = "204", description = "성공")})
  @DeleteMapping("/registration/cancel/{artWorkId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public BaseResponse<GlobalErrorCode> deleteArtwork(@Parameter @PathVariable Long artWorkId) {
    artWorkCommandService.deleteArtWork(artWorkId);
    return BaseResponse.onSuccess(GlobalErrorCode.DELETED);
  }
}
