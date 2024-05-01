package com.example.artstoryage.service;

import com.example.artstoryage.domain.Post;
import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.dto.request.PostRequestDto;

public interface PostCommandService {

  Post createPost(PostRequestDto.CreatePostRequest request, Member member);

  Post createExhibition(PostRequestDto.CreateExhibitionRequest request, Member member);
}
