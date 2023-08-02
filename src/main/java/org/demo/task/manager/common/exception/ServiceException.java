package org.demo.task.manager.common.exception;

public class ServiceException extends RuntimeException {

  private static final long serialVersionUID = -3443425986776919107L;

  public ServiceException(String message) {
    super(message);
  }

  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public ServiceException(Throwable cause) {
    super(cause);
  }
}
