package com.candycrush.view;

import com.candycrush.controller.GameController;
import com.candycrush.model.Board;
import com.candycrush.model.Candy;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    private JButton[][] buttons = new JButton[8][8];

    private int selX = -1;
    private int selY = -1;

    private boolean animating = false;

    public BoardPanel(GameController controller) {

        setLayout(new GridLayout(8, 8));

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                JButton btn = new JButton();
                btn.setFocusPainted(false);

                int x = i;
                int y = j;

                btn.addActionListener(e -> {
                    if (!animating)
                        controller.handleClick(x, y);
                });

                buttons[i][j] = btn;
                add(btn);
            }
        }
    }

    // =========================
    // 🎯 SELECTION
    // =========================
    public void setSelection(int x, int y) {
        buttons[x][y].setBorder(BorderFactory.createLineBorder(Color.RED, 3));
    }

    public void clearSelection() {
        for (JButton[] row : buttons)
            for (JButton b : row)
                b.setBorder(null);
    }

    // =========================
    // 🍬 UPDATE BOARD
    // =========================
    public void update(Board board) {

        Candy[][] grid = board.getGrid();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                Candy c = grid[i][j];

                JButton btn = buttons[i][j];

                if (c == null || c.type == Candy.Type.EMPTY) {
                    btn.setIcon(null);
                    btn.setText("");
                    continue;
                }

                // ✅ IMAGE MODE (RESTAURÉ)
                String path = "candies/" + c.type.name().toLowerCase() + ".png";

                java.net.URL url = ClassLoader.getSystemResource(path);

                if (url != null) {

                    ImageIcon icon = new ImageIcon(url);
                    Image img = icon.getImage().getScaledInstance(55, 55, Image.SCALE_SMOOTH);

                    btn.setIcon(new ImageIcon(img));
                    btn.setText("");

                } else {
                    // fallback si image manquante
                    btn.setIcon(null);
                    btn.setText(c.type.name());
                }
            }
        }

        repaint();
        revalidate();
    }

    // =========================
    // 🚀 ANIMATION SWAP FLUIDE
    // =========================
    public void animateSwap(int x1, int y1, int x2, int y2, Runnable afterSwap) {

        animating = true;

        JButton b1 = buttons[x1][y1];
        JButton b2 = buttons[x2][y2];

        Point p1 = b1.getLocation();
        Point p2 = b2.getLocation();

        int steps = 10;
        int delay = 15;

        Timer timer = new Timer(delay, null);

        final int[] step = {0};

        timer.addActionListener(e -> {

            double t = (double) step[0] / steps;

            int dx1 = (int) ((p2.x - p1.x) * t);
            int dy1 = (int) ((p2.y - p1.y) * t);

            int dx2 = (int) ((p1.x - p2.x) * t);
            int dy2 = (int) ((p1.y - p2.y) * t);

            b1.setLocation(p1.x + dx1, p1.y + dy1);
            b2.setLocation(p2.x + dx2, p2.y + dy2);

            step[0]++;

            if (step[0] > steps) {

                timer.stop();

                b1.setLocation(p1);
                b2.setLocation(p2);

                animating = false;

                afterSwap.run();
            }
        });

        timer.start();
    }
}