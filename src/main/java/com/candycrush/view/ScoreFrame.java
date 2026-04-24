package com.candycrush.view;

import com.candycrush.model.ScoreManager;

import javax.swing.*;
import java.util.List;

public class ScoreFrame extends JFrame {

    public ScoreFrame() {

        setTitle("Scores");
        setSize(300, 400);
        setLocationRelativeTo(null);

        List<Integer> scores = ScoreManager.load();

        DefaultListModel<String> model = new DefaultListModel<>();

        int rank = 1;
        for (int s : scores) {
            model.addElement("#" + rank + " - " + s);
            rank++;
        }

        add(new JScrollPane(new JList<>(model)));

        setVisible(true);
    }
}