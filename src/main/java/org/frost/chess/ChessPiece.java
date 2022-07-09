package org.frost.chess;

public enum ChessPiece {
    PAWN(1), ROOK(2), KNIGHT(3), BISHOP(4), KING(5), QUEEN(6);

    final int value;
    ChessPiece(int value){
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static String imageFromValue(int value) {
        String colour = value > 8 ? "_black" : "_white";
        String piece = switch (value & 7){
            case 1 -> "pawn";
            case 2 -> "rook";
            case 3 -> "knight";
            case 4 -> "bishop";
            case 5 -> "king";
            case 6 -> "queen";
            default -> throw new IllegalStateException("invalid piece");
        };
        return piece + colour;
    }
}