package org.frost.chess;

import static java.lang.Integer.signum;

public class Board {

    int[][] board = new int[8][8];
    int[] layout = {2, 3, 4, 5, 6, 4, 3, 2};
    int[][] bababooey = new int[2][8];
    int m = 1;
    boolean CanCastle = true;
    boolean CanCastleB = true;
    boolean CanCastleR0 = true;
    boolean CanCastleR0B = true;
    boolean CanCastleR7 = true;
    boolean CanCastleR7B = true;


    Board(){
        for (int i = 0; i < 8; i++){
            this.board[0][i] = this.layout[i];
            this.board[1][i] = 1;
            this.board[6][i] = 1 + 8;
            this.board[7][i] = this.layout[i] + 8;
        }
    }

    boolean inBounds(int x, int y) {
        return x < 8 && x > -1 && y < 8 && y > -1;
    }

    boolean checkOccupied(int x, int y){
        return this.board[y][x] != 0;
    }

    int getPiece(int x, int y){
        return this.board[y][x];
    }

    boolean checkPath(int sX, int sY, int tX, int tY, boolean white){

        int aDX = Math.abs(tX - sX);
        int aDY = Math.abs(tY - sY);
        int dX = tX - sX;
        int dY = tY - sY;
        int a = 0;

        boolean higher = this.board[tY][tX] >= 9 && this.board[tY][tX] != 0;
        boolean lower = this.board[tY][tX] < 9 && this.board[tY][tX] != 0;
        boolean isEnemy = (white ? higher : lower);
        if (isEnemy){
            a--;
        }

        if (aDX <= 1 && aDY <= 1){
            return true;
        }

        if (this.getVector(sX, sY, tX, tY) == 1) {
            for (int i = 1; i <= aDX; i++) {
                if (this.board[sY][sX + signum(dX) * i] != 0){
                    a++;
                }
            }
        }

        if (this.getVector(sX, sY, tX, tY) == 2) {
            for (int i = 1; i <= aDY; i++) {
                if (this.board[sY + signum(dY) * i][sX] != 0){
                    a++;
                }
            }
        }

        if (this.getVector(sX, sY, tX, tY) == 3) {
            for (int i = 1; i <= aDX; i++) {
                if (this.board[sY + signum(dY) * i][sX + signum(dX) * i] != 0){
                    a++;
                }
            }
        }

        return a == 0;
    }

