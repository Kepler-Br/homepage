package ru.keplerbr.homepage.graphql.data.model.exception;

import static ru.keplerbr.homepage.graphql.data.model.enumerator.ErrorType.BAD_REQUEST;

import graphql.ErrorType;
import graphql.GraphQLError;

public class GraphQLIllegalArgumentException extends GraphQLHomepageException implements
    GraphQLError {

  public GraphQLIllegalArgumentException() {
    super(BAD_REQUEST.getCode());
  }

  public GraphQLIllegalArgumentException(String s) {
    super(s, BAD_REQUEST.getCode());
  }

  public GraphQLIllegalArgumentException(String message, Throwable cause) {
    super(message, cause, BAD_REQUEST.getCode());
  }

  public GraphQLIllegalArgumentException(Throwable cause) {
    super(cause, BAD_REQUEST.getCode());
  }

  @Override
  public ErrorType getErrorType() {
    return ErrorType.ValidationError;
  }

}
