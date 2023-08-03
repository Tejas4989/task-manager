package org.demo.task.manager.domain.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.demo.task.manager.common.enumeration.Status;
import org.demo.task.manager.domain.dto.Task;
import org.demo.task.manager.domain.validation.constraint.TaskStatusConstraint;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskStatusValidator implements ConstraintValidator<TaskStatusConstraint, Task> {
    @Override
    public void initialize(TaskStatusConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Task task, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(task.getStatus())) {
            //set the default status
            task.setStatus(Status.NEW.name());
        }
        Status taskStatus = Status.getByName(task.getStatus());
        switch (taskStatus) {
            case COMPLETED, PROGRESS, NEW -> {
            }
            default -> {
                String message = "unexpected status : " + task.getStatus()
                        + ". Excepted status: NEW/PROGRESS/COMPLETED";
               log.error(message);
                return false;
            }
        }

        return true;
    }
}
