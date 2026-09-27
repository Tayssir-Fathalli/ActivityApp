package com.mycompany.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Welcome extends JFrame implements ActionListener {

    JRadioButton inscrireButton = new JRadioButton("S'inscrire");
    JRadioButton connecterButton = new JRadioButton("Se connecter");
    ButtonGroup group = new ButtonGroup();

    JButton okButton = new JButton("OK");
    JButton annulerButton = new JButton("Annuler");

    public Welcome() {
        super("Bienvenue");
        setSize(420, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel p = new JPanel();
        p.setBackground(new Color(10, 15, 30));
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createLineBorder(new Color(0, 200, 255), 2));

        
        JLabel title = new JLabel("Bienvenue");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(0, 200, 255));

        styleRadio(inscrireButton);
        styleRadio(connecterButton);

        group.add(inscrireButton);
        group.add(connecterButton);

        styleButton(okButton, new Color(0, 200, 255));
        styleButton(annulerButton, new Color(255, 80, 80));

        okButton.addActionListener(this);
        annulerButton.addActionListener(this);

        p.add(Box.createVerticalStrut(25));
        p.add(title);
        p.add(Box.createVerticalStrut(30));
        p.add(connecterButton);
        p.add(Box.createVerticalStrut(10));
        p.add(inscrireButton);
        p.add(Box.createVerticalStrut(30));
        p.add(okButton);
        p.add(Box.createVerticalStrut(10));
        p.add(annulerButton);
        p.add(Box.createVerticalStrut(20));

        setContentPane(p);
        setVisible(true);
    }

    private void styleRadio(JRadioButton radio) {
        radio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        radio.setForeground(Color.WHITE);
        radio.setBackground(new Color(10, 15, 30));
        radio.setFocusPainted(false);
        radio.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void styleButton(JButton button, Color glowColor) {
        button.setMaximumSize(new Dimension(180, 35));
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.BLACK);
        button.setBackground(glowColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            if (connecterButton.isSelected()) {
                new Login();
                dispose();
            } else if (inscrireButton.isSelected()) {
                new Inscription();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please select an option!");
            }
        } else if (e.getSource() == annulerButton) {
            System.exit(0);
        }
    }
}
