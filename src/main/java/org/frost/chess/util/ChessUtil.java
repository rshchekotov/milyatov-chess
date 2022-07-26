package org.frost.chess.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ChessUtil {
  public static boolean inRange(int value, int min, int max) {
    return value >= min && value <= max;
  }
}
