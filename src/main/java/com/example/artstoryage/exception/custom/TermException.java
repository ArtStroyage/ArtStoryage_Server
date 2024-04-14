package com.example.artstoryage.exception.custom;

import com.example.artstoryage.exception.GlobalErrorCode;
import com.example.artstoryage.exception.GlobalException;

public class TermException extends GlobalException {
  public TermException(GlobalErrorCode errorCode) {
    super(errorCode);
  }
}
