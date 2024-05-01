package com.example.artstoryage.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.artstoryage.converter.PostConverter;
import com.example.artstoryage.domain.Post;
import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.dto.request.PostRequestDto;
import com.example.artstoryage.dto.request.PostRequestDto.CreatePostRequest;
import com.example.artstoryage.repository.PostRepository;
import com.example.artstoryage.service.PostCommandService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PostCommandServiceImpl implements PostCommandService {

  private final PostRepository postRepository;

  @Override
  public Post createPost(CreatePostRequest request, Member member) {
    return postRepository.save(PostConverter.toPost(request, member));
  }

  @Override
  public Post createExhibition(PostRequestDto.CreateExhibitionRequest request, Member member) {
    return postRepository.save(PostConverter.toExhibition(request, member));
  }
}
