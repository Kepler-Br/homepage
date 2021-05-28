package ru.keplerbr.homepage.graphql.data.model.exception;

import graphql.ErrorType;
import graphql.GraphQLError;

import static ru.keplerbr.homepage.graphql.data.model.enumerator.ErrorType.NOT_FOUND;

public class GraphQLNotFoundException extends GraphQLHomepageException implements GraphQLError {

  public GraphQLNotFoundException() {
    super(NOT_FOUND.getCode());
  }

  public GraphQLNotFoundException(String s) {
    super(s, NOT_FOUND.getCode());
  }

  public GraphQLNotFoundException(String message, Throwable cause) {
    super(message, cause, NOT_FOUND.getCode());
  }

  public GraphQLNotFoundException(Throwable cause) {
    super(cause, NOT_FOUND.getCode());
  }

  @Override
  public ErrorType getErrorType() {
    return ErrorType.DataFetchingException;
  }
}
