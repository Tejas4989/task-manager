package org.demo.task.manager.web;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.demo.task.manager.IntegrationTest;
import org.demo.task.manager.service.TaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("Configuration Api Tests")
class TaskControllerTest extends IntegrationTest {

  @Value("/api/tasks")
  private String endpoint;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  private MockMvc mvc;

  @Autowired
  private TaskService taskService;

  @Test
  @DisplayName("Get all tasks non empty")
  void getAllTasks_when_not_empty() throws Exception {
    this.mvc.perform(get(endpoint).accept(MediaType.APPLICATION_JSON)).andDo(print())
        .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(6)));
  }


  @Test
  @DisplayName("Get task by id when it's present")
  void getTask_when_available() throws Exception {
    this.mvc.perform(get(endpoint+ "/{taskId}", 1).accept(MediaType.APPLICATION_JSON)).andDo(print())
        .andExpect(status().isOk()).
        andExpect(jsonPath("$.id", is(1)));
  }

  @Test
  @DisplayName("Get task by id when it's not found.")
  void getTask_when_not_found() throws Exception {
    this.mvc.perform(get(endpoint+ "/{taskId}", 10000000).accept(MediaType.APPLICATION_JSON)).andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Get task by id when it's not valid taskId.")
  void getTask_when_taskId_not_valid() throws Exception {
    this.mvc.perform(get(endpoint+ "/{taskId}", "InvalidId").accept(MediaType.APPLICATION_JSON)).andDo(print())
        .andExpect(status().isBadRequest());
  }
}
