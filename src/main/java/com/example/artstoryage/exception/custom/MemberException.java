package com.example.artstoryage.exception.custom;

import com.example.artstoryage.exception.GlobalErrorCode;
import com.example.artstoryage.exception.GlobalException;

public class MemberException extends GlobalException {

  public MemberException(GlobalErrorCode errorCode) {
    super(errorCode);
  }
}
