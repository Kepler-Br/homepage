package ru.keplerbr.homepage.graphql.handler;

import graphql.GraphQLException;
import graphql.kickstart.spring.error.ThrowableGraphQLError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.keplerbr.homepage.graphql.data.model.exception.GraphQLHomepageException;

@RestControllerAdvice(basePackages = "ru.keplerbr.homepage.server.api.impl.v1")
@Slf4j
public class ExceptionHandlerV1 {

  @ExceptionHandler(GraphQLHomepageException.class)
  public GraphQLHomepageException notFoundException(GraphQLHomepageException ex) {
    return ex;
  }

  @ExceptionHandler(GraphQLException.class)
  public ThrowableGraphQLError graphQLException(GraphQLException ex) {
    return new ThrowableGraphQLError(ex);
  }

//  @ExceptionHandler(GraphQLNotFoundException.class)
//  public GraphQLNotFoundException illegalArgument(GraphQLNotFoundException ex) {
//    return ex;
//  }

  @ExceptionHandler(RuntimeException.class)
  public ThrowableGraphQLError internalError(RuntimeException ex) {
    log.error(ex.getMessage());

    return new ThrowableGraphQLError(ex, "Internal server error");
  }

}
