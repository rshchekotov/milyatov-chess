package org.frost.chess.piece;

import lombok.Getter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

@Getter
public enum ChessPieceResource {
  BLACK_PAWN("pawn_black", Color.BLACK, 1),
  BLACK_ROOK("rook_black", Color.BLACK, 2),
  BLACK_KNIGHT("knight_black", Color.BLACK, 3),
  BLACK_BISHOP("bishop_black", Color.BLACK, 4),
  BLACK_KING("king_black", Color.BLACK, 5),
  BLACK_QUEEN("queen_black", Color.BLACK, 6),
  WHITE_PAWN("pawn_white", Color.WHITE, 1),
  WHITE_ROOK("rook_white", Color.WHITE, 2),
  WHITE_KNIGHT("knight_white", Color.WHITE, 3),
  WHITE_BISHOP("bishop_white", Color.WHITE, 4),
  WHITE_KING("king_white", Color.WHITE, 5),
  WHITE_QUEEN("queen_white", Color.WHITE, 6);

  final String name;
  final Color color;
  final int value;
  BufferedImage image;

  ChessPieceResource(String name, Color color, int value) {
    this.name = name;
    this.color = color;
    this.value = (this.color == Color.BLACK ? 8 : 0) + value;
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
      String file = "/img/" + this.name + ".png";
      URL url = this.getClass().getResource(file);
      if (url == null) {
        System.err.printf("URL <%s> is null!\n", file);
      }
      this.image = ImageIO.read(Objects.requireNonNull(url));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
