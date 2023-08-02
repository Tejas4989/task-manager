package org.demo.task.manager.common.exception;

public class ExternalResourceException extends RuntimeException {

  private static final long serialVersionUID = -2631632607322599923L;

  public ExternalResourceException(String message) {
    super(message);
  }

  public ExternalResourceException(String message, Throwable cause) {
    super(message, cause);
  }

  public ExternalResourceException(Throwable cause) {
    super(cause);
  }
}
