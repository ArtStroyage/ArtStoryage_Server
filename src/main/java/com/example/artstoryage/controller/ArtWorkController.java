package com.example.artstoryage.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.artstoryage.annotation.auth.AuthMember;
import com.example.artstoryage.common.BaseResponse;
import com.example.artstoryage.converter.ArtWorkConverter;
import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.dto.request.ArtWorkRequestDto.RegArtWorkRequest;
import com.example.artstoryage.dto.response.ArtWorkResponseDto.AllowArtWorkResponse;
import com.example.artstoryage.dto.response.ArtWorkResponseDto.RegArtWorkResponse;
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
@RequestMapping("/api/v1/artworks/registration")
@Tag(name = "🖼️ ArtWork", description = "예술 작품 관련 API")
public class ArtWorkController {

  private final ArtWorkCommandService artWorkCommandService;
  private final ArtWorkQueryService artWorkQueryService;

  @Operation(summary = "입점 신청 API", description = "입점 신청을 진행합니다")
  @ApiResponses({@ApiResponse(responseCode = "201", description = "성공")})
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BaseResponse<RegArtWorkResponse> regArtWork(
      @Parameter(hidden = true) @AuthMember Member member, @RequestBody RegArtWorkRequest request) {
    return BaseResponse.onSuccess(
        GlobalErrorCode.CREATED,
        ArtWorkConverter.toRegArtWorkResponse(artWorkCommandService.regArtWork(member, request)));
  }

  @Operation(summary = "입점 신청 승인 API", description = "입점 신청을 승인합니다")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "성공")})
  @PutMapping("/{artWorkId}")
  @ResponseStatus(HttpStatus.OK)
  public BaseResponse<AllowArtWorkResponse> regArtWork(@Parameter @PathVariable Long artWorkId) {
    return BaseResponse.onSuccess(
        GlobalErrorCode.UPDATED,
        ArtWorkConverter.toAllowArtWorkResponse(artWorkCommandService.allowArtWork(artWorkId)));
  }

  @Operation(summary = "작가별 작품 조회 API", description = "작가별 작품 조회")
  @ApiResponses({@ApiResponse(responseCode = "200", description = "성공")})
  @GetMapping("/{artistId}")
  @ResponseStatus(HttpStatus.OK)
  public BaseResponse<List<RegArtWorkResponse>> getArtWorks(
      @Parameter @PathVariable Long artistId) {
    return BaseResponse.onSuccess(
        ArtWorkConverter.toRegArtWorkResponseList(
            artWorkQueryService.getArtWorksByArtist(artistId)));
  }

  @Operation(summary = "입점 신청 삭제 API", description = "입점 신청을 삭제합니다")
  @ApiResponses({@ApiResponse(responseCode = "204", description = "성공")})
  @DeleteMapping("/{artWorkId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public BaseResponse<GlobalErrorCode> deleteArtwork(@Parameter @PathVariable Long artWorkId) {
    artWorkCommandService.deleteArtWork(artWorkId);
    return BaseResponse.onSuccess(GlobalErrorCode.DELETED);
  }
}
