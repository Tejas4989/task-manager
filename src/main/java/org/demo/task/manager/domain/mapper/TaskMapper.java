package org.demo.task.manager.domain.mapper;

import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.demo.task.manager.domain.dto.Task;
import org.demo.task.manager.domain.entity.TaskEntity;
import org.demo.task.manager.domain.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class TaskMapper {

  private final ModelMapper modelMapper;

  public Task convertToDTO(TaskEntity taskEntity) {
    Task map = this.modelMapper.map(taskEntity, Task.class);
    if (Objects.nonNull(taskEntity.getUser())) {
      map.setUserId(taskEntity.getUser().getId());
    }
    return map;
  }

  public TaskEntity convertToEntity(Task task) {
    TaskEntity map = this.modelMapper.map(task, TaskEntity.class);
    if (Objects.nonNull(task.getUserId())) {
      UserEntity userEntity = new UserEntity();
      userEntity.setId(task.getUserId());
      map.setUser(userEntity);
    }
    return map;
  }

  public List<Task> convertToDTOs(List<TaskEntity> taskEntities) {
    return taskEntities.stream().map(this::convertToDTO).toList();
  }
  public List<TaskEntity> convertToEntities(List<Task> tasks) {
    return tasks.stream().map(this::convertToEntity).toList();
  }

}
