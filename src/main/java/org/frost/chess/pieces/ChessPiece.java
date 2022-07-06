package org.frost.chess.pieces;

public class ChessPiece {

    public static final int PAWN = 1;
    public static final int KNIGHT = 2;
    public static final int BISHOP = 3;
    public static final int ROOK = 4;
    public static final int QUEEN = 5;
    public static final int KING = 6;
    public static final int WHITE = 8;

    int value;

    public ChessPiece(int value){

        this.value = value;

    }

    public boolean isWhite(){
        return (this.value & 8) == 8;
    }

    public int getValue() {
        return this.value;
    }

    public char getName() {
        char type = switch (this.value & 7) {
            case 1 -> 'P';
            case 2 -> 'N';
            case 3 -> 'B';
            case 4 -> 'R';
            case 5 -> 'Q';
            case 6 -> 'K';
            default -> ' ';
        };

        if(isWhite()) {
            return type;
        } else {
            return Character.toLowerCase(type);
        }
    }
}
