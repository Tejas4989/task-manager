package org.demo.task.manager.common.enumeration;

import java.util.Arrays;
import java.util.Objects;

public enum Status {
  NEW,
  PROGRESS,
  COMPLETED,
  INVALID;

  public static Status getByName(String statusName) {
    return Arrays.stream(Status.values()).filter(s -> Objects.equals(s.name(), statusName)).findFirst().orElse(INVALID);
  }
}