    public int[] findKingW(){
        int[] cords = new int[2];

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (this.board[i][j] == 5){
                    cords[0] = i;
                    cords[1] = j;
                }
            }
        }

        return cords;
    }

    public int[] findKingB(){
        int[] cords = new int[2];

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (this.board[i][j] == 13){
                    cords[0] = i;
                    cords[1] = j;
                }
            }
        }

        return cords;
    }

    boolean isInCheckFrom(int iY, int iX, int sY, int sX, int... block) {
        while (this.inBounds(iX, iY)) {
            for(int item : block) {
                if (this.board[iY][iX] == item) {
                    return true;
                }
            }

            if (this.board[iY][iX] != 0){
                break;
            }
            iX += sX;
            iY += sY;
        }
        return false;
    }

    /**
     *
     * @return true if ... otherwise false
     */
    boolean checkPathK(boolean white, int aX){
        int cY = (white ? this.findKingW() : this.findKingB())[0];
        int cX = (white ? this.findKingW() : this.findKingB())[1] + aX;
        int pawn = (white ? 8 : 0) + ChessPiece.PAWN.getValue();
        int rook = (white ? 8 : 0) + ChessPiece.ROOK.getValue();
        int Juan = (white ? 8 : 0) + ChessPiece.KNIGHT.getValue();
        int bishop = (white ? 8 : 0) + ChessPiece.BISHOP.getValue();
        int king = (white ? 8 : 0) + ChessPiece.KING.getValue();
        int queen = (white ? 8 : 0) + ChessPiece.QUEEN.getValue();

        int[][] v = {{1, -1},{2, -2}};
        for (int i = 0; i < 2; i++) {
            for (int y = 0; y < 2; y++) {
                for (int x = 0; x < 2; x++) {
                    int jumpY = v[i][y];
                    int jumpX = v[(i+1)%2][x];
                    if (this.inBounds(jumpX + cX, jumpY + cY)){
                        if (this.board[jumpY + cY][jumpX + cX] == Juan){
                            return false;
                        }
                    }
                }
            }
        }

        if(this.isInCheckFrom(cY + 1, cX, 1, 0, rook, queen))
            return false;

        if(this.isInCheckFrom(cY - 1, cX, -1, 0, rook, queen))
            return false;

        if(this.isInCheckFrom(cY, cX + 1, 0, 1, rook, queen))
            return false;

        if(this.isInCheckFrom(cY, cX - 1, 0, -1, rook, queen))
            return false;

        if(this.isInCheckFrom(cY + 1, cX + 1, 1, 1, bishop, queen))
            return false;

        if(this.isInCheckFrom(cY - 1, cX - 1, -1, -1, bishop, queen))
            return false;

        if(this.isInCheckFrom(cY + 1, cX - 1, 1, -1, bishop, queen))
            return false;

        if(this.isInCheckFrom(cY - 1, cX + 1, -1, 1, bishop, queen))
            return false;

        for (int i = Math.max(cY - 1, 0); i <= Math.min(7, cY + 1); i++){
            for (int j = Math.max(cX - 1, 0); j < Math.min(7, cX + 1); j++){
                if (i == cY && j == cX){
                    continue;
                }
                if (this.board[i][j] == king){
                    return false;
                }
            }
        }

        int checkY = white ? cY + 1 : cY - 1;
        if(this.inBounds(cX-1, checkY) && this.board[checkY][cX - 1] == pawn) {
            return false;
        }
        return !this.inBounds(cX + 1, checkY) || this.board[checkY][cX + 1] != pawn;
    }

    boolean checkPathK2(boolean white, int X, int Y){
        int pawn = (white ? 8 : 0) + 1;
        int rook = (white ? 8 : 0) + 2;
        int Juan = (white ? 8 : 0) + 3;
        int bishop = (white ? 8 : 0) + 4;
        int king = (white ? 8 : 0) + 5;
        int queen = (white ? 8 : 0) + 6;

        int[][] v = {{1, -1},{2, -2}};
        for (int i = 0; i < 2; i++) {
            for (int y = 0; y < 2; y++) {
                for (int x = 0; x < 2; x++) {
                    int jumpY = v[i][y];
                    int jumpX = v[(i+1)%2][x];
                    if (this.inBounds(jumpX + X, jumpY + Y)){
                        if (this.board[jumpY + Y][jumpX + X] == Juan){
                            return false;
                        }
                    }
                }
            }
        }

        if(this.isInCheckFrom(Y + 1, X, 1, 0, rook, queen))
            return false;

        if(this.isInCheckFrom(Y - 1, X, -1, 0, rook, queen))
            return false;

        if(this.isInCheckFrom(Y, X + 1, 0, 1, rook, queen))
            return false;

        if(this.isInCheckFrom(Y, X - 1, 0, -1, rook, queen))
            return false;

        if(this.isInCheckFrom(Y + 1, X + 1, 1, 1, bishop, queen))
            return false;

        if(this.isInCheckFrom(Y - 1, X - 1, -1, -1, bishop, queen))
            return false;

        if(this.isInCheckFrom(Y + 1, X - 1, 1, -1, bishop, queen))
            return false;

        if(this.isInCheckFrom(Y - 1, X + 1, -1, 1, bishop, queen))
            return false;

        for (int i = Math.max(Y - 1, 0); i <= Math.min(7, Y + 1); i++){
            for (int j = Math.max(X - 1, 0); j < Math.min(7, X + 1); j++){
                if (i == Y && j == X){
                    continue;
                }
                if (this.board[i][j] == king){
                    return false;
                }
            }
        }

        int checkY = white ? Y + 1 : Y - 1;
        if(this.inBounds(X -1, checkY) && this.board[checkY][X - 1] == pawn) {
            return false;
        }
        return !this.inBounds(X + 1, checkY) || this.board[checkY][X + 1] != pawn;
    }

    int getVector(int sX, int sY, int tX, int tY){
        int dX = Math.abs(tX-sX);
        int dY = Math.abs(tY-sY);
        if (dX + dY == 0) {
            return 0;
        }
        if (dY == 0){
            return 1;
        }
        if (dX == 0){
            return 2;
        }
        if (dY == dX){
            return 3;
        }
        if (dX / dY == 2 || dY / dX == 2){
            return 4;
        }
        return 0;
    }

    boolean checkPiece(int sX, int sY, int tX, int tY, boolean white){
        int dX = Math.abs(tX-sX);
        int dY = Math.abs(tY-sY);
        int z = this.getVector(sX, sY, tX, tY);
        boolean goingForward = Math.signum(tY - sY) == 1;
        if (this.checkOccupied(sX, sY)){
            switch (this.getPiece(sX, sY)){
                case 1,9 -> {
                    if ((z == 2) && (white == goingForward)) {
                        if (sY == (white ? 1 : 6) && dY <= 2 && !this.checkOccupied(tX, tY)) {
                            return true;
                        }
                        if (dY == 1 && !this.checkOccupied(tX, tY)) {
                            return true;
                        }
                    }
                    if (z == 3 && dY == 1 && (white == goingForward)){
                        return true;
                    }
                }
                case 2,10 -> {
                    if (z == 2 || z == 1){
                        return true;
                    }
                }
                case 3,11 -> {
                    if (z == 4) {
                        return true;
                    }
                }
                case 4, 12 -> {
                    if (z == 3){
                        return true;
                    }
                }
                case 5, 13 -> {
                    if (z != 0 && dX < 2 && dY < 2){
                        return true;
                    }
                }
                case 6, 14 -> {
                    if (z == 1 || z == 2 || z == 3){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void setBoard(int x, int y, int v) {
        this.board[y][x] = v;
    }

    boolean getMoveColor(){
        return this.m % 2 == 1;
    }

    boolean canCastle(int tX, boolean white){
        boolean canFinCastleK = (white ? this.CanCastle : this.CanCastleB);
        boolean canFinCastleR = false;
        boolean c = false;
        int x = tX - 3;
        int t = Math.abs(x);
        int s = signum(x);
        int posY = (white ? 0 : 7);
        int checkPathX = -1;
        if (tX == 1){
            checkPathX = 1;
            canFinCastleR = (white ? this.CanCastleR0 : this.CanCastleR0B);
        }
        if (tX == 5){
            checkPathX = 6;
            canFinCastleR = (white ? this.CanCastleR7 : this.CanCastleR7B);
        }
        if (canFinCastleK && canFinCastleR) {
            if (this.checkPath(3, posY, checkPathX, posY, white)){
                for (int i = 0; i <= t; i++) {
                    if (this.checkPathK(white, i * s)) {
                        c = true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return c;
    }

    boolean wantToCastle(int sX, int sY, int tX, int tY, boolean white){
        int dX = Math.abs(tX - sX);
        int dY = Math.abs(tY - sY);
        int king = (white ? 5 : 13);
        if (this.board[sY][sX] == king){
            return dY == 0 && dX == 2;
        }
        return false;
    }

    public void castle(int tX, boolean white){
        int posY = (white ? 0 : 7);
        int posRn = 0;
        int posR = 0;
        switch (tX) {
            case 1 -> posRn = 2;
            case 5 ->{
                posRn = 4;
                posR = 7;
            }
        }
        int king = (white ? 5 : 13);
        int rook = (white ? 2 : 10);
        this.board[posY][tX] = king;
        this.board[posY][3] = 0;
        this.board[posY][posRn] = rook;
        this.board[posY][posR] = 0;
    }

    @SuppressWarnings("unused")
    boolean wantsMove2(int sX, int sY, int ignoredTX, int tY, boolean white){
        int z = Math.abs(tY - sY);
        if (this.getPiece(sX, sY) == (white ? 1 : 9)){
            return z == 2;
        }
        return false;
    }

    public void dbleMove(int sX, int sY, int tX, int tY, boolean white){
        if (this.wantsMove2(sX,  sY,  tX,  tY, white)){
            if (this.inBounds(sX - 1, 1)){
                this.bababooey[(white ? 0 : 1)][sX - 1] = 1;
            }
            if (this.inBounds(sX + 1, 1)){
                this.bababooey[(white ? 0 : 1)][sX + 1] = 2;
            }
        }
    }

    public void clearBababooey(boolean white){
        for (int i = 0; i <= 7; i++){
            this.bababooey[(white ? 0 : 1)][i] = 0;
        }
    }

    public void finClearBababooey(){
        if (this.m > 2){
            this.clearBababooey(this.getMoveColor());
        }
    }

    public boolean canEnPeasant(int sX, int sY, int tX, int tY, boolean white){
        int ePawn = (white ? 9 : 1);
        if (this.getPiece(sX, sY) == (white ? 1 : 9)){
            if (this.bababooey[(white ? 1 : 0)][sX] == 1 && tX == sX + 1 && !this.isAlly(sX + 1, sY, white)
            && this.board[sY][sX + 1] == ePawn){
                this.move(sX, sY, tX, tY);
                this.board[sY][sX + 1] = 0;
                return true;
            }
            if (this.bababooey[(white ? 1 : 0)][sX] == 2 && tX == sX - 1 && !this.isAlly(sX - 1, sY, white)
            && this.board[sY][sX - 1] == ePawn){
                this.move(sX, sY, tX, tY);
                this.board[sY][sX - 1] = 0;
                return true;
            }
        }
        return false;
    }

    boolean finCheckPawn(int sX, int sY, int tX, int tY, boolean white){
        int pawn = (white ? 1 : 9);
        int dY = Math.abs(tY-sY);
        int z = this.getVector(sX, sY, tX, tY);
        boolean goingForward = Math.signum(tY - sY) == 1;
        boolean higher = this.board[tY][tX] >= 9;
        boolean lower = this.board[tY][tX] < 9 && this.board[tY][tX] != 0;
        boolean isEnemy = (white ? higher : lower);
        if (this.board[sY][sX] == pawn && isEnemy){
            return true;
        }
        if ((z == 2) && (white == goingForward)) {
            return dY == 1;
        }
        return false;
    }

    public void move(int sX, int sY, int tX, int tY){
        this.board[tY][tX] = this.board[sY][sX];
        this.board[sY][sX] = 0;
    }

    public void finMove(int sX, int sY, int tX, int tY, boolean turn){
        if(this.inBounds(sX, sY)) {
            boolean white = (this.getPiece(sX, sY) & 8) == 0;
            if(turn != white) {
                return;
            }
        } else {
            return;
        }

        if (!this.inBounds(tX, tY)) {
            return;
        }

        if (!this.checkCheck(sX, sY, tX, tY, turn)) {
            return;
        }

        this.finClearBababooey();
        if (this.wantToCastle(sX, sY, tX, tY, turn) && this.canCastle(tX, turn)) {
            this.castle(tX, turn);
            if (turn)
                this.CanCastle = false;
            if (!turn)
                this.CanCastleB = false;
            this.m++;
            return;
        }
        if (this.checkPiece(sX, sY, tX, tY, turn) && this.checkPath(sX, sY, tX, tY, turn) && !this.isAlly(tX, tY, turn)){
            if (this.wantsMove2(sX, sY, tX, tY, turn)){
                this.dbleMove(sX, sY, tX, tY, turn);
                this.move(sX, sY, tX, tY);
                this.m++;
                return;
            }
            if (this.canEnPeasant(sX, sY, tX, tY, turn)){
                this.m++;
                return;
            }
            if (this.board[sY][sX] == (turn ? 1 : 9)){
                if (this.finCheckPawn(sX, sY, tX, tY, turn)){
                    this.move(sX, sY, tX, tY);
                    if (this.wantsToPromote(tY, turn)){
                        this.promote(tX, tY, turn);
                    }
                    this.m++;
                    return;
                }
                return;
            }

            if (this.board[sY][sX] == (turn ? 2 : 10)){
                if (sX == 0){
                    if (turn)
                        this.CanCastleR0 = false;
                    else
                        this.CanCastleR0B = false;
                } else {
                    if (turn)
                        this.CanCastleR7 = false;
                    else
                        this.CanCastleR7B = false;
                }
            }

            if (this.board[sY][sX] == (turn ? 5 : 13)){
                if (turn)
                    this.CanCastle = false;
                else
                    this.CanCastleB = false;
            }

            this.move(sX, sY, tX, tY);
            this.m++;
        }

        if (this.getPiece(sX, sY) == (turn ? 3 : 11)) {
            if (this.isEnemy(tX, tY, turn)) {
                this.move(sX, sY, tX, tY);
                this.m++;
            }
        }
    }

    boolean checkCheck(int sX, int sY, int tX, int tY, boolean white){
        boolean check = false;
        int temp = this.board[tY][tX];
        this.move(sX, sY, tX, tY);
        int king = (white ? 5 : 13);
        if (this.board[sY][sX] != king){
            if (this.checkPathK(white, 0)){
                check = true;
            }
        } else {
            if (this.checkPathK2(white, tX, tY)){
                check = true;
            }
        }
        this.move(tX, tY, sX, sY);
        this.setBoard(tX, tY, temp);
        return check;
    }

    boolean isAlly(int x, int y, boolean white){
        boolean higher = this.board[y][x] >= 9;
        boolean lower = this.board[y][x] < 9 && this.board[y][x] != 0;
        return (white ? lower : higher);
    }

    boolean isEnemy(int x, int y, boolean white){
        boolean higher = this.board[y][x] >= 9;
        boolean lower = this.board[y][x] < 9 && this.board[y][x] != 0;
        return (white ? higher : lower);
    }

    public void promote(int tX, int tY, boolean white){
        int queen = (white ? 6 : 14);
        this.setBoard(tX, tY, queen);
    }

    public boolean wantsToPromote(int tY, boolean white){
        int finRow = (white ? 7 : 0);
        return tY == finRow;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i <= this.board.length - 1 ; i++){
            for (int j = 0; j <= this.layout.length - 1; j++){
                res.append(Integer.toString(this.board[i][j], 16));
            }
            res.append("\n");
        }
        return res.toString();
    }
}
