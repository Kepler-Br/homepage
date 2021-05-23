package ru.keplerbr.homepage.data.utils;

public class IdBasedUriGenerator {

  private static final char[] charset =
      "Js1C8oAyWhSrcTkGwDZXP-Llfm620EQO7_viFq9INgnutUBdKbV45pazMYxj3eHR".toCharArray();

  private static final long CHARSET_LEN = charset.length;

  private IdBasedUriGenerator() {
  }

  public static String generate(String seed, long dbId, long minLength) {
    long seedHash = Math.abs(seed.hashCode());
    int cycleCount = 0;
    var stringBuilder = new StringBuilder();

    do {
      int charsetIndex = (int) ((dbId + seedHash + cycleCount) % CHARSET_LEN);

      stringBuilder.append(charset[charsetIndex]);
      dbId /= CHARSET_LEN;
      cycleCount++;
    } while (dbId > 0);

    while (stringBuilder.length() < minLength) {
      int charsetIndex = (int) ((seedHash + cycleCount) % CHARSET_LEN);

      stringBuilder.append(charset[charsetIndex]);
      cycleCount++;
    }

    return stringBuilder.toString();
  }

}
