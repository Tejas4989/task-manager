package org.demo.task.manager.common.exception.problem;

import jakarta.validation.ConstraintViolation;
import java.beans.ConstructorProperties;
import lombok.Getter;

@Getter
public class ViolationError {

  private final String constraint;
  private final String field;
  private final String detail;
  private final Object rejectedValue;

  @ConstructorProperties({"constraint", "field", "detail", "rejectedValue"})
  public ViolationError(final String constraint, final String field, final String detail,
      final Object rejectedValue) {
    this.constraint = constraint;
    this.field = field;
    this.detail = detail;
    this.rejectedValue = rejectedValue;
  }

  public ViolationError(final ConstraintViolation violation) {
    this.constraint = violation.getConstraintDescriptor().getAnnotation().annotationType().getName();
    this.field = violation.getPropertyPath().toString().split("\\.")[1];
    this.detail = violation.getMessage();
    this.rejectedValue = violation.getInvalidValue();
  }

}
