package com.candycrush.model;

public class GameState {

    private int score = 0;
    private int movesLeft = 30;

    public int getScore() { return score; }

    public int getMovesLeft() { return movesLeft; }

    public void addScore(int s) { score += s; }

    public void useMove() { movesLeft--; }

    public boolean isOutOfMoves() { return movesLeft <= 0; }

    public void reset() {
        score = 0;
        movesLeft = 30;
    }
}