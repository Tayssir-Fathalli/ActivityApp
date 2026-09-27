package com.mycompany.app;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddActivity extends JFrame implements ActionListener {
    JLabel activity = new JLabel("Activité");
    JLabel type = new JLabel("Type");
    JLabel date = new JLabel("Date");
    
    JTextField activityField = new JTextField(15);
    JTextField typeField = new JTextField(15);
    JTextField dateField = new JTextField(15);
    
    JButton okButton = new JButton("OK");
    JButton annulerButton = new JButton("Annuler");
    
    public AddActivity() {
        super("Add Activity");
        setSize(460, 340);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        Color backgroundColor = new Color(15, 20, 35);
        Color cardColor = new Color(25, 30, 50);
        Color accentColor = new Color(0, 180, 255);
        
        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        activity.setFont(labelFont);
        type.setFont(labelFont);
        date.setFont(labelFont);
        
        activity.setForeground(Color.WHITE);
        type.setForeground(Color.WHITE);
        date.setForeground(Color.WHITE);
        
        styleField(activityField, cardColor, accentColor);
        styleField(typeField, cardColor, accentColor);
        styleField(dateField, cardColor, accentColor);
        
        styleButton(okButton, accentColor, Color.BLACK);
        styleButton(annulerButton, new Color(220, 53, 69), Color.WHITE);
        
        okButton.addActionListener(this);
        annulerButton.addActionListener(this);
        
        JPanel p = new JPanel();
        p.setBackground(backgroundColor);
        p.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        p.setLayout(new GridLayout(4, 2, 12, 15));
        
        p.add(activity);
        p.add(activityField);
        p.add(type);
        p.add(typeField);
        p.add(date);
        p.add(dateField);
        p.add(okButton);
        p.add(annulerButton);
        
        setContentPane(p);
        setVisible(true);
    }
    
    private void styleField(JTextField field, Color bg, Color borderColor) {
        field.setBackground(bg);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createLineBorder(borderColor));
    }
    
    private void styleButton(JButton button, Color bg, Color fg) {
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            addActivity();
        } else if (e.getSource() == annulerButton) {
            dispose();
        }
    }
    
    private void addActivity() {
        String activityy = activityField.getText().trim();
        String typee = typeField.getText().trim();
        String datee = dateField.getText().trim();
        
        if (activityy.isEmpty() || typee.isEmpty() || datee.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Please fill all fields",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        
        if (!isValidDateFormat(datee)) {
            JOptionPane.showMessageDialog(
                this,
                "Invalid date format. Please use YYYY-MM-DD\nExample: 2025-12-20",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = DBConnection.getConnection();
            
            if (con == null) {
                JOptionPane.showMessageDialog(
                    this,
                    "Database connection failed. Please check your database configuration.",
                    "Connection Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            ps = con.prepareStatement(
                "INSERT INTO events (activity, type, date_event) VALUES (?, ?, ?)"
            );
            ps.setString(1, activityy);
            ps.setString(2, typee);
            ps.setDate(3, java.sql.Date.valueOf(datee));
            
            ps.executeUpdate();
            
            JOptionPane.showMessageDialog(
                this,
                "Activity added successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );
            new ActivityCalendar();
            dispose();
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                this,
                "Database error: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                this,
                "Error while adding activity: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private boolean isValidDateFormat(String date) {
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return false;
        }
        
        try {
            java.sql.Date.valueOf(date);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    
    private void insertActivityIntoDatabase(String activityy, String typee, String datee) {
        System.out.println("INSERT INTO activity (name, type, date)");
        System.out.println("VALUES ('" + activityy + "', '" + typee + "', '" + datee + "')");
    }
}