package org.demo.task.manager.domain.validation.contraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.demo.task.manager.domain.validation.validator.TaskStatusValidator;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Constraint(validatedBy = TaskStatusValidator.class)
@Target(ElementType.TYPE)
@Retention(RUNTIME)
@Documented
public @interface TaskStatusConstraint {

    String message() default "Task status has invalid status name, check log for more details";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
