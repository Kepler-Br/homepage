package ru.keplerbr.homepage.data.utils;

public class IdBasedUriGenerator {

  private static final char[] charset =
      "Js1C8oAyWhSrcTkGwDZXP-Llfm620EQO7_viFq9INgnutUBdKbV45pazMYxj3eHR".toCharArray();

  private static final long charsetLen = charset.length;

  public static String generate(String seed, long dbId, long minLength) {
    long seedHash = Math.abs(seed.hashCode());
    int cycleCount = 0;
    var stringBuilder = new StringBuilder();

    do {
      int charsetIndex = (int) ((dbId + seedHash + cycleCount) % charsetLen);

      stringBuilder.append(charset[charsetIndex]);
      dbId /= charsetLen;
      cycleCount++;
    } while (dbId > 0);

    while (stringBuilder.length() < minLength) {
      int charsetIndex = (int) ((seedHash + cycleCount) % charsetLen);

      stringBuilder.append(charset[charsetIndex]);
      cycleCount++;
    }

    return stringBuilder.toString();
  }

}
