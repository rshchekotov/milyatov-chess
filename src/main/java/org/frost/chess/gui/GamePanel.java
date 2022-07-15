package org.frost.chess.gui;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.frost.chess.Board;
import org.frost.chess.piece.ChessPiece;
import org.frost.chess.piece.Pawn;
import org.frost.chess.util.Vector2i;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Getter
public class GamePanel extends JPanel {
  final Board board;
  final int pieceSize = 100;
  final int guiSize = 9 * pieceSize;
  final int offset = this.guiSize / 18;
  final int rectSize = (this.guiSize - this.offset * 2) / 8;
  boolean[][] overlay;
  ChessPiece activePiece;
  @Setter
  PromotionDialogue promotionDialogue;

  public GamePanel() {
    this.board = new Board();
    this.overlay = new boolean[8][8];
    this.initializeControls();
    this.setSize(this.guiSize, this.guiSize);
    this.setVisible(true);
  }

  private void initializeControls() {
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        if (promotionDialogue != null) {
          JOptionPane.showMessageDialog(GamePanel.this,
              "Please first select the piece you want to promote to.");
          return;
        }

        val origin = GamePanel.this.screenToBoard(e.getPoint());
        if (GamePanel.this.board.isInBounds(origin)) {
          val piece = GamePanel.this.board.at(origin);
          if (piece != null) {
            GamePanel.this.activePiece = piece;
            for (int y = 0; y < 8; y++) {
              for (int x = 0; x < 8; x++) {
                val destination = new Vector2i(x, y);
                GamePanel.this.overlay[y][x] = piece.canMove(destination);
              }
            }
            GamePanel.this.repaint();
          }
        }
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        val target = GamePanel.this.screenToBoard(e.getPoint());
        if (GamePanel.this.board.isInBounds(target)) {
          if (GamePanel.this.activePiece != null) {
            val color = GamePanel.this.board.getTurnColor();
            GamePanel.this.board.move(activePiece, target);
            if (color != GamePanel.this.board.getTurnColor()) {
              if (activePiece instanceof Pawn pawn && pawn.isPromote()) {
                promotionDialogue = new PromotionDialogue(GamePanel.this, pawn);
              }
            }
            GamePanel.this.activePiece = null;
            GamePanel.this.overlay = new boolean[8][8];
            GamePanel.this.repaint();
          }
        }
      }
    });
  }

  private Vector2i screenToBoard(Point coordinates) {
    coordinates.translate(-this.offset, -this.offset);
    return new Vector2i((int) (coordinates.getX() / this.rectSize), (int) (coordinates.getY() / this.rectSize));
  }

  @Override
  public void paint(Graphics g) {
    g.clearRect(0, 0, this.getWidth(), this.getHeight());
    this.drawBoard((Graphics2D) g);
  }

  private void drawBoard(Graphics2D g) {
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, this.guiSize, this.guiSize);
    g.drawImage(this.board.getImage(), this.offset, this.offset, null);
    for (int y = 0; y < 8; y++) {
      for (int x = 0; x < 8; x++) {
        val piece = this.board.at(new Vector2i(x, y));
        if (piece != null) {
          val img = piece.getResource().getImage();
          g.drawImage(img, this.offset + x * this.rectSize, this.offset + y * this.rectSize, null);
        }
        if (overlay[y][x]) {
          g.setColor(new Color(0x4084BB26, true));
          g.fillRect(this.offset + x * this.rectSize, this.offset + y * this.rectSize, this.rectSize, this.rectSize);
        }
      }
    }
  }
}
