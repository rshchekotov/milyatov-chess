package org.frost.chess.gui;

import lombok.val;
import org.frost.chess.Board;
import org.frost.chess.piece.ChessPiece;
import org.frost.chess.piece.ChessPieceColor;
import org.frost.chess.piece.ChessPieceResource;
import org.frost.chess.piece.Pawn;
import org.frost.chess.util.ChessUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PromotionDialogue extends JFrame {
  final int offset = 40;
  final int padding = 10;
  GamePanel gui;
  Pawn pawn;
  ChessPieceResource[] selectables;

  public PromotionDialogue(GamePanel gui, Pawn pawn) {
    this.gui = gui;
    this.pawn = pawn;

    val code = new int[]{
        ChessPieceResource.WHITE_ROOK.getValue(),
        ChessPieceResource.WHITE_KNIGHT.getValue(),
        ChessPieceResource.WHITE_BISHOP.getValue(),
        ChessPieceResource.WHITE_QUEEN.getValue()
    };
    val color = pawn.getResource().getChessPieceColor() == ChessPieceColor.WHITE ? 0 : 8;
    this.selectables = new ChessPieceResource[code.length];
    for (int i = 0; i < code.length; i++) {
      this.selectables[i] = ChessPieceResource.fromValue(code[i] + color);
    }


    this.initializeControls();
    this.setUndecorated(true);
    val pieceSize = this.gui.getPieceSize();
    val width = 2 * offset + code.length * pieceSize + (code.length - 1) * padding;
    this.setSize(width, 2 * (pieceSize + offset));
    this.setVisible(true);
    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
  }

  private void initializeControls() {
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        val scaledX = (e.getX() - offset) / (gui.getPieceSize() + padding);
        val scaledY = (e.getY() - offset) / (gui.getPieceSize());
        if(ChessUtil.inRange(scaledX, 0, selectables.length) &&
          scaledY == 0) {
          val selected = selectables[scaledX];
          val value = selected.getValue() + selected.getChessPieceColor().getValue();
          val board = gui.getBoard();
          board.set(pawn.getPosition(), ChessPiece.of(board, pawn.getPosition(), value));
          gui.setPromotionDialogue(null);
          dispose();
        }
      }

      @Override
      public void mouseClicked(MouseEvent e) {
        gui.setPromotionDialogue(null);
        dispose();
      }
    });
  }

  @Override
  public void paint(Graphics g) {
    val g2d = (Graphics2D) g;
    g2d.translate(this.offset, this.offset);

    // Draw Pieces
    val pieceSize = this.gui.getPieceSize();
    for (int i = 0; i < this.selectables.length; i++) {
      val piece = this.selectables[i];
      val pieceImage = piece.getImage();
      g2d.drawImage(pieceImage, i * (pieceSize + padding), 0, null);
    }
  }
}
