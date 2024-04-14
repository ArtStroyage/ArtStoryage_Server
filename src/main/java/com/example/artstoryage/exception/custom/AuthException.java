package com.example.artstoryage.exception.custom;

import com.example.artstoryage.exception.GlobalErrorCode;
import com.example.artstoryage.exception.GlobalException;

public class AuthException extends GlobalException {

  public AuthException(GlobalErrorCode errorCode) {
    super(errorCode);
  }
}
