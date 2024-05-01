package com.example.artstoryage.converter;

import org.springframework.stereotype.Component;

import com.example.artstoryage.domain.Outline;
import com.example.artstoryage.domain.Post;
import com.example.artstoryage.domain.enums.PostCategory;
import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.dto.request.PostRequestDto.*;
import com.example.artstoryage.dto.response.PostResponseDto.CreatePostResponse;

@Component
public class PostConverter {

  public static Post toPost(CreatePostRequest request, Member member) {
    return Post.builder()
        .postCategory(PostCategory.POST)
        .title(request.getTitle())
        .content(request.getContent())
        .view(0L)
        .member(member)
        .build();
  }

  public static Post toExhibition(CreateExhibitionRequest request, Member member) {
    return Post.builder()
        .postCategory(PostCategory.EXHIBITION)
        .title(request.getTitle())
        .content(request.getContent())
        .view(0L)
        .member(member)
        .outline(
            Outline.builder()
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .siteUrl(request.getSiteUrl())
                .businessHours(request.getBusinessHours())
                .address(request.getAddress())
                .tags(request.getTags())
                .build())
        .build();
  }

  public static CreatePostResponse toCreatePostResponse(Post post) {
    return CreatePostResponse.builder()
        .postId(post.getId())
        .title(post.getTitle())
        .postCategory(post.getPostCategory())
        .build();
  }
}
