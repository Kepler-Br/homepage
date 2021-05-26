package ru.keplerbr.homepage.graphql.data.model.enumerator;

import lombok.Getter;

@Getter
public enum ErrorType {
  BAD_REQUEST(400),
  UNAUTHORIZED(401),
  FORBIDDEN(403),
  NOT_FOUND(404),
  CONFLICT(409),
  UNSUPPORTED_MEDIA_TYPE(415),
  TOO_MANY_REQUESTS(429);

  private final int code;

  ErrorType(int code) {
    this.code = code;
  }
}
