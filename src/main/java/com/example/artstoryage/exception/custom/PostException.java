package com.example.artstoryage.exception.custom;

import com.example.artstoryage.exception.GlobalErrorCode;
import com.example.artstoryage.exception.GlobalException;

public class PostException extends GlobalException {

  public PostException(GlobalErrorCode errorCode) {
    super(errorCode);
  }
}
