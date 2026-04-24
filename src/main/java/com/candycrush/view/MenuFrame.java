package com.candycrush.view;

import com.candycrush.controller.GameController;

import javax.swing.*;
import java.awt.*;

public class MenuFrame extends JFrame {

    public MenuFrame() {

        setTitle("Candy Crush");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel logo = new JLabel();
        logo.setHorizontalAlignment(SwingConstants.CENTER);

        java.net.URL url = ClassLoader.getSystemResource("logo.png");

        if (url != null) {
            ImageIcon icon = new ImageIcon(url);
            Image img = icon.getImage().getScaledInstance(250, 120, Image.SCALE_SMOOTH);
            logo.setIcon(new ImageIcon(img));
        } else {
            logo.setText("🍬 CANDY CRUSH");
        }

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        JButton play = new JButton("▶ Jouer");
        JButton unlimited = new JButton("🎮 Mode illimité");
        JButton scores = new JButton("🏆 Scores");
        JButton quit = new JButton("❌ Quitter");

        // 🎮 MODE NORMAL
        play.addActionListener(e -> {
            GameController c = new GameController();
            GameFrame f = new GameFrame(c);
            c.setView(f);
            f.setVisible(true);
            dispose();
        });

        // ⭐ MODE ILLIMITÉ
        unlimited.addActionListener(e -> {
            GameController c = new GameController();
            c.setUnlimitedMode(true);

            GameFrame f = new GameFrame(c);
            c.setView(f);

            f.setVisible(true);
            dispose();
        });

        scores.addActionListener(e -> new ScoreFrame());
        quit.addActionListener(e -> System.exit(0));

        panel.add(play);
        panel.add(unlimited);
        panel.add(scores);
        panel.add(quit);

        add(logo, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
    }
}