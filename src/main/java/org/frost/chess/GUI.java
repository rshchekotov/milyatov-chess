package org.frost.chess;

import org.frost.chess.util.Vector2i;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GUI extends JFrame {

    // Gameplay
    final Board board;
    boolean[][] overlay;
    ChessPiece activePiece;

    // Frame Options
    final int size = 900;
    final int offset = this.size / 18;
    final int rectSize = (this.size - this.offset * 2) / 8;

    GUI() {
        this.board = new Board();
        this.overlay = new boolean[8][8];
        this.initializeControls();
        this.setUndecorated(true);
        this.setSize(this.size, this.size);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new GUI();
    }

    private void initializeControls() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Vector2i origin = GUI.this.screenToBoard(e.getPoint());
                if (GUI.this.board.isInBounds(origin)) {
                    ChessPiece piece = GUI.this.board.at(origin);
                    if (piece != null) {
                        GUI.this.activePiece = piece;
                        for (int y = 0; y < 8; y++) {
                            for (int x = 0; x < 8; x++) {
                                Vector2i destination = new Vector2i(x, y);
                                GUI.this.overlay[y][x] = piece.canMove(destination);
                            }
                        }
                        GUI.this.repaint();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Vector2i target = GUI.this.screenToBoard(e.getPoint());
                if (GUI.this.board.isInBounds(target)) {
                    if (GUI.this.activePiece != null) {
                        GUI.this.board.move(activePiece, target);
                        GUI.this.activePiece = null;
                        GUI.this.overlay = new boolean[8][8];
                        GUI.this.repaint();
                    }
                }
            }
        });
    }

    private Vector2i screenToBoard(Point coordinates) {
        coordinates.translate(-this.offset, -this.offset);
        return new Vector2i(
            (int) (coordinates.getX() / this.rectSize),
            (int) (coordinates.getY() / this.rectSize)
        );
    }

    @Override
    public void paint(Graphics g) {
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        this.drawBoard((Graphics2D) g);
    }

    private void drawBoard(Graphics2D g) {
        g.drawImage(this.board.getImage(), this.offset, this.offset, null);
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                ChessPiece piece = this.board.at(new Vector2i(x, y));
                if (piece != null) {
                    BufferedImage img = piece.getResource().getImage();
                    g.drawImage(img, this.offset + x * this.rectSize,
                        this.offset + y * this.rectSize, null);
                }
                if (overlay[y][x]) {
                    g.setColor(new Color(0x40BABB26, true));
                    g.fillRect(this.offset + x * this.rectSize,
                        this.offset + y * this.rectSize, this.rectSize, this.rectSize);
                }
            }
        }
    }
}
