package org.frost.chess.piece;

import lombok.val;
import org.frost.chess.Board;
import org.frost.chess.util.Vector2i;

public class Bishop extends ChessPiece {
  public Bishop(Board board, ChessPieceResource resource, Vector2i position) {
    super(board, resource, position);
  }

  @Override
  public boolean canMove(Vector2i destination) {
    if (!super.canMove(destination)) {
      return false;
    }

    val movementVector = destination.sub(getPosition());
    // Checks if the movement is a straight line
    if (!movementVector.isDiagonal()) {
      return false;
    }
    // TODO: Check for Pins
    return getBoard().checkPath(getPosition(), destination);
  }
}
