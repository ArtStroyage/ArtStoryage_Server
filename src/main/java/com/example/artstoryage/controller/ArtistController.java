package com.example.artstoryage.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.artstoryage.annotation.auth.AuthMember;
import com.example.artstoryage.common.BaseResponse;
import com.example.artstoryage.converter.ArtistConverter;
import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.dto.request.ArtistRequestDto.CreateArtistRequest;
import com.example.artstoryage.dto.response.ArtistResponseDto.CreateArtistResponse;
import com.example.artstoryage.exception.GlobalErrorCode;
import com.example.artstoryage.service.ArtistCommandService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/artist")
@Tag(name = "👩 Artist", description = "작가 관련 API")
public class ArtistController {

  private final ArtistCommandService artistCommandService;

  @Operation(summary = "작가 정보 등록 API", description = "작가 정보를 등록합니다")
  @ApiResponses({@ApiResponse(responseCode = "201", description = "성공")})
  @PostMapping("/")
  @ResponseStatus(HttpStatus.CREATED)
  public BaseResponse<CreateArtistResponse> createArtist(
      @Parameter(hidden = true) @AuthMember Member member,
      @RequestBody CreateArtistRequest request) {
    return BaseResponse.onSuccess(
        GlobalErrorCode.CREATED,
        ArtistConverter.toCreateArtistResponse(artistCommandService.createArtist(member, request)));
  }
}
