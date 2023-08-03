package org.demo.task.manager.domain.repository;

import java.util.List;
import org.demo.task.manager.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  List<UserEntity> findAll();

}
