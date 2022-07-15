package org.frost.chess.piece;

import lombok.val;
import org.frost.chess.Board;
import org.frost.chess.util.Vector2i;

public class Rook extends ChessPiece {
  final Vector2i originalPosition;

  public Rook(Board board, ChessPieceResource resource, Vector2i position) {
    super(board, resource, position);
    this.originalPosition = position.copy();
  }

  @Override
  public boolean canMove(Vector2i destination) {
    if (!super.canMove(destination)) {
      return false;
    }

    val movementVector = destination.sub(getPosition());
    // Checks if the movement is a straight line
    if (movementVector.isNonOrthogonal()) {
      return false;
    }
    // TODO: Check for Pins

    val canMove = getBoard().checkPath(getPosition(), destination);
    if (canMove) {
      // Disable Castling
      val pos = originalPosition.getX() == 0 ? 0 : 1;
      val king = switch (getResource().getChessPieceColor()) {
        case WHITE -> getBoard().getWhiteKing();
        case BLACK -> getBoard().getBlackKing();
      };
      king.castling[pos] = false;
    }
    return canMove;
  }
}
