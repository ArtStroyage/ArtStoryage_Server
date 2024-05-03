package com.example.artstoryage.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.artstoryage.domain.Post;
import com.example.artstoryage.domain.enums.MemberRole;
import com.example.artstoryage.domain.enums.SocialType;
import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.domain.member.Password;
import com.example.artstoryage.dto.request.PostRequestDto;
import com.example.artstoryage.repository.PostRepository;
import com.example.artstoryage.service.impl.PostCommandServiceImpl;

class PostCommandServiceImplTest {
  @Mock private PostRepository postRepository;
  @InjectMocks private PostCommandServiceImpl postCommandService;

  private Member member;

  private PostRequestDto.CreatePostRequest createPostRequest;
  private PostRequestDto.CreateExhibitionRequest createExhibitionRequest;

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
  }

  @Test
  void createPost_success() {
    createPostRequest =
        PostRequestDto.CreatePostRequest.builder().title("title").content("content").build();
    // Exhibition(Post) 저장 시 값 반환 설정
    given(postRepository.save(any(Post.class))).willAnswer(invocation -> invocation.getArgument(0));
    Post result = postCommandService.createPost(createPostRequest, member);
    assertNotNull(result);
    assertEquals(result.getTitle(), createPostRequest.getTitle());
    assertEquals(result.getTitle(), createPostRequest.getTitle());
  }

  @Test
  void createExhibition() {
    createExhibitionRequest =
        PostRequestDto.CreateExhibitionRequest.builder()
            .title("title")
            .content("content")
            .startDate("2024-01-01")
            .endDate("2024-12-31")
            .siteUrl("www.aaa.com")
            .businessHours("0시부터 12시까지")
            .address("서울시 중구")
            .tags("#을지로")
            .build();
    // Exhibition(Post) 저장 시 값 반환 설정
    given(postRepository.save(any(Post.class))).willAnswer(invocation -> invocation.getArgument(0));
    Post result = postCommandService.createExhibition(createExhibitionRequest, member);
    assertNotNull(result);
    assertEquals(result.getTitle(), createExhibitionRequest.getTitle());
    assertEquals(result.getOutline().getStartDate(), createExhibitionRequest.getStartDate());
  }
}
