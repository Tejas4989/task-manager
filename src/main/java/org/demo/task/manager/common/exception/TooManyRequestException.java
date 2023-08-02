package org.demo.task.manager.common.exception;

public class TooManyRequestException extends RuntimeException {

  private static final long serialVersionUID = -7791767670214498062L;

  public TooManyRequestException(String message) {
    super(message);
  }

  public TooManyRequestException(String message, Throwable cause) {
    super(message, cause);
  }

  public TooManyRequestException(Throwable cause) {
    super(cause);
  }
}
