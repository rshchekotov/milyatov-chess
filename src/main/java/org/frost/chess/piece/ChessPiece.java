package org.frost.chess.piece;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.frost.chess.Board;
import org.frost.chess.util.Vector2i;

@Data
@RequiredArgsConstructor
public abstract class ChessPiece {
  @NonNull
  Board board;
  @NonNull
  ChessPieceResource resource;
  @NonNull
  Vector2i position;
  private boolean promoted = false;

  public static ChessPiece of(Board board, Vector2i position, int value) {
    val resource = ChessPieceResource.fromValue(value);
    return switch (resource.getValue() & 7) {
      case 1 -> new Pawn(board, resource, position);
      case 2 -> new Rook(board, resource, position);
      case 3 -> new Knight(board, resource, position);
      case 4 -> new Bishop(board, resource, position);
      case 5 -> new King(board, resource, position);
      case 6 -> new Queen(board, resource, position);
      default -> throw new IllegalStateException("invalid piece");
    };
  }

  public boolean canMove(Vector2i destination) {
    if (!board.isInBounds(destination)) {
      return false;
    }

    if (board.getTurnColor() != this.getResource().getChessPieceColor()) {
      return false;
    }

    val piece = board.at(destination);
    if (piece != null && piece.getResource().getChessPieceColor() == this.getResource().getChessPieceColor()) {
      return false;
    }

    return destination.sub(position).rectilinearLength() >= 1;
  }

  public void move(Vector2i destination) {
    getBoard().set(position, null);
    getBoard().set(destination, this);
    this.position = destination;
  }

  public boolean isAlly(ChessPiece piece) {
    return piece.getResource().getChessPieceColor() == this.getResource().getChessPieceColor();
  }
}