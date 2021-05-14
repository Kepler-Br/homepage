package ru.keplerbr.homepage.data.model.exception;

public abstract class NotFoundException extends BusinessRuntimeException {
  public final static int ERROR_CODE = ErrorType.NOT_FOUND.getCode();

  NotFoundException(String message, String displayedMessage) {
    super(message, displayedMessage, ERROR_CODE);
  }

  NotFoundException(String message) {
    super(message, ERROR_CODE);
  }
}
