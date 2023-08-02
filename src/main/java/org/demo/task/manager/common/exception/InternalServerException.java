package org.demo.task.manager.common.exception;

public class InternalServerException extends RuntimeException {

  private static final long serialVersionUID = -3641636607322599994L;

  public InternalServerException(String message) {
    super(message);
  }

  public InternalServerException(String message, Throwable cause) {
    super(message, cause);
  }

  public InternalServerException(Throwable cause) {
    super(cause);
  }
}
