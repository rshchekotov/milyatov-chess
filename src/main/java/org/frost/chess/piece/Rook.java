package org.frost.chess.piece;

import org.frost.chess.Board;
import org.frost.chess.ChessPiece;
import org.frost.chess.ChessPieceResource;
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

    Vector2i movementVector = destination.sub(getPosition());
    // Checks if the movement is a straight line
    if (movementVector.isNonOrthogonal()) {
      return false;
    }
    // TODO: Check for Pins

    boolean canMove = getBoard().checkPath(getPosition(), destination);
    // Disable Castling
    King king = switch (getResource().getPieceColor()) {
      case WHITE -> getBoard().getWhiteKing();
      case BLACK -> getBoard().getBlackKing();
    };
    if (canMove) {
      int pos = originalPosition.getX() == 0 ? 0 : 1;
      king.castling[pos] = false;
    }
    return canMove;
  }
}
