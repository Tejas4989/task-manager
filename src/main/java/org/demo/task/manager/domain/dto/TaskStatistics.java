package org.demo.task.manager.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@OpenAPIDefinition(info = "Model representing an request object of bulk simulation")
public class TaskStatistics {

  private Integer totalNumberOfTasks;
  private Integer totalCompletedTasks;
//  private Double percentageOfCompletedTasks;

  // find the percentage of completed tasks
  public Double getPercentageOfCompletedTasks() {
    // Ensure that the total number of tasks is not zero to avoid division by zero error
    if (totalNumberOfTasks == 0) {
      throw new IllegalArgumentException("Total number of tasks cannot be zero.");
    }
    // Calculate the percentage value
    return (double) totalCompletedTasks / totalNumberOfTasks * 100;
  }
}
