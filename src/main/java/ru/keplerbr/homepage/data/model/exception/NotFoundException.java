package ru.keplerbr.homepage.data.model.exception;

public abstract class NotFoundException extends BusinessRuntimeException {

  NotFoundException(String message, String displayedMessage, int errorCode) {
    super(message, displayedMessage, errorCode);
  }

  NotFoundException(String message, int errorCode) {
    super(message, errorCode);
  }
}
