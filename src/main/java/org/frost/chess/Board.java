package org.frost.chess;

import lombok.Getter;
import org.frost.chess.piece.King;
import org.frost.chess.piece.Pawn;
import org.frost.chess.util.Vector2i;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

@Getter
public class Board {
    private final ChessPiece[][] pieces = new ChessPiece[8][8];
    private final King blackKing;
    private final King whiteKing;
    private int move = 0;
    private BufferedImage image;

    public Board() {
        int[] layout = {2, 3, 4, 5, 6, 4, 3, 2};
        int size = layout.length;

        for (int i = 0; i < size; i++) {
            Vector2i first = new Vector2i(i, 0);
            Vector2i second = new Vector2i(i, 1);
            Vector2i seventh = new Vector2i(i, 6);
            Vector2i eighth = new Vector2i(i, 7);

            pieces[0][i] = ChessPiece.create(this, first, layout[i]);
            pieces[1][i] = ChessPiece.create(this, second, 1);
            pieces[6][i] = ChessPiece.create(this, seventh, 9);
            pieces[7][i] = ChessPiece.create(this, eighth, 8 + layout[i]);
        }
        this.whiteKing = (King) pieces[0][3];
        this.blackKing = (King) pieces[7][3];

        loadImage();
    }

    private void loadImage() {
        try {
            String file = "/img/board.png";
            URL url = this.getClass().getResource(file);
            if (url == null) {
                System.err.printf("URL <%s> is null!\n", file);
            }
            this.image = ImageIO.read(Objects.requireNonNull(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isInBounds(Vector2i position) {
        return position.getX() >= 0 && position.getX() < 8 &&
            position.getY() >= 0 && position.getY() < 8;
    }

    public boolean checkPath(Vector2i start, Vector2i end) {
        if (!isInBounds(start) || !isInBounds(end)) {
            return false;
        }

        // Disallow Non-Paths to be checked!
        Vector2i movement = end.sub(start);
        if (!movement.isDiagonal() && movement.isNonOrthogonal()) {
            return false;
        }

        // Check 'Touching' Pieces
        if (movement.rectilinearLength() <= 1 ||
            (movement.isDiagonal() && movement.rectilinearLength() == 2)) {
            return true;
        }

        // Check Path
        Vector2i vec = end.sub(start).unit();
        int sx = vec.getX();
        int sy = vec.getY();

        int x = start.getX() + sx;
        int y = start.getY() + sy;
        int dx = end.getX();
        int dy = end.getY();

        while (dx != x || dy != y) {
            if (pieces[y][x] != null) {
                return false;
            }
            x += sx;
            y += sy;
        }

        return true;
    }

    public ChessPiece at(Vector2i position) {
        return pieces[position.getY()][position.getX()];
    }

    public void set(Vector2i position, ChessPiece piece) {
        pieces[position.getY()][position.getX()] = piece;
    }

    public void move(ChessPiece piece, Vector2i target) {
        if (piece != null) {
            if (piece.canMove(target)) {
                piece.move(target);
                for (ChessPiece[] ranks : pieces) {
                    for (ChessPiece p : ranks) {
                        if (p instanceof Pawn pawn && p != piece) {
                            pawn.resetEnPassant();
                        }
                    }
                }
                move++;
            }
        }
    }

    public PieceColor getTurnColor() {
        return this.move % 2 == 0 ? PieceColor.WHITE : PieceColor.BLACK;
    }

    public BufferedImage getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Chess Game (Move: %d)".formatted(move);
    }
}
