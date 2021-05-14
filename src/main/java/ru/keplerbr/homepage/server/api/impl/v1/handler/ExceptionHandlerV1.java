package ru.keplerbr.homepage.server.api.impl.v1.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.keplerbr.homepage.data.model.ErrorResponse;
import ru.keplerbr.homepage.data.model.exception.NotFoundException;

@RestControllerAdvice(basePackages = "ru.keplerbr.homepage.server.api.impl.v1")
public class ExceptionHandlerV1 {

  @ExceptionHandler(value = NotFoundException.class)
  public ResponseEntity<ErrorResponse> tagNotFoundException(NotFoundException e) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(new ErrorResponse(e.getMessage(), e.getErrorCode()));
  }
}
