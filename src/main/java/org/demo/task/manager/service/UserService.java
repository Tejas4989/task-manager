package org.demo.task.manager.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.demo.task.manager.common.exception.NotFoundException;
import org.demo.task.manager.domain.dto.Task;
import org.demo.task.manager.domain.dto.User;
import org.demo.task.manager.domain.entity.TaskEntity;
import org.demo.task.manager.domain.entity.UserEntity;
import org.demo.task.manager.domain.mapper.TaskMapper;
import org.demo.task.manager.domain.mapper.UserMapper;
import org.demo.task.manager.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;


  public List<User> getAllUsers() {
    return this.userMapper.convertToDTOs(userRepository.findAll());
  }

  public User createUser(User user) {
    UserEntity userEntity = userMapper.convertToEntity(user);
    return this.userMapper.convertToDTO(userRepository.save(userEntity));
  }

  public UserEntity getUserEntityById(Long userId) {
  return this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException(
        "Can't find the record since the entity is not found in the database. userId: "
            + userId));
  }

  public User getUserById(Long userId) {
    return this.userMapper.convertToDTO(getUserEntityById(userId));
  }

}
