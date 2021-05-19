package ru.keplerbr.homepage.server.api.impl.v1.handler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.util.Objects;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.keplerbr.homepage.data.model.response.ConstraintViolationResponse;
import ru.keplerbr.homepage.data.model.response.ErrorResponse;
import ru.keplerbr.homepage.data.model.enumerator.ErrorType;
import ru.keplerbr.homepage.data.model.exception.AlreadyExistsException;
import ru.keplerbr.homepage.data.model.exception.NotFoundException;

@RestControllerAdvice(basePackages = "ru.keplerbr.homepage.server.api.impl.v1")
@Slf4j
public class ExceptionHandlerV1 {

  @ExceptionHandler(value = NotFoundException.class)
  public ResponseEntity<ErrorResponse> notFoundException(NotFoundException ex) {
    log.info(ex.getMessage());

    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(new ErrorResponse(ex.getDisplayedMessage(), ex.getErrorCode()));
  }

  @ExceptionHandler(value = AlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> alreadyExistsException(AlreadyExistsException ex) {
    log.info(ex.getMessage());

    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(new ErrorResponse(ex.getDisplayedMessage(), ex.getErrorCode()));
  }

  @ExceptionHandler(value = HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> alreadyExistsException(HttpMessageNotReadableException ex) {
    ErrorResponse errorResponse;
    String message;

    log.info(ex.getMessage());

    if (ex.getCause() instanceof JsonParseException) {
      JsonParseException jsonParseException = (JsonParseException) ex.getCause();

      message = jsonParseException.getOriginalMessage();
    } else if (ex.getCause() instanceof InvalidFormatException) {
      InvalidFormatException invalidFormatException = (InvalidFormatException) ex.getCause();
      String fieldName = invalidFormatException.getPath().get(0).getFieldName();

      message = String
          .format("Invalid field: '%s'. Probably invalid enum. Check documentation.", fieldName);
    } else {
      String exceptionMessage = ex.getMessage() == null ? "" : ex.getMessage();

      if (exceptionMessage.contains("Required request body is missing")) {
        message = "Required request body is missing.";
      } else {
        message = "Request is not readable.";
      }
    }

    errorResponse = new ErrorResponse(message, ErrorType.BAD_REQUEST.getCode());

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(errorResponse);
  }

  @ExceptionHandler(value = {ConstraintViolationException.class})
  public ResponseEntity<Object> constraintViolationException(
      ConstraintViolationException ex) {
    var response = new ConstraintViolationResponse(
        "Constraint violation.",
        ErrorType.BAD_REQUEST.getCode());
    ex.getConstraintViolations().forEach((violation) -> {
      String fieldName = null;

      for (var node : violation.getPropertyPath()) {
        fieldName = node.getName();
      }
      response.addViolatedField(fieldName, violation.getMessage());
    });

    return ResponseEntity.badRequest().body(response);
  }
}
