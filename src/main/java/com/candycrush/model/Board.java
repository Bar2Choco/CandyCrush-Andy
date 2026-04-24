package com.candycrush.model;

import java.util.Random;

public class Board {

    public static final int SIZE = 8;

    private Candy[][] grid = new Candy[SIZE][SIZE];
    private Random r = new Random();

    public Board() {
        fill();
    }

    private void fill() {

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {

                Candy.Type type;

                do {
                    type = random();
                }
                while (
                        createsHorizontalMatch(i, j, type) ||
                                createsVerticalMatch(i, j, type)
                );

                grid[i][j] = new Candy(type);
            }
        }
    }

    private Candy.Type random() {
        Candy.Type[] v = {
                Candy.Type.RED, Candy.Type.BLUE, Candy.Type.GREEN,
                Candy.Type.YELLOW, Candy.Type.PURPLE, Candy.Type.ORANGE
        };
        return v[r.nextInt(v.length)];
    }

    private boolean createsHorizontalMatch(int i, int j, Candy.Type type) {
        if (j < 2) return false;

        return grid[i][j - 1] != null &&
                grid[i][j - 2] != null &&
                grid[i][j - 1].type == type &&
                grid[i][j - 2].type == type;
    }

    private boolean createsVerticalMatch(int i, int j, Candy.Type type) {
        if (i < 2) return false;

        return grid[i - 1][j] != null &&
                grid[i - 2][j] != null &&
                grid[i - 1][j].type == type &&
                grid[i - 2][j].type == type;
    }

    public Candy[][] getGrid() { return grid; }

    public void swap(int x1, int y1, int x2, int y2) {
        Candy tmp = grid[x1][y1];
        grid[x1][y1] = grid[x2][y2];
        grid[x2][y2] = tmp;
    }

    public boolean hasMatch() {
        return MatchFinder.find(grid);
    }

    public int clearMatches() {

        boolean[][] m = MatchFinder.map(grid);
        int score = 0;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {

                if (m[i][j]) {
                    grid[i][j] = new Candy(Candy.Type.EMPTY);
                    score += 10;
                }
            }
        }

        return score;
    }

    public void applyGravity() {

        for (int j = 0; j < SIZE; j++) {

            for (int i = SIZE - 1; i > 0; i--) {

                if (grid[i][j].type == Candy.Type.EMPTY) {
                    grid[i][j] = grid[i - 1][j];
                    grid[i - 1][j] = new Candy(Candy.Type.EMPTY);
                }
            }
        }

        refill();
    }

    private void refill() {

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {

                if (grid[i][j].type == Candy.Type.EMPTY) {
                    grid[i][j] = new Candy(random());
                }
            }
        }
    }

    public void shuffle() {

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {

                int x = r.nextInt(SIZE);
                int y = r.nextInt(SIZE);

                Candy tmp = grid[i][j];
                grid[i][j] = grid[x][y];
                grid[x][y] = tmp;
            }
        }
    }

    public boolean hasPossibleMoves() {

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {

                if (j + 1 < SIZE) {
                    swap(i, j, i, j + 1);
                    boolean ok = hasMatch();
                    swap(i, j, i, j + 1);
                    if (ok) return true;
                }

                if (i + 1 < SIZE) {
                    swap(i, j, i + 1, j);
                    boolean ok = hasMatch();
                    swap(i, j, i + 1, j);
                    if (ok) return true;
                }
            }
        }

        return false;
    }
}