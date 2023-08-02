package org.demo.task.manager.common.exception;

public class BadRequestException extends RuntimeException {

  private static final long serialVersionUID = -2681636607322599994L;

  public BadRequestException(String message) {
    super(message);
  }

  public BadRequestException(String message, Throwable cause) {
    super(message, cause);
  }

  public BadRequestException(Throwable cause) {
    super(cause);
  }
}
