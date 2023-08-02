package org.demo.task.manager.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@OpenAPIDefinition(info = "Model representing an request object of bulk simulation")
public class User {

  @Schema(description = "Unique identifier of the user", example = "1", readOnly = true)
  private Long id;
  @Schema(description = "Name of the user", example = "John Doe", required = true)
  private String name;
}
