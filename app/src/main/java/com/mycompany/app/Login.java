package com.mycompany.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    JLabel user = new JLabel("User");
    JLabel password = new JLabel("Password");
    JLabel errorLabel = new JLabel("");

    JTextField userField = new JTextField(15);
    JPasswordField passwordField = new JPasswordField(15);

    JButton okButton = new JButton("LOGIN");
    JButton annulerButton = new JButton("BACK");

    public Login() {
        super("Login");

        setSize(420, 340);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(10, 15, 30));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 200, 255), 2));

        JLabel title = new JLabel("LOGIN");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(0, 200, 255));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        styleLabel(user);
        styleLabel(password);

        styleField(userField);
        styleField(passwordField);

        errorLabel.setForeground(new Color(255, 80, 80));
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        styleButton(okButton, new Color(0, 200, 255));
        styleButton(annulerButton, new Color(255, 80, 80));

        okButton.addActionListener(this);
        annulerButton.addActionListener(this);

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = new String(passwordField.getPassword());
                if (text.contains(" ")) {
                    errorLabel.setText("Password can't contain spaces");
                    return;
                } else {
                    errorLabel.setText("");
                }
            }
        });

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(user);
        mainPanel.add(userField);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(password);
        mainPanel.add(passwordField);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(errorLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(okButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(annulerButton);
        mainPanel.add(Box.createVerticalStrut(20));

        setContentPane(mainPanel);
        setVisible(true);
    }

    private void styleLabel(JLabel label) {
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void styleField(JTextField field) {
        field.setMaximumSize(new Dimension(220, 30));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBackground(new Color(25, 30, 50));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createLineBorder(new Color(0, 200, 255)));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void styleButton(JButton button, Color color) {
        button.setMaximumSize(new Dimension(160, 35));
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            verifyLogin();
        } else if (e.getSource() == annulerButton) {
            dispose();
            new Welcome();
        }
    }

    private void verifyLogin() {
        String username = userField.getText().trim();
        String passwordd = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || passwordd.isEmpty()) {
            errorLabel.setText("Please fill all fields");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM members WHERE email=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, passwordd);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                new Choice(); 
                dispose();
            } else {
                errorLabel.setText("Invalid credentials");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

