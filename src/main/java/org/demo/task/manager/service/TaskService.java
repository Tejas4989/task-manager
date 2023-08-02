package org.demo.task.manager.service;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.demo.task.manager.common.enumeration.Status;
import org.demo.task.manager.common.exception.NotFoundException;
import org.demo.task.manager.domain.dto.Task;
import org.demo.task.manager.domain.dto.TaskPriorityQueue;
import org.demo.task.manager.domain.dto.TaskStatistics;
import org.demo.task.manager.domain.dto.User;
import org.demo.task.manager.domain.entity.TaskEntity;
import org.demo.task.manager.domain.entity.UserEntity;
import org.demo.task.manager.domain.mapper.TaskMapper;
import org.demo.task.manager.domain.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;
  private final TaskMapper taskMapper;
  private final UserService userService;

  public List<Task> getAllTasks() {
    return this.taskMapper.convertToDTOs(taskRepository.findAll());
  }

  public TaskEntity getTaskEntityById(Long taskId) {
    return taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException(
        "Can't find the record since the entity is not found in the database. taskId: "
            + taskId));
  }
  public Task getTaskById(Long taskId) {
    return this.taskMapper.convertToDTO(getTaskEntityById(taskId));
  }

  @Transactional
  public Task createTask(Task task) {
    TaskEntity taskEntity = taskMapper.convertToEntity(task);
    return this.taskMapper.convertToDTO(taskRepository.save(taskEntity));
  }

  @Transactional
  public Task updateTask(Long taskId, Task task) {
    TaskEntity taskEntity = getTaskEntityById(taskId);
    taskEntity.setTitle(task.getTitle());
    taskEntity.setDescription(task.getDescription());
    taskEntity.setDueDate(task.getDueDate());
    taskEntity.setStatus(task.getStatus());
    //if status is completed then add the completion date
    if (task.getStatus().equals("COMPLETED")) {
      taskEntity.setCompletedDate(new Date(System.currentTimeMillis()));
    }
    return this.taskMapper.convertToDTO(taskRepository.save(taskEntity));
  }

  @Transactional
  public void deleteTask(Long taskId) {
    TaskEntity taskEntity = getTaskEntityById(taskId);
    taskRepository.delete(taskEntity);
  }

  @Transactional
  public Task assignTask(Long taskId, Long userId) {
    UserEntity user = userService.getUserEntityById(userId);
    TaskEntity taskEntity = getTaskEntityById(taskId);
    taskEntity.setUser(user);
    return this.taskMapper.convertToDTO(taskRepository.save(taskEntity));
  }

  @Transactional(readOnly = true)
  public List<Task> getTasksAssignedToUser(Long userId) {
    User user = userService.getUserById(userId);
    return this.taskMapper.convertToDTOs(taskRepository.findByUserId(user.getId()));
  }

  @Transactional
  public Task setTaskProgress(Long taskId, Integer progress) {
    TaskEntity taskEntity = getTaskEntityById(taskId);
    taskEntity.setProgressPercentage(progress);
    return this.taskMapper.convertToDTO(taskRepository.save(taskEntity));
  }

  @Transactional(readOnly = true)
  public List<Task> getOverdueTasks() {
    return this.taskMapper.convertToDTOs(taskRepository.findByDueDateLessThanEqualAndStatusNot(
        System.currentTimeMillis(), "COMPLETED"));
  }

  @Transactional(readOnly = true)
  public List<Task> getTasksByStatus(String status) {
    return this.taskMapper.convertToDTOs(taskRepository.findByStatus(status));
  }

  @Transactional(readOnly = true)
  public List<Task> getCompletedTasksByDateRange(Date startDate, Date endDate) {
    return this.taskMapper.convertToDTOs(taskRepository.findByCompletedDateBetween(startDate, endDate));
  }

  @Transactional(readOnly = true)
  public TaskStatistics getTasksStatistics() {
    List<Task> tasks = this.getAllTasks();
    int totalCompletedTasks = tasks.stream().filter(t -> Objects.equals(t.getStatus(),
        Status.COMPLETED.name())).toList().size();
      return TaskStatistics.builder().totalNumberOfTasks(tasks.size()).totalCompletedTasks(totalCompletedTasks).build();
  }

  @Transactional(readOnly = true)
  public TaskPriorityQueue prepareTasksPriorityQueue() {
    TaskPriorityQueue taskPriorityQueue = new TaskPriorityQueue();
    List<Task> tasks = this.getAllTasks();
    taskPriorityQueue.addTasks(tasks);
    return taskPriorityQueue;
  }
}
