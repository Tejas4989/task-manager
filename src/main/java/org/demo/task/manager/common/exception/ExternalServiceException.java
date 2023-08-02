package org.demo.task.manager.common.exception;

public class ExternalServiceException extends RuntimeException {

  private static final long serialVersionUID = -3681632607322599923L;

  public ExternalServiceException(String message) {
    super(message);
  }

  public ExternalServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public ExternalServiceException(Throwable cause) {
    super(cause);
  }
}
