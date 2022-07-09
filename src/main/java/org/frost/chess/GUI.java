package org.frost.chess;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;

public class GUI extends JFrame {

    // Gameplay
    final Board board;
    int activeX = -1, activeY = -1;

    // Frame Options
    final int size = 900;
    final int offset = this.size / 18;
    final int rectSize = (this.size - this.offset * 2) / 8;

    final HashMap<String, BufferedImage> resources = new HashMap<>();

    GUI() {
        this.board = new Board();
        this.initializeGraphics();
        this.initializeControls();
        this.setSize(this.size, this.size);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new GUI();
    }

    private void initializeGraphics() {
        String[] res = {"bishop_black", "bishop_white", "board", "king_black", "king_white", "knight_black", "knight_white", "pawn_black", "pawn_white", "queen_black", "queen_white", "rook_black", "rook_white"};
        for (String resource : res) {
            BufferedImage image;
            try {
                String file = "/img/" + resource + ".png";
                URL url = this.getClass().getResource(file);
                if (url == null) {
                    System.err.printf("URL <%s> is null!\n", file);
                }
                image = ImageIO.read(Objects.requireNonNull(url));
                this.resources.put(resource, image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeControls() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int[] origin = GUI.this.screenToBoard(e.getPoint());
                if(GUI.this.board.inBounds(origin[0], origin[1])) {
                    int piece = GUI.this.board.getPiece(origin[0], origin[1]);
                    if(piece != 0) {
                        GUI.this.activeX = origin[0];
                        GUI.this.activeY = origin[1];
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int[] target = GUI.this.screenToBoard(e.getPoint());
                if(GUI.this.board.inBounds(target[0], target[1])) {
                    if(GUI.this.activeX != -1 && GUI.this.activeY != -1) {
                        boolean turn = GUI.this.board.getMoveColor();
                        GUI.this.board.finMove(GUI.this.activeX, GUI.this.activeY, target[0], target[1], turn);
                        GUI.this.activeX = -1;
                        GUI.this.activeY = -1;
                        GUI.this.repaint();
                    }
                }
            }
        });
    }

    private int[] screenToBoard(Point coordinates) {
        coordinates.translate(-this.offset, -this.offset);
        return new int[]{
                (int) (coordinates.getX() / this.rectSize),
                (int) (coordinates.getY() / this.rectSize)
        };
    }

    @Override
    public void paint(Graphics g) {
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        this.drawBoard((Graphics2D) g);
    }

    private void drawBoard(Graphics2D g) {
        g.drawImage(this.resources.get("board"), this.offset, this.offset, null);
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                int piece = this.board.getPiece(x, y);
                if (piece != 0) {
                    String key = ChessPiece.imageFromValue(piece);
                    BufferedImage img = this.resources.get(key);
                    g.drawImage(img, this.offset + x * this.rectSize, this.offset + y * this.rectSize, null);
                }
            }
        }
    }
}
