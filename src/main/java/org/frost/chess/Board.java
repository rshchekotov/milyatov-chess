package org.frost.chess;

import org.frost.chess.pieces.ChessPiece;

public class Board {
    int[][] board = new int[8][8];

    Board() {

        final int[] pieceLayout = {4, 2, 3, 5, 6, 3, 2, 4};

        for (int i = 0; i < 8; i++) {
            board[0][i] = pieceLayout[i];
            board[1][i] = 1;
            board[6][i] = 8 + pieceLayout[i];
            board[7][i] = 8 + 1;
        }
    }

    public void move(int sourceX, int sourceY, int targetX, int targetY) {
        board[sourceY][sourceX] ^= board[targetY][targetX];
        board[targetY][targetX] ^= board[sourceY][sourceX];
        board[sourceY][sourceX] ^= board[targetY][targetX];
    }

    public boolean checkPawn(int sourceX, int sourceY, int targetX, int targetY) {
        boolean isNotOccupied = this.board[sourceY][sourceX] != 0;
        if(isNotOccupied) {
            return false;
        }

        boolean white = new ChessPiece(this.board[sourceY][sourceX]).isWhite();
        boolean canMoveTwo = (white && sourceY == 6) || (!white && sourceY == 1);
        int distance = Math.abs(targetY - sourceY);
        boolean isInRange = (canMoveTwo && distance <= 2) || distance == 1;
        int target = this.board[targetY][targetX];
        boolean canMoveToTarget = target == 0;
        int distanceX = Math.abs(targetX - sourceX);
        boolean canTake = distance == 1 && distanceX == 1 && board[targetX][targetY] != 0;

        return false;
    }

    public boolean isNotOccupied(int sourceY, int sourceX){
        return board[sourceY][sourceX] != 0;
    }

    public boolean checkPath(int sourceX, int sourceY, int targetX, int targetY){
        int distance = Math.abs(targetY - sourceY);
        int distanceX = Math.abs(targetX - sourceX);
        if(distance == distanceX){
            return false;
        } else {
            if (distanceX == 0){
                int i = sourceY;
                int v = (int) Math.signum(distance);
                while(i != targetY){
                }
            }
        }
        return false;


    }

    @Override
    public String toString() {
        String output = "";
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                ChessPiece piece = new ChessPiece(board[y][x]);
                output += String.format("%c ", piece.getName());
            }
            output += "\n";
        }
        return output;
    }
}
