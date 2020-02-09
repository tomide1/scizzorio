package com.scizzor.scizzorio.exceptions;

public class EmailExistsException extends Exception {
  public EmailExistsException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }
  
  public EmailExistsException(String errorMessage) {
    super(errorMessage);
  }
}
