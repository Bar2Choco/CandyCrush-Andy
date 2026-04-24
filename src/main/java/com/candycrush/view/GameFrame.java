package com.candycrush.view;

import com.candycrush.model.Board;
import com.candycrush.model.GameState;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private BoardPanel boardPanel;

    private JLabel scoreLabel = new JLabel("Score: 0");
    private JLabel movesLabel = new JLabel("Moves: 30");

    // ⭐ NOUVEAU : mode illimité
    private boolean unlimitedMode = false;

    public GameFrame(com.candycrush.controller.GameController controller) {

        setTitle("Candy Crush");
        setSize(600, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new GridLayout(1, 2));

        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        movesLabel.setHorizontalAlignment(SwingConstants.CENTER);

        top.add(scoreLabel);
        top.add(movesLabel);

        add(top, BorderLayout.NORTH);

        boardPanel = new BoardPanel(controller);
        add(boardPanel, BorderLayout.CENTER);
    }

    // ⭐ NOUVEAU : activer mode illimité
    public void setUnlimitedMode(boolean value) {
        this.unlimitedMode = value;
        movesLabel.setVisible(!value);
    }

    public void update(Board b, GameState s) {

        boardPanel.update(b);

        scoreLabel.setText("Score: " + s.getScore());

        // ❌ Moves affiché seulement en mode normal
        if (!unlimitedMode) {
            movesLabel.setText("Moves: " + s.getMovesLeft());

            if (s.getMovesLeft() <= 5) {
                movesLabel.setForeground(Color.RED);
            } else {
                movesLabel.setForeground(Color.BLACK);
            }
        }

        repaint();
        revalidate();
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }
}