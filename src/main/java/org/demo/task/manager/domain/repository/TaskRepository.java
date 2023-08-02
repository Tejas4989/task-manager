package org.demo.task.manager.domain.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.demo.task.manager.domain.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

  Optional<TaskEntity> findById(Long taskId);

  List<TaskEntity> findTasksByDueDate(Date dueDate);

  List<TaskEntity> findByUserId(Long userId);

  List<TaskEntity> findByDueDateLessThanEqualAndStatusNot(long l, String completed);

  List<TaskEntity> findByStatus(String status);

  List<TaskEntity> findByCompletedDateBetween(Date startDate, Date endDate);
}
