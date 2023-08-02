package org.demo.task.manager.common.exception.problem;

import jakarta.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponse;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

@Slf4j
@Component
public class ProblemDetailUtility {

  public static final String PROPERTY_STACKTRACE = "stacktrace";
  private static final URI UNRESOLVED_TYPE = URI.create("about:unresolved");
  private static final String HTTP_STATUS_URI = "https://developer.mozilla.org/en-US/docs/Web/HTTP/Status";
  private final boolean includeStacktrace;

  public ProblemDetailUtility(@Value("${service.error.includeStacktrace}") boolean includeStacktrace) {
    this.includeStacktrace = includeStacktrace;
  }

  public ResponseEntity<Object> createProblemDetail(final HttpStatus status, final Exception e,
      final WebRequest request,
      final MessageSource messageSource) {
    return createProblemDetail(status, e, request, new HttpHeaders(), messageSource);
  }

  public ResponseEntity<Object> createProblemDetail(final HttpStatus status, final Exception e,
      final WebRequest request, final HttpHeaders headers,
      final MessageSource messageSource) {
    log.error(String.format("Status: %s - %s, Message: %s", status.value(), status.getReasonPhrase(),
        e.getMessage()), e);
    var pd = ProblemDetail.forStatusAndDetail(status, e.getMessage());
    if (status == HttpStatus.INTERNAL_SERVER_ERROR) {
      request.setAttribute("javax.servlet.error.exception", e, 0);
    }
    return this.createProblemDetail(e, pd, headers, status, request, messageSource);
  }

  public ResponseEntity<Object> newConstraintViolationProblemDetail(final Exception e,
      final List<ViolationError> violationErrors,
      final WebRequest request,
      final MessageSource messageSource) {
    var pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Constraint Violations");
    pd.setTitle("Constraint Violation");
    pd.setProperty("violations", violationErrors);
    return createProblemDetail(e, pd, new HttpHeaders(), HttpStatus.BAD_REQUEST, request, messageSource);
  }

  public URI extractTypeFromStatus(HttpStatusCode statusCode) {
    try {
      return new URI(String.format(HTTP_STATUS_URI + "/%s", statusCode.value()));
    } catch (URISyntaxException e) {
      return UNRESOLVED_TYPE;
    }
  }

  @Nullable
  public ResponseEntity<Object> createProblemDetail(Exception ex, Object body,
      HttpHeaders headers, HttpStatusCode statusCode,
      WebRequest request, MessageSource messageSource) {

    if (request instanceof ServletWebRequest servletWebRequest) {
      HttpServletResponse response = servletWebRequest.getResponse();
      if (response != null && response.isCommitted()) {
        if (log.isWarnEnabled()) {
          log.warn("Response already committed. Ignoring: " + ex);
        }
        return null;
      }
    }

    if (statusCode.equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
      request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
    }

    if (body == null && ex instanceof ErrorResponse errorResponse) {
      body = errorResponse.updateAndGetBody(messageSource, LocaleContextHolder.getLocale());
    }

    if (Objects.isNull(body)) {
      body = ProblemDetail.forStatus(statusCode);
    }

    if (this.includeStacktrace) {
      ((ProblemDetail) body).setProperty(PROPERTY_STACKTRACE, Arrays.stream(ex.getStackTrace())
          .map(StackTraceElement::toString).collect(Collectors.toList()));
    }

    if (body instanceof ProblemDetail) {
      ((ProblemDetail) body).setType(extractTypeFromStatus(statusCode));
    }

    return new ResponseEntity<>(body, headers, statusCode);
  }
}
