package com.example.artstoryage.exception.custom;

import com.example.artstoryage.exception.GlobalErrorCode;
import com.example.artstoryage.exception.GlobalException;

public class TokenException extends GlobalException {
  public TokenException(GlobalErrorCode errorCode) {
    super(errorCode);
  }
}
