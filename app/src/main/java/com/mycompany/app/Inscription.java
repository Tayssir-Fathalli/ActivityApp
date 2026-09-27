package com.mycompany.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Inscription extends JFrame implements ActionListener {

    JLabel nom = new JLabel("NOM");
    JLabel prenom = new JLabel("PRENOM");
    JLabel email = new JLabel("E-mail");
    JLabel club = new JLabel("CLUB");

    JLabel password = new JLabel("PASSWORD");

    JLabel errorLabel = new JLabel(" ");

    JTextField nomField = new JTextField(15);
    JTextField prenomField = new JTextField(15);
    JTextField emailField = new JTextField(15);

    JPasswordField passwordField = new JPasswordField(15);

    JComboBox<String> clubBox;

    JButton okButton = new JButton("OK");
    JButton annulerButton = new JButton("Annuler");

    public Inscription() {
        super("Inscription");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(15, 20, 35);
        Color cardColor = new Color(25, 30, 50);
        Color accentColor = new Color(0, 180, 255);

        clubBox = new JComboBox<>();
        clubBox.setBackground(cardColor);
        clubBox.setForeground(Color.WHITE);
        clubBox.setBorder(BorderFactory.createLineBorder(accentColor));
        loadClubsFromDB();

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        nom.setFont(labelFont);
        prenom.setFont(labelFont);
        email.setFont(labelFont);
        club.setFont(labelFont);
        password.setFont(labelFont); 

        nom.setForeground(Color.WHITE);
        prenom.setForeground(Color.WHITE);
        email.setForeground(Color.WHITE);
        club.setForeground(Color.WHITE);
        password.setForeground(Color.WHITE); 
        
        styleField(nomField);
        styleField(prenomField);
        styleField(emailField);
        stylePasswordField(passwordField); 

        errorLabel.setForeground(Color.RED);

        styleButton(okButton, accentColor, Color.BLACK);
        styleButton(annulerButton, new Color(220, 53, 69), Color.WHITE);

        JPanel p = new JPanel();
        p.setBackground(backgroundColor);
        p.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        p.setLayout(new GridLayout(7, 2, 10, 12));

        p.add(nom);      p.add(nomField);
        p.add(prenom);   p.add(prenomField);
        p.add(email);    p.add(emailField);

        p.add(password);
        p.add(passwordField);

        p.add(club);     p.add(clubBox);
        p.add(errorLabel); p.add(new JLabel(""));
        p.add(okButton); p.add(annulerButton);

        okButton.addActionListener(this);
        annulerButton.addActionListener(this);

        setContentPane(p);
        setVisible(true);
    }

    private void styleField(JTextField field) {
        field.setBackground(cardColor());
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createLineBorder(new Color(0, 180, 255)));
    }

    private void stylePasswordField(JPasswordField field) {
        field.setBackground(cardColor());
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createLineBorder(new Color(0, 180, 255)));
    }

    private void styleButton(JButton button, Color bg, Color fg) {
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }

    private Color cardColor() {
        return new Color(25, 30, 50);
    }

    private void loadClubsFromDB() {
        try (Connection con = DBConnection.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT nameclub FROM clubs");

            while (rs.next()) {
                clubBox.addItem(rs.getString("nameclub"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading clubs");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            verifyLogin();
        } else {
            dispose();
            new Welcome();
        }
    }

    private void verifyLogin() {
        String username = nomField.getText();
        String prenomm = prenomField.getText();
        String emaill = emailField.getText();
        String clubb = (String) clubBox.getSelectedItem();

        String passwordd = new String(passwordField.getPassword());

        if (username.isEmpty() || prenomm.isEmpty() || emaill.isEmpty()
                || passwordd.isEmpty() || clubb == null) {
            errorLabel.setText("Please fill all fields");
            return;
        }

        if (passwordd.length() < 6) {
            errorLabel.setText("Password must be at least 6 characters");
            return;
        }

        if (!emaill.contains("@")) {
            errorLabel.setText("Invalid email");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO members (nom, prenom, email, password, club) VALUES (?, ?, ?, ?, ?)"
            );

            ps.setString(1, username);
            ps.setString(2, prenomm);
            ps.setString(3, emaill);
            ps.setString(4, passwordd); 
            ps.setString(5, clubb);

            ps.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error");
            return;
        }

        new ActivityCalendar();
        dispose();
    }
}
