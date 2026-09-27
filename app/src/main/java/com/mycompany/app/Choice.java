package com.mycompany.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Choice extends JFrame implements ActionListener {

    JRadioButton voiruserButton = new JRadioButton("Voir user");
    JRadioButton ajoutActButton = new JRadioButton("Ajouter activité");

    JRadioButton voirActButton = new JRadioButton("Voir activité");

    ButtonGroup group = new ButtonGroup();

    JButton okButton = new JButton("OK");
    JButton annulerButton = new JButton("Annuler");

    public Choice() {
        super("Choice");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(15, 20, 35);
        Color accentColor = new Color(0, 180, 255);
        Color cardColor = new Color(25, 30, 50);

        styleRadio(voiruserButton, cardColor);
        styleRadio(ajoutActButton, cardColor);
        styleRadio(voirActButton, cardColor); 

      
        group.add(voiruserButton);
        group.add(ajoutActButton);
        group.add(voirActButton); 

        styleButton(okButton, accentColor, Color.BLACK);
        styleButton(annulerButton, new Color(220, 53, 69), Color.WHITE);

        okButton.addActionListener(this);
        annulerButton.addActionListener(this);

        JPanel p = new JPanel();
        p.setBackground(backgroundColor);
        p.setLayout(new BorderLayout(0, 20));
        p.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        JLabel title = new JLabel("CHOIX", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(accentColor);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(cardColor);
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(accentColor, 1),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));

        voiruserButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        ajoutActButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        voirActButton.setAlignmentX(Component.LEFT_ALIGNMENT); 

        centerPanel.add(voiruserButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(ajoutActButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(voirActButton); 

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.add(okButton);
        buttonPanel.add(annulerButton);

                
        p.add(title, BorderLayout.NORTH);
        p.add(centerPanel, BorderLayout.CENTER);
        p.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(p);
        setVisible(true);
    }

    private void styleRadio(JRadioButton radio, Color bg) {
        radio.setForeground(Color.WHITE);
        radio.setBackground(bg);
        radio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        radio.setFocusPainted(false);
    }

    private void styleButton(JButton button, Color bg, Color fg) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(140, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == annulerButton) {
            dispose();
            new Welcome();

        } else if (e.getSource() == okButton) {

            if (voiruserButton.isSelected()) {
                new Users();
                dispose();

            } else if (ajoutActButton.isSelected()) {
                new AddActivity();
                dispose();

            } else if (voirActButton.isSelected()) { 
                new ActivityCalendar();
                dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Please select an option!");
            }
        }
    }
}
