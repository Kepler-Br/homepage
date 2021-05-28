package ru.keplerbr.homepage.graphql.data.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

public class Base64Utils {

  private Base64Utils() {
  }

  public static String encode(String value) {
    if (Objects.isNull(value)) {
      return "";
    }

    byte[] bytes = value.getBytes(StandardCharsets.UTF_8);

    return Base64.getEncoder().encodeToString(bytes);
  }

  public static String decode(String base64) {
    if (Objects.isNull(base64)) {
      return "";
    }

    byte[] bytes = Base64.getDecoder().decode(base64);

    return new String(bytes, StandardCharsets.UTF_8);
  }

  public static Long parseLong(String base64) {
    if (Objects.isNull(base64)) {
      return 0L;
    }

    return Long.parseLong(
        decode(base64)
    );
  }

}
