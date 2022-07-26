package org.frost.chess;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.frost.chess.gui.GamePanel;
import org.frost.chess.gui.TitleBar;

import javax.swing.*;
import java.awt.*;

@Log4j2
@Getter
public class GUI extends JFrame {
  final static int GUI_WIDTH = 1000;
  final static int GUI_HEIGHT = 900;
  private static Font preferredFont;

  GUI() {
    this.loadFont();

    this.setUndecorated(true);
    this.setLocationRelativeTo(null);
    this.setSize(GUI_WIDTH, GUI_HEIGHT);
    this.centerFrame();

    this.add(new TitleBar(this));
    this.add(new GamePanel());

    this.setVisible(true);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  private void centerFrame() {
    int x = (int) (this.getToolkit().getScreenSize().getWidth() / 2 - this.getWidth() / 2);
    int y = (int) (this.getToolkit().getScreenSize().getHeight() / 2 - this.getHeight() / 2);
    this.setLocation(x, y);
  }

  private void loadFont() {
    try {
      val path = "/font/Roboto-Light.ttf";
      val fontStream = this.getClass().getResourceAsStream(path);
      if(fontStream != null) {
        preferredFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(preferredFont);
      } else {
        log.error("Could not load font from path: {}", path);
      }
    } catch (Exception e) {
      log.error("Could not load font!", e);
    }
  }

  public static Font getPreferredFont(Font fallback) {
    return preferredFont != null ? preferredFont : fallback;
  }

  public static void main(String[] args) {
    log.info("Starting GUI...");
    new GUI();
  }
}
