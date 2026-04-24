package com.candycrush.model;

import java.io.*;
import java.util.*;

public class ScoreManager {

    private static final String FILE = "scores.txt";

    public static void save(int score) {
        try (FileWriter fw = new FileWriter(FILE, true)) {
            fw.write(score + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Integer> load() {

        List<Integer> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;

            while ((line = br.readLine()) != null) {
                list.add(Integer.parseInt(line));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        list.sort(Collections.reverseOrder());
        return list;
    }
}