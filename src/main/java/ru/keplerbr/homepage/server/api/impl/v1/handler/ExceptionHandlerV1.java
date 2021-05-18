package ru.keplerbr.homepage.server.api.impl.v1.handler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.keplerbr.homepage.data.model.response.ErrorResponse;
import ru.keplerbr.homepage.data.model.enumerator.ErrorType;
import ru.keplerbr.homepage.data.model.exception.AlreadyExistsException;
import ru.keplerbr.homepage.data.model.exception.NotFoundException;

@RestControllerAdvice(basePackages = "ru.keplerbr.homepage.server.api.impl.v1")
public class ExceptionHandlerV1 {

  @ExceptionHandler(value = NotFoundException.class)
  public ResponseEntity<ErrorResponse> notFoundException(NotFoundException ex) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(new ErrorResponse(ex.getDisplayedMessage(), ex.getErrorCode()));
  }

  @ExceptionHandler(value = AlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> alreadyExistsException(AlreadyExistsException ex) {
    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(new ErrorResponse(ex.getDisplayedMessage(), ex.getErrorCode()));
  }

  @ExceptionHandler(value = HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> alreadyExistsException(HttpMessageNotReadableException ex) {
    ErrorResponse errorResponse;
    String message;

    if (ex.getCause() instanceof JsonParseException) {
      JsonParseException jsonParseException = (JsonParseException) ex.getCause();

      message = jsonParseException.getOriginalMessage();
    } else if (ex.getCause() instanceof InvalidFormatException) {
      InvalidFormatException invalidFormatException = (InvalidFormatException) ex.getCause();
      String fieldName = invalidFormatException.getPath().get(0).getFieldName();

      message = String.format("Invalid field: '%s'. Probably invalid enum. Check documentation.", fieldName);
    } else {
      message = "UNCHECHED ERROR: " + ex.getMessage();
    }

    errorResponse = new ErrorResponse(message, ErrorType.BAD_REQUEST.getCode());

    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(errorResponse);
  }
}
