package org.demo.task.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.demo.task.manager")
public class TaskManagerApplication {

  public static void main(String[] args) {
    SpringApplication.run(TaskManagerApplication.class, args);
  }

}
