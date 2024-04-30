package com.example.artstoryage.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.artstoryage.converter.ArtistConverter;
import com.example.artstoryage.domain.Artist;
import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.dto.request.ArtistRequestDto.CreateArtistRequest;
import com.example.artstoryage.exception.GlobalErrorCode;
import com.example.artstoryage.exception.custom.ArtistException;
import com.example.artstoryage.repository.ArtistRepository;
import com.example.artstoryage.service.ArtistCommandService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ArtistCommandServiceImpl implements ArtistCommandService {

  private final ArtistRepository artistRepository;

  @Override
  public Artist createArtist(Member member, CreateArtistRequest request) {

    artistRepository
        .findByMember(member)
        .ifPresent(
            artist -> {
              throw new ArtistException(GlobalErrorCode.DUPLICATE_ARTIEST);
            });

    return artistRepository.save(ArtistConverter.toArtist(request, member));
  }
}
