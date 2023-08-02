package org.demo.task.manager.common.exception.problem;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProblemStatusExtractor {

  public HttpStatus getByStatusCode(HttpStatusCode statusCode) {
    log.trace("Attempting to get status by statusCode: {}.", statusCode);
    for (HttpStatus status : HttpStatus.values()) {
      if (statusCode.value() == status.value()) {
        log.trace("Matched status code: {}, and reasonPhrase: {}.", status.value(), status.getReasonPhrase());
        return status;
      }
    }
    log.debug("Status not found hence throwing illegalArgumentException.");
    throw new IllegalArgumentException(String.format("Status(%s) not found!", statusCode));
  }
}
