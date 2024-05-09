package com.example.artstoryage.exception.custom;

import com.example.artstoryage.exception.GlobalErrorCode;
import com.example.artstoryage.exception.GlobalException;

public class S3Exception extends GlobalException {
  public S3Exception(GlobalErrorCode errorCode) {
    super(errorCode);
  }
}
