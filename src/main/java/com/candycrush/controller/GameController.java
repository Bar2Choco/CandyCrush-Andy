package com.candycrush.controller;

import com.candycrush.model.*;
import com.candycrush.view.GameFrame;
import com.candycrush.view.MenuFrame;

import javax.swing.*;

public class GameController {

    private Board board = new Board();
    private GameState state = new GameState();
    private GameFrame view;

    private int x1 = -1, y1 = -1;

    // ⭐ NOUVEAU : mode illimité
    private boolean unlimitedMode = false;

    public void setUnlimitedMode(boolean value) {
        this.unlimitedMode = value;
    }

    public void setView(GameFrame v) {
        view = v;
        view.setUnlimitedMode(unlimitedMode); // ⭐ important
        view.update(board, state);
    }

    public void handleClick(int x, int y) {

        if (x1 == -1) {
            x1 = x;
            y1 = y;
            view.getBoardPanel().setSelection(x, y);
            return;
        }

        if (Math.abs(x1 - x) + Math.abs(y1 - y) == 1) {
            swapWithAnimation(x1, y1, x, y);
        }

        x1 = y1 = -1;
    }

    private void swapWithAnimation(int x1, int y1, int x2, int y2) {

        view.getBoardPanel().animateSwap(x1, y1, x2, y2, () -> {

            board.swap(x1, y1, x2, y2);

            if (!board.hasMatch()) {
                board.swap(x1, y1, x2, y2);
                view.update(board, state);
                view.getBoardPanel().clearSelection();
                return;
            }

            // ⭐ MODE ILLIMITÉ : pas de consommation de coups
            if (!unlimitedMode) {
                state.useMove();
            }

            resolveCascade();

            view.update(board, state);
            view.getBoardPanel().clearSelection();

            checkGameOver();
        });
    }

    private void resolveCascade() {

        int score = board.clearMatches();

        if (score == 0) return;

        state.addScore(score);

        Timer t = new Timer(150, e -> {
            board.applyGravity();
            view.update(board, state);
            resolveCascade();
        });

        t.setRepeats(false);
        t.start();
    }

    private void checkGameOver() {

        // ⭐ MODE ILLIMITÉ : pas de game over
        if (!unlimitedMode && state.isOutOfMoves()) {
            endGame("💀 Plus de coups !");
            return;
        }

        if (!board.hasPossibleMoves()) {

            int choice = JOptionPane.showOptionDialog(
                    null,
                    "💀 Aucun coup possible !",
                    "Shuffle",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new String[]{"🔁 Mélanger", "❌ Quitter"},
                    "🔁 Mélanger"
            );

            if (choice == 0) {
                board.shuffle();
                view.update(board, state);
            } else {
                System.exit(0);
            }
        }
    }

    private void endGame(String msg) {

        // ⭐ MODE ILLIMITÉ : pas de sauvegarde score
        if (!unlimitedMode) {
            ScoreManager.save(state.getScore());
        }

        int choice = JOptionPane.showOptionDialog(
                null,
                msg,
                "Game Over",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"🔁 Rejouer", "🏠 Menu", "❌ Quitter"},
                "🔁 Rejouer"
        );

        if (choice == 0) {
            board = new Board();
            state.reset();
            view.update(board, state);
        } else if (choice == 1) {
            view.dispose();
            new MenuFrame().setVisible(true);
        } else {
            System.exit(0);
        }
    }
}