package org.frost.chess;

import lombok.Getter;
import lombok.val;
import org.frost.chess.gui.GUITheme;
import org.frost.chess.piece.ChessPiece;
import org.frost.chess.piece.ChessPieceColor;
import org.frost.chess.piece.King;
import org.frost.chess.piece.Pawn;
import org.frost.chess.util.ChessUtil;
import org.frost.chess.util.Vector2i;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

@Getter
public class Board {
  private final ChessPiece[][] pieces = new ChessPiece[8][8];
  private final King blackKing;
  private final King whiteKing;
  private int move = 0;
  private BufferedImage image;

  public Board() {
    val layout = new int[]{2, 3, 4, 5, 6, 4, 3, 2};
    val size = layout.length;

    for (int i = 0; i < size; i++) {
      val first = new Vector2i(i, 0);
      val second = new Vector2i(i, 1);
      val seventh = new Vector2i(i, 6);
      val eighth = new Vector2i(i, 7);

      pieces[0][i] = ChessPiece.of(this, first, layout[i]);
      pieces[1][i] = ChessPiece.of(this, second, 1);
      pieces[6][i] = ChessPiece.of(this, seventh, 9);
      pieces[7][i] = ChessPiece.of(this, eighth, 8 + layout[i]);
    }
    this.whiteKing = (King) pieces[0][3];
    this.blackKing = (King) pieces[7][3];

    loadImage();
  }

  private void loadImage() {
    try {
      val file = "/img/%s/board.png".formatted(GUITheme.getCurrentTheme());
      val url = this.getClass().getResource(file);
      if (url == null) {
        // TODO: Change to Log4j
        System.err.printf("URL <%s> is null!\n", file);
      }
      this.image = ImageIO.read(Objects.requireNonNull(url));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public boolean isInBounds(Vector2i position) {
    return ChessUtil.inRange(position.getX(), 0, 7) &&
        ChessUtil.inRange(position.getY(), 0, 7);
  }

  public boolean checkPath(Vector2i start, Vector2i end) {
    if (!isInBounds(start) || !isInBounds(end)) {
      return false;
    }

    // Disallow Non-Paths to be checked!
    val movement = end.sub(start);
    if (!movement.isDiagonal() && movement.isNonOrthogonal()) {
      return false;
    }

    // Check 'Touching' Pieces
    if (movement.rectilinearLength() <= 1 ||
        (movement.isDiagonal() && movement.rectilinearLength() == 2)) {
      return true;
    }

    // Check Path
    val vec = end.sub(start).unit();
    val sx = vec.getX();
    val sy = vec.getY();

    int x = start.getX() + sx;
    int y = start.getY() + sy;
    val dx = end.getX();
    val dy = end.getY();

    while (dx != x || dy != y) {
      if (pieces[y][x] != null) {
        return false;
      }
      x += sx;
      y += sy;
    }

    return true;
  }

  public ChessPiece at(Vector2i position) {
    return pieces[position.getY()][position.getX()];
  }

  public void set(Vector2i position, ChessPiece piece) {
    pieces[position.getY()][position.getX()] = piece;
  }

  public void move(ChessPiece piece, Vector2i target) {
    if (piece != null) {
      if (piece.canMove(target)) {
        piece.move(target);

        for (ChessPiece[] ranks : pieces) {
          for (ChessPiece p : ranks) {
            if (p instanceof Pawn pawn && p != piece) {
              pawn.setEnPassant(false);
            }
          }
        }
        move++;
      }
    }
  }

  public ChessPieceColor getTurnColor() {
    return this.move % 2 == 0 ? ChessPieceColor.WHITE : ChessPieceColor.BLACK;
  }

  public BufferedImage getImage() {
    return image;
  }

  @Override
  public String toString() {
    return "Chess Game (Move: %d)".formatted(move);
  }
}
