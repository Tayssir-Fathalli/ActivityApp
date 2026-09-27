package com.mycompany.app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class ActivityCalendar extends JFrame implements ActionListener {

    private String[] columns = {"Event", "Date"};
    private JTable table;
    private JButton okButton = new JButton("OK");
    private JButton annulerButton = new JButton("Annuler");

    public ActivityCalendar() {
        super("Calendrier Activités");

        setSize(560, 400);
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

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(backgroundColor);
        scroll.setBorder(BorderFactory.createLineBorder(accentColor));

        JLabel titleLabel = new JLabel("Calendrier Activités", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(accentColor);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(backgroundColor);
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        styleButton(okButton, accentColor, Color.BLACK);
        styleButton(annulerButton, new Color(220, 53, 69), Color.WHITE);

        okButton.addActionListener(this);
        annulerButton.addActionListener(this);

        buttonsPanel.add(okButton);
        buttonsPanel.add(annulerButton);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(backgroundColor);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);

        setContentPane(panel);
        setVisible(true);
    }

    private Object[][] loadData() {
        ArrayList<Object[]> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT activity, date_event FROM events");

            while (rs.next()) {
                list.add(new Object[]{rs.getString("activity"), rs.getDate("date_event").toString()});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list.toArray(new Object[0][]);
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
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an event first", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String event = table.getValueAt(selectedRow, 0).toString();
            String date = table.getValueAt(selectedRow, 1).toString();

            JOptionPane.showMessageDialog(this, "You selected:\nEvent: " + event + "\nDate: " + date, "Event Selected", JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource() == annulerButton) {
            new Choice();
            dispose();
        }
    }
}
