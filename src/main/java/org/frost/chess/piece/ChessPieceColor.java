package org.frost.chess.piece;

import lombok.Getter;

public enum ChessPieceColor {
  WHITE,
  BLACK;

  @Getter
  final int value = (ordinal() * 8);
}
