package ru.keplerbr.homepage.data.model.exception;

import lombok.Getter;

@Getter
public abstract class BusinessRuntimeException extends RuntimeException {
  protected final int errorCode;
  protected final String displayedMessage;

  BusinessRuntimeException(String message, String displayedMessage, int errorCode) {
    super(message);

    this.errorCode = errorCode;
    this.displayedMessage = displayedMessage;
  }

  BusinessRuntimeException(String message, int errorCode) {
    super(message);

    this.errorCode = errorCode;
    this.displayedMessage = message;
  }
}
