package org.frost.chess;

public class Application {

    public static void main(String[] args) {
        Board board = new Board();
        //System.out.println(board.checkPath(1, 1, 6, 6));
        /*
        for (int i = 4; i < 7; i++) {
            board.setBoard(i, 0, 0);
        }
        board.setBoard(5, 1, 10);
        System.out.println(board.checkPathK(board.getMoveColor(), 0));
        System.out.println(board.canCastle(6, board.getMoveColor()));
        System.out.println(board.checkPiece(1, 1, 1, 3, board.getMoveColor()));
        System.out.println(board.wantsMove2(1, 1, 1, 2, board.getMoveColor()));
         */
        board.finMove(3, 1, 3, 3, board.getMoveColor());
        board.finMove(3, 6, 3, 4, board.getMoveColor());
        board.finMove(4, 0, 0, 4, board.getMoveColor());
        board.finMove(3, 7, 3, 6, board.getMoveColor());
        board.finMove(0, 4, 3, 4, board.getMoveColor());
        board.finMove(3, 6, 3, 5, board.getMoveColor());

        System.out.println(board);

    }
}
