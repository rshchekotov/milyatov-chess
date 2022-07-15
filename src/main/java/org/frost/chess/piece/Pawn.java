package org.frost.chess.piece;

import lombok.val;
import org.frost.chess.Board;
import org.frost.chess.util.Vector2i;

public class Pawn extends ChessPiece {
  boolean enPassant = false;
  boolean dualMove = false;
  Pawn enPassantPawn = null;

  public Pawn(Board board, ChessPieceResource resource, Vector2i position) {
    super(board, resource, position);
  }

  @Override
  public void move(Vector2i destination) {
    super.move(destination);
    if (dualMove) {
      enPassant = true;
    }
    if (enPassantPawn != null) {
      getBoard().set(enPassantPawn.getPosition(), null);
      this.enPassantPawn = null;
    }
  }

  @Override
  public boolean canMove(Vector2i destination) {
    if (!super.canMove(destination)) {
      return false;
    }

    enPassantPawn = null;
    dualMove = false;
    val movementVector = destination.sub(getPosition());

    /* General Pawn Handling */
    val stride = movementVector.rectilinearLength();
    val factor = this.getResource().getChessPieceColor() == ChessPieceColor.WHITE ? 1 : -1;
    if (stride > 2) {
      return false;
    }

    // TODO: Promotion
    if (movementVector.getX() != 0) {
      return canTake(destination, movementVector, factor);
    } else {
      return !cannotMoveForward(destination, movementVector, factor);
    }
  }

  /**
   * Checks if the pawn cannot move forward.
   *
   * @param destination    The destination of the move.
   * @param movementVector The movement vector.
   * @param factor         The factor indicating the color of the pawn.
   * @return True if the pawn cannot move forward.
   */
  private boolean cannotMoveForward(Vector2i destination,
                                    Vector2i movementVector,
                                    int factor) {
    // Checks Movement Direction
    if (movementVector.getY() != factor && movementVector.getY() != factor * 2) {
      return true;
    }

    // Checks if there is a piece in the way
    if (this.getBoard().at(destination) != null) {
      return true;
    }

    // Checks if piece can move 2 spaces
    if (movementVector.getY() == factor * 2) {
      // Correct Origin Position
      if (factor == 1 && this.getPosition().getY() != 1) {
        return true;
      } else if (factor == -1 && this.getPosition().getY() != 6) {
        return true;
      }

      // Checks if there is a piece in the way
      val mid = new Vector2i(this.getPosition().getX(),
          this.getPosition().getY() + factor);
      if (getBoard().at(mid) != null) {
        return true;
      }
      dualMove = true;
    }
    // TODO: Check for Pins!
    return false;
  }

  /**
   * Checks if the piece can take a piece at the given destination.
   *
   * @param destination    The destination to check.
   * @param movementVector The movement vector.
   * @param factor         The factor indicating the color of the piece.
   * @return True if the piece can take a piece at the given destination.
   */
  private boolean canTake(Vector2i destination, Vector2i movementVector,
                          int factor) {
    // Pawn can only move exactly 1 diagonally
    if (movementVector.getY() != factor) {
      return false;
    }
    // Check for En Passant
    val enPassantPosition = new Vector2i(destination.getX(), getPosition().getY());
    val enPassantPiece = getBoard().at(enPassantPosition);
    if (enPassantPiece instanceof Pawn pawn) {
      if (!this.isAlly(pawn) && pawn.enPassant) {
        // TODO: Check for Pins!
        enPassantPawn = pawn;
        return true;
      }
    }
    val piece = getBoard().at(destination);
    // TODO: Check for Pins!
    return piece != null;
  }

  public boolean isEnPassant() {
    return enPassant;
  }

  public void resetEnPassant() {
    this.enPassant = false;
  }
}
