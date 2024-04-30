package com.example.artstoryage.exception.custom;

import com.example.artstoryage.exception.GlobalErrorCode;
import com.example.artstoryage.exception.GlobalException;

public class ArtWorkException extends GlobalException {

  public ArtWorkException(GlobalErrorCode errorCode) {
    super(errorCode);
  }
}
