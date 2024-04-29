package com.example.artstoryage.exception.custom;

import com.example.artstoryage.exception.GlobalErrorCode;
import com.example.artstoryage.exception.GlobalException;

public class ArtistException extends GlobalException {

  public ArtistException(GlobalErrorCode errorCode) {
    super(errorCode);
  }
}
