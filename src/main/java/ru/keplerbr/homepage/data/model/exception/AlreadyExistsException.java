package ru.keplerbr.homepage.data.model.exception;

import ru.keplerbr.homepage.data.model.enumerator.ErrorType;

public class AlreadyExistsException extends BusinessRuntimeException {
    public final static int ERROR_CODE = ErrorType.CONFLICT.getCode();

    public AlreadyExistsException(String message, String displayedMessage) {
        super(message, displayedMessage, ERROR_CODE);
    }

    public AlreadyExistsException(String message) {
        super(message, ERROR_CODE);
    }
}