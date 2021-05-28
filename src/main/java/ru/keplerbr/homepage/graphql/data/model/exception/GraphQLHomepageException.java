package ru.keplerbr.homepage.graphql.data.model.exception;

import graphql.GraphQLError;
import graphql.language.SourceLocation;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class GraphQLHomepageException extends RuntimeException implements
    GraphQLError {

  private final transient Map<String, Object> extensions;

  public GraphQLHomepageException(int code) {
    super();

    extensions = Map.of("code", code);
  }

  public GraphQLHomepageException(String s, int code) {
    super(s);

    extensions = Map.of("code", code);
  }

  public GraphQLHomepageException(String message, Throwable cause, int code) {
    super(message, cause);

    extensions = Map.of("code", code);
  }

  public GraphQLHomepageException(Throwable cause, int code) {
    super(cause);

    extensions = Map.of("code", code);
  }

  @Override
  public List<SourceLocation> getLocations() {
    return Collections.emptyList();
  }

  @Override
  public Map<String, Object> getExtensions() {
    return extensions;
  }

}
