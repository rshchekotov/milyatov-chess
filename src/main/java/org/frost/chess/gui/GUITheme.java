package org.frost.chess.gui;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum GUITheme {
  MILYATOV("milyatov"),
  CLASSIC("classic");

  @Setter
  @SuppressWarnings("NonFinalFieldInEnum")
  private static GUITheme currentTheme = CLASSIC;
  private final String name;

  GUITheme(String name) {
    this.name = name;
  }

  public static String getCurrentTheme() {
    return currentTheme.getName();
  }
}
