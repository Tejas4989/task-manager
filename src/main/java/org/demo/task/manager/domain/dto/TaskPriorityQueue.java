package org.demo.task.manager.domain.dto;
import java.util.List;
import java.util.PriorityQueue;

public class TaskPriorityQueue {
  private final PriorityQueue<Task> priorityQueue;

  public TaskPriorityQueue() {
    // Initialize the priority queue with a custom comparator based on task's compareTo method
    priorityQueue = new PriorityQueue<>();
  }

  public void addTask(Task task) {
    priorityQueue.add(task);
  }

  public void addTasks(List<Task> tasks) {
    priorityQueue.addAll(tasks);
  }

  public Task getNextTask() {
    // Retrieves and removes the head of the queue (task with the highest priority)
    return priorityQueue.poll();
  }

  public boolean isEmpty() {
    return priorityQueue.isEmpty();
  }
}

