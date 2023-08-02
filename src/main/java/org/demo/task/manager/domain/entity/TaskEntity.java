package org.demo.task.manager.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "task", schema = "task_manager")
@Data
public class TaskEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "due_date")
  private Date dueDate;

  @Column(name = "completed_date")
  private Date completedDate;

  @Column(name = "status")
  private String status;

  @Column(name = "priority")
  private String priority;
/*
  @Column(name = "user_id")
  private Long userId;*/

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  @EqualsAndHashCode.Exclude
  private UserEntity user;

  @Column(name = "progress_percentage")
  private Integer progressPercentage;

}
