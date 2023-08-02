package org.demo.task.manager.domain.dto;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@OpenAPIDefinition(info = "Model representing an request object of bulk simulation")
public class Task implements Comparable<Task> {

  @Schema(description = "Unique identifier of the task", example = "1", readOnly = true)
  private int id;
  @Schema(description = "Title of the task", example = "Task 1", required = true)
  private String title;
  @Schema(description = "Description of the task", example = "Description of task 1", required = true)
  private String description;
  @Schema(description = "Status of the task", example = "NEW", required = false)
  private String status;
  @Schema(description = "Due date of the task", example = "2020-10-10", required = true)
  private Date dueDate;
  @Schema(description = "Priority of the task", example = "HIGH", required = false, readOnly = true)
  private String priority;
  @Schema(description = "Progress percentage of the task", example = "0",  readOnly = true)
  private int progressPercentage;
  @Schema(description = "Completed date of the task", example = "2020-10-10", readOnly = true)
  private Date completedDate;
  @Schema(description = "User ID of the task", example = "1", readOnly = true)
  private Long userId;

  @Override
  public int compareTo(Task otherTask) {
    // Compare tasks based on their due dates and priority levels
    int dateComparison = dueDate.compareTo(otherTask.getDueDate());
    if (dateComparison != 0) {
      return dateComparison;
    }
    return priority.compareTo(otherTask.getPriority());
  }


}
