package org.demo.task.manager.domain.mapper;

import java.util.List;
import lombok.AllArgsConstructor;
import org.demo.task.manager.domain.dto.User;
import org.demo.task.manager.domain.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserMapper {

  private final ModelMapper modelMapper;

  public User convertToDTO(UserEntity userEntity) {
    return this.modelMapper.map(userEntity, User.class);
  }

  public UserEntity convertToEntity(User user) {
    return this.modelMapper.map(user, UserEntity.class);
  }

  public List<User> convertToDTOs(List<UserEntity> userEntities) {
    return userEntities.stream().map(this::convertToDTO).toList();
  }
  public List<UserEntity> convertToEntities(List<User> users) {
    return users.stream().map(this::convertToEntity).toList();
  }

}
