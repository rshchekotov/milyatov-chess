package org.frost.chess.gui;

import lombok.val;
import org.frost.chess.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TitleBar extends JPanel {
  private final JFrame frame;
  private Point initialLocation;

  public TitleBar(JFrame frame) {
    this.frame = frame;
    setSize(frame.getWidth(), 40);

    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        initialLocation = e.getPoint();
      }
    });

    this.addMouseMotionListener(new MouseAdapter() {
      @Override
      public void mouseDragged(MouseEvent e) {
        val currentX = frame.getLocation().x;
        val currentY = frame.getLocation().y;

        val movedX = e.getX() - initialLocation.x;
        val movedY = e.getY() - initialLocation.y;

        frame.setLocation(currentX + movedX, currentY + movedY);
      }
    });
  }

  @Override
  public void paint(Graphics g) {
    // Back-Up
    Color color = g.getColor();

    g.setColor(new Color(0x485fc7));
    g.fillRect(0, 0, getWidth(), getHeight());

    g.setColor(new Color(0x969696));
    g.setFont(GUI.getPreferredFont(g.getFont()).deriveFont(24f));
    int xPos = getWidth() / 2 - g.getFontMetrics().stringWidth("Chess") / 2;
    int yPos = getHeight() / 2 + g.getFontMetrics().getHeight() / 2;
    g.drawString("Chess", xPos, yPos);

    // Restore
    g.setColor(color);
  }
}
