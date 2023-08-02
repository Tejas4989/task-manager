package org.demo.task.manager.common.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.demo.task.manager.common.exception.problem.ProblemDetailUtility;
import org.demo.task.manager.common.exception.problem.ProblemStatusExtractor;
import org.demo.task.manager.common.exception.problem.ViolationError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String PROBLEM_SPECIFICATION_KEY = "detail";
  private static final String VCAPS_SPECIFICATION_KEY = "message";
  private final ProblemDetailUtility problemDetailUtility;
  private final ProblemStatusExtractor problemStatusExtractor;
  private final ObjectMapper objectMapper;

  @ExceptionHandler
  public ResponseEntity<Object> handleResponseStatusException(final ResponseStatusException exception,
      final WebRequest request) {
    return this.problemDetailUtility.createProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception, request,
        this.getMessageSource());
  }

  @ExceptionHandler
  public ResponseEntity<Object> handleBadRequestException(final BadRequestException exception,
      final WebRequest request) {
    return this.problemDetailUtility.createProblemDetail(HttpStatus.BAD_REQUEST, exception, request,
        this.getMessageSource());
  }

  @ExceptionHandler
  public ResponseEntity<Object> handleTooManyRequestException(final TooManyRequestException exception,
      final WebRequest request) {
    return this.problemDetailUtility.createProblemDetail(HttpStatus.TOO_MANY_REQUESTS, exception, request,
        this.getMessageSource());
  }

  @ExceptionHandler
  public ResponseEntity<Object> handleValidationException(final ValidationException exception,
      final WebRequest request) {
    return this.problemDetailUtility.createProblemDetail(HttpStatus.BAD_REQUEST, exception, request,
        this.getMessageSource());
  }

  @ExceptionHandler
  public ResponseEntity<Object> handleNumberFormatException(final NumberFormatException exception,
      final WebRequest request) {
    return this.problemDetailUtility.createProblemDetail(HttpStatus.BAD_REQUEST, exception, request,
        this.getMessageSource());
  }

  @ExceptionHandler
  public ResponseEntity<Object> handleExternalResourceException(final ExternalResourceException exception,
      final WebRequest request) {
    return this.problemDetailUtility.createProblemDetail(HttpStatus.SERVICE_UNAVAILABLE, exception, request,
        this.getMessageSource());
  }

  @ExceptionHandler
  public ResponseEntity<Object> handleInternalServerException(final InternalServerException exception,
      final WebRequest request) {
    return this.problemDetailUtility.createProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception, request,
        this.getMessageSource());
  }

  @ExceptionHandler
  public ResponseEntity<Object> handleRestClientException(final RestClientException exception,
      final WebRequest request) {
    if (exception instanceof RestClientResponseException restClientResponseException) {
      HttpStatus status = this.problemStatusExtractor.getByStatusCode(
          restClientResponseException.getStatusCode());
      return this.problemDetailUtility.createProblemDetail(status,
          convertExternalException(restClientResponseException), request, this.getMessageSource());
    } else {
      return this.problemDetailUtility.createProblemDetail(HttpStatus.SERVICE_UNAVAILABLE, exception, request,
          this.getMessageSource());
    }

  }

  @ExceptionHandler
  public ResponseEntity<Object> handleUnsupportedOperationException(final UnsupportedOperationException exception,
      final WebRequest request) {
    return this.problemDetailUtility.createProblemDetail(HttpStatus.NOT_IMPLEMENTED, exception, request,
        this.getMessageSource());
  }

  @ExceptionHandler
  public ResponseEntity<Object> handleMultipartException(final MultipartException exception, final WebRequest request) {
    return this.problemDetailUtility.createProblemDetail(HttpStatus.BAD_REQUEST, exception, request,
        this.getMessageSource());
  }

  @ExceptionHandler
  public ResponseEntity<Object> handleSocketTimeoutException(final SocketTimeoutException exception,
      final WebRequest request) {
    return this.problemDetailUtility.createProblemDetail(HttpStatus.GATEWAY_TIMEOUT, exception, request,
        this.getMessageSource());
  }

  @ExceptionHandler
  public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException exception,
      final WebRequest request) {
    List<ViolationError> violationErrors = exception.getConstraintViolations().stream()
        .map(ViolationError::new).collect(Collectors.toList());
    return this.problemDetailUtility.newConstraintViolationProblemDetail(exception, violationErrors, request,
        this.getMessageSource());
  }

  @ExceptionHandler(ServiceException.class)
  public ResponseEntity<Object> handleService(final ServiceException e, final WebRequest request) {
    return this.problemDetailUtility.createProblemDetail(HttpStatus.SERVICE_UNAVAILABLE, e, request,
        this.getMessageSource());
  }

  @ExceptionHandler(NotFoundException.class)
  ResponseEntity<Object> handleNotFound(final NotFoundException e, final WebRequest request) {
    return this.problemDetailUtility.createProblemDetail(HttpStatus.NOT_FOUND, e, request,
        this.getMessageSource());
  }

  @ExceptionHandler(Exception.class)
  ResponseEntity<Object> handleNotFound(final Exception e, final WebRequest request) {
    // handle the unauthorized exception scenario for authorizationRequired aspect.
    if (e.getClass().getSimpleName().equals("UnauthorizedException")) {
      return this.problemDetailUtility.createProblemDetail(HttpStatus.UNAUTHORIZED, e, request,
          this.getMessageSource());
    }
    // handle the database exception
    if (e.getClass().getSimpleName().equals("DataIntegrityViolationException")) {
      // DataIntegrityViolationException (Spring) encapsulates ConstraintViolationException (Hibernate) that encapsulates PSQLException (Postgres),
      // which is the root cause looked for. To avoid adding spring jpa to this project we are getting the root "manually".
      Throwable temp = e.getCause();
      if (temp != null) {
        while (temp.getCause() != null) {
          temp = temp.getCause();
        }
        return this.problemDetailUtility.createProblemDetail(HttpStatus.CONFLICT, e, request, this.getMessageSource());
      }
    }
    return this.problemDetailUtility.createProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, e, request,
        this.getMessageSource());
  }


  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    final List<ViolationError> violationErrors = ex.getBindingResult().getAllErrors().stream()
        .map(this::toViolationError)
        .collect(Collectors.toList());
    var problemDetail = this.problemDetailUtility.newConstraintViolationProblemDetail(ex, violationErrors, request,
        this.getMessageSource());
    return this.handleExceptionInternal(ex, problemDetail.getBody(), headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
      HttpStatusCode statusCode, WebRequest request) {
    return this.problemDetailUtility.createProblemDetail(ex, body, headers, statusCode, request,
        this.getMessageSource());
  }

  private ViolationError toViolationError(ObjectError e) {
    Object rejectedValue = null;
    String objectName = e.getObjectName();

    if (e instanceof FieldError fieldError) {
      rejectedValue = fieldError.getRejectedValue();
      objectName = fieldError.getField();
    }

    return new ViolationError(e.getCode(), objectName, e.getDefaultMessage(), rejectedValue);
  }

  // If the exception caught is an external exception coming from another service
  // We only capture the detail to simply error message
  private RuntimeException convertExternalException(
      RestClientResponseException restClientResponseException) {
    String exceptionMessage = restClientResponseException.getResponseBodyAsString();
    boolean hasDetailedMessage = !exceptionMessage.isBlank();
    if (hasDetailedMessage) {
      try {
        var externalException = (LinkedHashMap<String, String>) this.objectMapper
            .readValue(exceptionMessage, Object.class);
        String messageProblemSpec = externalException.get(PROBLEM_SPECIFICATION_KEY);
        String messageVcapsSpec = externalException.get(VCAPS_SPECIFICATION_KEY);
        return new ExternalServiceException(!Objects.isNull(messageProblemSpec) ? messageProblemSpec
            : messageVcapsSpec + " Response body: " + restClientResponseException
                .getResponseBodyAsString());
      } catch (IOException notAnExternalException) {
        // The exception occurs when the error is not from a know specification
        // It should fall back to the generic exception
      }
    }
    return new ServiceException(restClientResponseException.getMessage());
  }
}
