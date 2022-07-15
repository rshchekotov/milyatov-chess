package org.frost.chess.piece;

import org.frost.chess.Board;
import org.frost.chess.ChessPiece;
import org.frost.chess.ChessPieceResource;
import org.frost.chess.util.Vector2i;

public class King extends ChessPiece {
  Rook castleRook = null;
  boolean[] castling = {true, true};

  public King(Board board, ChessPieceResource resource, Vector2i position) {
    super(board, resource, position);
  }

  @Override
  public void move(Vector2i destination) {
    super.move(destination);
    if (castleRook != null) {
      Vector2i castleTo = castleRook.getPosition().copy();
      if (castleTo.getX() == 0) {
        castleTo.setX(2);
      } else {
        castleTo.setX(5);
      }
      castleRook.move(castleTo);
    }
    castling = new boolean[]{false, false};
  }

  @Override
  public boolean canMove(Vector2i destination) {
    castleRook = null;
    if (!super.canMove(destination)) {
      return false;
    }

    Vector2i movementVector = destination.sub(getPosition());
    int dx = movementVector.getX();
    int dy = movementVector.getY();

    // Check for Castling
    if (dy == 0 && (dx == -2 || dx == 3)) {
      Rook rook;
      int index = dx == -2 ? 0 : 1;
      int[] rookPosition = {0, 7};

      if (!castling[index]) {
        return false;
      } else {
        // TODO: Check for Checks & Pins
        rook = (Rook) getBoard().at(new Vector2i(rookPosition[index], getPosition().getY()));
        boolean canCastle = getBoard().checkPath(getPosition(), rook.getPosition());
        if (canCastle) {
          castleRook = rook;
        }
        return canCastle;
      }
    }

    return Math.abs(dx) <= 1 && Math.abs(dy) <= 1;
  }
}
