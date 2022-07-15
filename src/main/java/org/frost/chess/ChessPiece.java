package org.frost.chess;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.frost.chess.piece.*;
import org.frost.chess.util.Vector2i;

@AllArgsConstructor
@Data
public abstract class ChessPiece {
  Board board;
  ChessPieceResource resource;
  Vector2i position;

  public static ChessPiece create(Board board, Vector2i position, int value) {
    ChessPieceResource resource = ChessPieceResource.fromValue(value);
    return switch (resource.value & 7) {
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

    if (board.getTurnColor() != this.getResource().getPieceColor()) {
      return false;
    }

    ChessPiece piece = board.at(destination);
    if (piece != null && piece.getResource().getPieceColor() == this.getResource().getPieceColor()) {
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
    return piece.getResource().getPieceColor() == this.getResource().getPieceColor();
  }
}