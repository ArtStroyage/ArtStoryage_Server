package com.example.artstoryage.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.artstoryage.domain.Artist;
import com.example.artstoryage.domain.enums.MemberRole;
import com.example.artstoryage.domain.enums.SocialType;
import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.domain.member.Password;
import com.example.artstoryage.dto.request.ArtistRequestDto.CreateArtistRequest;
import com.example.artstoryage.exception.custom.ArtistException;
import com.example.artstoryage.repository.ArtistRepository;
import com.example.artstoryage.service.impl.ArtistCommandServiceImpl;

public class ArtistCommandServiceImplTest {

  @Mock private ArtistRepository artistRepository;

  @InjectMocks private ArtistCommandServiceImpl artistCommandService;

  private Member member;
  private CreateArtistRequest request;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    member =
        Member.builder()
            .name("name")
            .nickName("nickname")
            .email("email")
            .password(Password.encrypt("Test1234!@#$", new BCryptPasswordEncoder()))
            .phoneNumber("010-0000-0000")
            .socialType(SocialType.COMMON)
            .memberRole(MemberRole.USER)
            .build();

    request =
        CreateArtistRequest.builder()
            .name("name")
            .profileImageLink("link")
            .genre("genre")
            .introduction("intro")
            .history("history")
            .bankBookLink("bank")
            .build();
  }

  @Test
  public void createArtist_success() {

    // Member의 artist null로 반환 설정
    given(artistRepository.findByMember(any(Member.class))).willReturn(Optional.empty());

    // Artist 저장 시 값 반환 설정
    given(artistRepository.save(any(Artist.class)))
        .willAnswer(invocation -> invocation.getArgument(0));

    // 실행
    Artist result = artistCommandService.createArtist(member, request);

    // 검증
    assertNotNull(result);
    assertEquals(request.getName(), result.getName());
  }

  @Test
  void createArtist_WhenExists_ShouldThrowException() {

    // Member의 artist가 있도록 반환
    Artist mockArtist = mock(Artist.class);
    given(artistRepository.findByMember(any(Member.class))).willReturn(Optional.of(mockArtist));

    // 실행 & 검증
    assertThrows(
        ArtistException.class,
        () -> {
          artistCommandService.createArtist(member, request);
        });
  }
}
