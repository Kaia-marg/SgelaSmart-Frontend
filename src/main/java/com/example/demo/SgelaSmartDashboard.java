package com.example.demo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SgelaSmartDashboard extends JFrame {

    // --- Gen Z / Teen Color Palette ---
    private static final Color DARK_BG = new Color(30, 30, 46);        // Charcoal Navy
    private static final Color CARD_BG = new Color(40, 42, 60);        // Soft Charcoal Card
    private static final Color TEXT_WHITE = new Color(248, 248, 242);   // High-Contrast White
    private static final Color MINT_GREEN = new Color(0, 245, 212);    // Gen Z Mint Green Accent
    private static final Color BRIGHT_PINK = new Color(255, 0, 127);   // Electric Pink Accent
    private static final Color GENZ_YELLOW = new Color(255, 209, 102); // Vibrant Yellow
    private static final Color TEAL_BLUE = new Color(0, 187, 249);     // Electric Teal
    private static final Color MUTED_TEXT = new Color(144, 153, 172);   // Soft Secondary Gray

    // Timer Variables
    private Timer timer;
    private int secondsRemaining = 25 * 60; // 25 Minutes Default
    private JLabel timerLabel;
    private boolean isTimerRunning = false;

    public SgelaSmartDashboard() {
        setTitle("SgelaSmart - Student Dashboard");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(DARK_BG);

        // Main Layout Structure
        setLayout(new BorderLayout());

        // Header Banner
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Tabbed Pane for Navigation
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(DARK_BG);
        tabbedPane.setForeground(TEXT_WHITE);
        tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 14));

        // Add the 3 Core Required Modules
        tabbedPane.addTab("🎯 Goals & Progress", createGoalsPanel());
        tabbedPane.addTab("📋 Task Execution Queue", createTasksPanel());
        tabbedPane.addTab("⏱️ Deep Work Timer", createPomodoroPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    // --- Header Panel ---
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(DARK_BG);
        header.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("⚡ SgelaSmart Dashboard");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(MINT_GREEN);

        JLabel subtitle = new JLabel("Level Up Your Focus & Academic Goals");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(MUTED_TEXT);

        JPanel textGroup = new JPanel(new GridLayout(2, 1));
        textGroup.setOpaque(false);
        textGroup.add(title);
        textGroup.add(subtitle);

        header.add(textGroup, BorderLayout.WEST);
        return header;
    }

    // --- Module 1: Goals & Progress Panel ---
    private JPanel createGoalsPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(DARK_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        String[] columns = {"Goal Name", "Category", "Deadline", "Progress (%)"};
        Object[][] data = {
            {"Master Java Swing Architecture", "Software Dev", "2026-11-05", "85%"},
            {"Complete CompTIA Security+ Prep", "Certifications", "2026-11-20", "50%"},
            {"Submit Group Project Code", "Academic", "2026-10-30", "90%"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);
        styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(CARD_BG);
        scrollPane.setBorder(BorderFactory.createLineBorder(CARD_BG));

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    // --- Module 2: Task Execution Queue Panel ---
    private JPanel createTasksPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(DARK_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        DefaultListModel<String> taskModel = new DefaultListModel<>();
        taskModel.addElement("⚡ Setup GitHub Collaborator permissions");
        taskModel.addElement("⚡ Review Swing UI components against spec");
        taskModel.addElement("⚡ Connect Spring Boot API endpoints");

        JList<String> taskList = new JList<>(taskModel);
        taskList.setBackground(CARD_BG);
        taskList.setForeground(TEXT_WHITE);
        taskList.setFont(new Font("SansSerif", Font.PLAIN, 15));
        taskList.setSelectionBackground(TEAL_BLUE);
        taskList.setSelectionForeground(DARK_BG);
        taskList.setFixedCellHeight(40);

        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setBorder(BorderFactory.createLineBorder(CARD_BG));

        // Input controls for new tasks
        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.setOpaque(false);

        JTextField taskField = new JTextField();
        taskField.setBackground(CARD_BG);
        taskField.setForeground(TEXT_WHITE);
        taskField.setCaretColor(MINT_GREEN);
        taskField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        taskField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MUTED_TEXT, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        JButton addButton = createStyledButton("Add Task", MINT_GREEN, DARK_BG);
        addButton.addActionListener(e -> {
            String text = taskField.getText().trim();
            if (!text.isEmpty()) {
                taskModel.addElement("⚡ " + text);
                taskField.setText("");
            }
        });

        inputPanel.add(taskField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.SOUTH);

        return panel;
    }

    // --- Module 3: Deep Work Pomodoro Timer Panel ---
    private JPanel createPomodoroPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(DARK_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        // Big Digital Clock
        timerLabel = new JLabel("25:00");
        timerLabel.setFont(new Font("SansSerif", Font.BOLD, 72));
        timerLabel.setForeground(GENZ_YELLOW);

        gbc.gridy = 0;
        panel.add(timerLabel, gbc);

        // Control Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);

        JButton startButton = createStyledButton("Start Focus", MINT_GREEN, DARK_BG);
        JButton pauseButton = createStyledButton("Pause", BRIGHT_PINK, TEXT_WHITE);
        JButton resetButton = createStyledButton("Reset", TEAL_BLUE, DARK_BG);

        buttonPanel.add(startButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(resetButton);

        gbc.gridy = 1;
        panel.add(buttonPanel, gbc);

        // Timer Logic
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (secondsRemaining > 0) {
                    secondsRemaining--;
                    int mins = secondsRemaining / 60;
                    int secs = secondsRemaining % 60;
                    timerLabel.setText(String.format("%02d:%02d", mins, secs));
                } else {
                    timer.stop();
                    isTimerRunning = false;
                    JOptionPane.showMessageDialog(null, "🔥 Focus Session Complete! Take a break.");
                }
            }
        });

        startButton.addActionListener(e -> {
            if (!isTimerRunning) {
                timer.start();
                isTimerRunning = true;
            }
        });

        pauseButton.addActionListener(e -> {
            timer.stop();
            isTimerRunning = false;
        });

        resetButton.addActionListener(e -> {
            timer.stop();
            isTimerRunning = false;
            secondsRemaining = 25 * 60;
            timerLabel.setText("25:00");
        });

        return panel;
    }

    // --- Helper Component Style Methods ---
    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton button = new JButton(text);
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 18, 10, 18));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void styleTable(JTable table) {
        table.setBackground(CARD_BG);
        table.setForeground(TEXT_WHITE);
        table.setGridColor(DARK_BG);
        table.setRowHeight(35);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setSelectionBackground(TEAL_BLUE);
        table.setSelectionForeground(DARK_BG);

        JTableHeader header = table.getTableHeader();
        header.setBackground(DARK_BG);
        header.setForeground(MINT_GREEN);
        header.setFont(new Font("SansSerif", Font.BOLD, 14));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SgelaSmartDashboard().setVisible(true);
        });
    }
}