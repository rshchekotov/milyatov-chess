package org.frost.chess.piece;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.frost.chess.Board;

@AllArgsConstructor
@Data
public abstract class ChessPiece {
  Board board;
  ChessPieceResource resource;
  int x;
  int y;

  abstract boolean move(int x, int y);

  int rectilinearDistance(int x, int y) {
    return Math.abs(x - this.x) + Math.abs(y - this.y);
  }
}