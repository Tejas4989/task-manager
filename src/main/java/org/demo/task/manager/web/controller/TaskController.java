package org.demo.task.manager.web.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.demo.task.manager.domain.dto.Task;
import org.demo.task.manager.domain.dto.TaskPriorityQueue;
import org.demo.task.manager.domain.dto.TaskStatistics;
import org.demo.task.manager.service.TaskService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8090")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
class TaskController {

  private final TaskService taskService;

  //1. Create a Task.
  @PostMapping("/tasks")
  public Task createTask(Task task) {
    return taskService.createTask(task);
  }

  //2. Update Task.
  @PutMapping("/tasks/{taskId}")
  public Task updateTask(@PathVariable @NotNull Long taskId,
      @RequestBody @NotNull Task task) {
    return taskService.updateTask(taskId, task);
  }

  //3. Delete Task.
  @DeleteMapping("/tasks/{taskId}")
  public void deleteTask(@PathVariable @NotNull Long taskId) {
    taskService.deleteTask(taskId);
  }

  //4. Get all tasks.
  @GetMapping("/tasks")
  public List<Task> getAllTasks() {
    return taskService.getAllTasks();
  }

  //5. Assign a task to a user
  @PutMapping("/tasks/{taskId}/assign/{userId}")
  public Task assignTask(@PathVariable @NotNull Long taskId, @PathVariable @NotNull Long userId) {
    return taskService.assignTask(taskId, userId);
  }

  //6. Get all tasks assigned to a user
  @GetMapping("/tasks/assign/{userId}")
  public List<Task> getTasksAssignedToUser(@PathVariable @NotNull Long userId) {
    return taskService.getTasksAssignedToUser(userId);
  }

  //7. Set task progress:
  @PutMapping("/tasks/{taskId}/progress/{progress}")
  public Task setTaskProgress(@PathVariable @NotNull Long taskId,
      @PathVariable @NotNull @Min(1) @Max(100) Integer progress) {
    return taskService.setTaskProgress(taskId, progress);
  }

  //8. Get overdue tasks
  @GetMapping("/tasks/overdue")
  public List<Task> getOverdueTasks() {
    return taskService.getOverdueTasks();
  }

  //9.  Get tasks by status
  @GetMapping("/tasks/status/{status}")
  public List<Task> getTasksByStatus(@PathVariable @NotNull String status) {
    return taskService.getTasksByStatus(status);
  }

  //10. Get completed tasks by date range
  @GetMapping("/tasks/completed/{startDate}/{endDate}")
  public List<Task> getCompletedTasksByDateRange(@PathVariable @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
      @PathVariable @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
    return taskService.getCompletedTasksByDateRange(Date.valueOf(startDate), Date.valueOf(endDate));
  }

  //11. Get tasks statistics
  @GetMapping("/tasks/statistics")
  public TaskStatistics getTasksStatistics() {
    return taskService.getTasksStatistics();
  }

  //12. Priority and due date based tasks queue
  @GetMapping("/tasks/queue")
  public void getTasksQueue() {
    TaskPriorityQueue priorityQueue = this.taskService.prepareTasksPriorityQueue();
    // Retrieve tasks in the priority order until the queue is empty
    while (!priorityQueue.isEmpty()) {
      Task nextTask = priorityQueue.getNextTask();
    log.info("Next Task: {}, Due Date: {}, Priority: {}",
        nextTask.getTitle(), nextTask.getDueDate(), nextTask.getPriority());
    }
  }

  //13. Get task by id
  @GetMapping("/tasks/{taskId}")
  public Task getTaskById(@PathVariable @NotNull @Min(1L) Long taskId) {
    return taskService.getTaskById(taskId);
  }

}
