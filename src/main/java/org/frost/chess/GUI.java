package org.frost.chess;

import lombok.Getter;
import org.frost.chess.gui.GamePanel;

import javax.swing.*;

@Getter
public class GUI extends JFrame {
  final static int GUI_SIZE = 900;

  GUI() {
    this.setUndecorated(true);
    this.setLocationRelativeTo(null);
    this.setSize(GUI_SIZE, GUI_SIZE);
    this.add(new GamePanel());
    this.setVisible(true);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  public static void main(String[] args) {
    new GUI();
  }
}
