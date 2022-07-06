package org.frost.newChess;

import javax.swing.*;

public class Window {

    public static void main(String[] args) {
        JFrame window = new JFrame("window");
        window.setSize(1920, 1080);
        window.setVisible(true);
        JDialog dialog = new JDialog();
        dialog.setSize(500, 500);
        dialog.setTitle("dialog");
        dialog.setModal(true);
        dialog.setVisible(true);
        JFileChooser choose = new JFileChooser();
        choose.showOpenDialog(null);
    }
}
