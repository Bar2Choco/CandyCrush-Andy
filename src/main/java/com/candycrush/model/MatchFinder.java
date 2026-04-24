package com.candycrush.model;

public class MatchFinder {

    public static boolean find(Candy[][] b) {
        boolean[][] m = map(b);

        for (boolean[] r : m)
            for (boolean v : r)
                if (v) return true;

        return false;
    }

    public static boolean[][] map(Candy[][] b) {

        boolean[][] m = new boolean[8][8];

        for (int i = 0; i < 8; i++) {
            int count = 1;

            for (int j = 1; j < 8; j++) {

                if (b[i][j].type != Candy.Type.EMPTY &&
                        b[i][j].type == b[i][j - 1].type) {
                    count++;
                } else {
                    mark(m, i, j - 1, count);
                    count = 1;
                }
            }

            mark(m, i, 7, count);
        }

        for (int j = 0; j < 8; j++) {
            int count = 1;

            for (int i = 1; i < 8; i++) {

                if (b[i][j].type != Candy.Type.EMPTY &&
                        b[i][j].type == b[i - 1][j].type) {
                    count++;
                } else {
                    markV(m, i - 1, j, count);
                    count = 1;
                }
            }

            markV(m, 7, j, count);
        }

        return m;
    }

    private static void mark(boolean[][] m, int i, int j, int count) {
        if (count >= 3)
            for (int k = 0; k < count; k++)
                m[i][j - k] = true;
    }

    private static void markV(boolean[][] m, int i, int j, int count) {
        if (count >= 3)
            for (int k = 0; k < count; k++)
                m[i - k][j] = true;
    }
}