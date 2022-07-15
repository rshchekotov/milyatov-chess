package org.frost.chess.piece;

import lombok.val;
import org.frost.chess.Board;
import org.frost.chess.util.Vector2i;

public class Knight extends ChessPiece {
  public Knight(Board board, ChessPieceResource resource, Vector2i position) {
    super(board, resource, position);
  }

  @Override
  public boolean canMove(Vector2i destination) {
    if (!super.canMove(destination)) {
      return false;
    }

    val movementVector = destination.sub(getPosition());
    // Checks for Knight-Jumps
    val dx = Math.abs(movementVector.getX());
    val dy = Math.abs(movementVector.getY());
    // TODO: Check for Pins
    return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
  }
}
