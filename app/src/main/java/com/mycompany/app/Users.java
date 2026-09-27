package com.mycompany.app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.sql.*;

public class Users extends JFrame {

    private String[] columns = {"Nom", "Prénom", "Email"};

    public Users() {
        super("Users");

        setSize(520, 340);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(15, 20, 35);
        Color cardColor = new Color(25, 30, 50);
        Color accentColor = new Color(0, 180, 255);

        Object[][] data = loadData();

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        table.setRowHeight(26);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setBackground(cardColor);
        table.setForeground(Color.WHITE);
        table.setGridColor(accentColor);
        table.setSelectionBackground(accentColor);
        table.setSelectionForeground(Color.BLACK);

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(20, 25, 45));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));

        scroll.getViewport().setBackground(backgroundColor);
        scroll.setBorder(BorderFactory.createLineBorder(accentColor));

        JLabel titleLabel = new JLabel("Liste des utilisateurs", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(accentColor);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(backgroundColor);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        setContentPane(panel);
        setVisible(true);
    }

    private Object[][] loadData() {
        ArrayList<Object[]> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT nom, prenom, email FROM members");

            while (rs.next()) {
                list.add(new Object[]{
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading members from database", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return list.toArray(new Object[0][]);
    }
}
