package ru.keplerbr.homepage.data.utils;

import java.util.Random;

public class Utils {

  private Utils() {
  }

  private static final char[] base64CharSet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_-"
      .toCharArray();

  private static final Random random = new Random();

  private static final int CHARSET_LENGTH = base64CharSet.length;

  public static String base64Random(int length) {
    var stringBuilder = new StringBuilder();

    while (length > 0) {
      var randomIndex = random.nextInt(CHARSET_LENGTH);

      stringBuilder.append(base64CharSet[randomIndex]);
      length--;
    }

    return stringBuilder.reverse().toString();
  }

  public static String intToBase64(int value) {
    var stringBuilder = new StringBuilder();

    while (value > 0) {
      stringBuilder.append(base64CharSet[value % CHARSET_LENGTH]);
      value /= CHARSET_LENGTH;
    }

    return stringBuilder.reverse().toString();
  }

  public static String longToBase64(long value) {
    var stringBuilder = new StringBuilder();

    while (value > 0) {
      stringBuilder.append(base64CharSet[(int) value % CHARSET_LENGTH]);
      value /= CHARSET_LENGTH;
    }

    return stringBuilder.reverse().toString();
  }

}
