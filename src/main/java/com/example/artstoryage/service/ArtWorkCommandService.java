package com.example.artstoryage.service;

import com.example.artstoryage.domain.ArtWork;
import com.example.artstoryage.dto.request.ArtWorkRequestDto.*;

public interface ArtWorkCommandService {

  ArtWork regArtWork(RegArtWorkRequest request);
}
