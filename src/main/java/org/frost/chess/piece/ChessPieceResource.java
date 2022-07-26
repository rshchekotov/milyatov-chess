package org.frost.chess.piece;

import lombok.Getter;
import lombok.val;
import org.frost.chess.gui.GUITheme;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

@Getter
public enum ChessPieceResource {
  BLACK_PAWN("pawn_black", ChessPieceColor.BLACK, 1),
  BLACK_ROOK("rook_black", ChessPieceColor.BLACK, 2),
  BLACK_KNIGHT("knight_black", ChessPieceColor.BLACK, 3),
  BLACK_BISHOP("bishop_black", ChessPieceColor.BLACK, 4),
  BLACK_KING("king_black", ChessPieceColor.BLACK, 5),
  BLACK_QUEEN("queen_black", ChessPieceColor.BLACK, 6),
  WHITE_PAWN("pawn_white", ChessPieceColor.WHITE, 1),
  WHITE_ROOK("rook_white", ChessPieceColor.WHITE, 2),
  WHITE_KNIGHT("knight_white", ChessPieceColor.WHITE, 3),
  WHITE_BISHOP("bishop_white", ChessPieceColor.WHITE, 4),
  WHITE_KING("king_white", ChessPieceColor.WHITE, 5),
  WHITE_QUEEN("queen_white", ChessPieceColor.WHITE, 6);

  private final String name;
  private final ChessPieceColor chessPieceColor;
  private final int value;
  private BufferedImage image;

  ChessPieceResource(String name, ChessPieceColor chessPieceColor, int value) {
    this.name = name;
    this.chessPieceColor = chessPieceColor;
    this.value = (this.chessPieceColor == ChessPieceColor.BLACK ? 8 : 0) + value;
    this.loadImage();
  }

  public static ChessPieceResource fromValue(int value) {
    return switch (value) {
      case 1 -> WHITE_PAWN;
      case 2 -> WHITE_ROOK;
      case 3 -> WHITE_KNIGHT;
      case 4 -> WHITE_BISHOP;
      case 5 -> WHITE_KING;
      case 6 -> WHITE_QUEEN;
      case 9 -> BLACK_PAWN;
      case 10 -> BLACK_ROOK;
      case 11 -> BLACK_KNIGHT;
      case 12 -> BLACK_BISHOP;
      case 13 -> BLACK_KING;
      case 14 -> BLACK_QUEEN;
      default -> throw new IllegalStateException("invalid piece");
    };
  }

  private void loadImage() {
    try {
      val file = "/img/%s/%s.png".formatted(GUITheme.getCurrentTheme(), this.name);
      val url = this.getClass().getResource(file);
      if (url == null) {
        // TODO: Replace by Log4j
        System.err.printf("URL <%s> is null!\n", file);
      }
      this.image = ImageIO.read(Objects.requireNonNull(url));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
