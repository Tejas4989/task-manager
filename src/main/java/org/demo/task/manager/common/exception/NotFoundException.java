package org.demo.task.manager.common.exception;

public class NotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1327560175828655866L;

  public NotFoundException(String message) {
    super(message);
  }

  public NotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public NotFoundException(Throwable cause) {
    super(cause);
  }
}
