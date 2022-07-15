package org.frost.chess.piece;

import org.frost.chess.Board;
import org.frost.chess.ChessPiece;
import org.frost.chess.ChessPieceResource;
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

    Vector2i movementVector = destination.sub(getPosition());
    // Checks for Knight-Jumps
    int dx = Math.abs(movementVector.getX());
    int dy = Math.abs(movementVector.getY());
    // TODO: Check for Pins
    return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
  }
}
