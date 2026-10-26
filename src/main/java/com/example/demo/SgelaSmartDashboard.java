package com.example.demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SgelaSmartDashboard extends JFrame {

    private Timer pomodoroTimer;
    private int timeLeftSeconds = 25 * 60; // 25 minutes default Pomodoro
    private JLabel timerLabel;
    private DefaultListModel<String> taskListModel;

    public SgelaSmartDashboard() {
        // Window Settings
        setTitle("SgelaSmart - Student Task & Goal Management Platform");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Layout (Tabbed Pane for different sections)
        JTabbedPane tabbedPane = new JTabbedPane();

        // 1. Dashboard / Goals Tab
        tabbedPane.add("Goals & Progress", createGoalsPanel());

        // 2. Tasks Tab
        tabbedPane.add("Task Manager", createTasksPanel());

        // 3. Deep Work Pomodoro Tab
        tabbedPane.add("Deep Work Timer", createPomodoroPanel());

        add(tabbedPane);
    }

    private JPanel createGoalsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Active Academic Goals & Fractional Timelines", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        
        String[] columns = {"Goal Title", "Deadline", "Template", "Progress Stage"};
        Object[][] data = {
            {"Pass Operating Systems Exam", "2026-11-10", "Exam Preparation", "Concept Mastery (40%)"},
            {"Mobile App Architecture Essay", "2026-11-15", "Assignment/Essay", "Drafting (50%)"},
            {"Java Swing PIMS Project", "2026-11-09", "Software Practical", "Implementation (50%)"}
        };
        
        JTable table = new JTable(data, columns);
        panel.add(title, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createTasksPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        taskListModel = new DefaultListModel<>();
        taskListModel.addElement("[ ] Study Virtual Memory Paging Algorithms");
        taskListModel.addElement("[ ] Draft Literature Review Citations");
        taskListModel.addElement("[ ] Test Spring Boot REST Endpoints");

        JList<String> taskList = new JList<>(taskListModel);
        JScrollPane scrollPane = new JScrollPane(taskList);

        JPanel inputPanel = new JPanel(new FlowLayout());
        JTextField taskInput = new JTextField(20);
        JButton addButton = new JButton("Add Task");

        addButton.addActionListener(e -> {
            if (!taskInput.getText().trim().isEmpty()) {
                taskListModel.addElement("[ ] " + taskInput.getText().trim());
                taskInput.setText("");
            }
        });

        inputPanel.add(new JLabel("New Task:"));
        inputPanel.add(taskInput);
        inputPanel.add(addButton);

        panel.add(new JLabel("Your Task Execution Queue", JLabel.CENTER), BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createPomodoroPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 248, 255));

        JLabel header = new JLabel("Deep Work Focus Timer");
        header.setFont(new Font("Arial", Font.BOLD, 22));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        timerLabel = new JLabel("25:00");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 48));
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startButton = new JButton("Start Focus Session");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        pomodoroTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeLeftSeconds > 0) {
                    timeLeftSeconds--;
                    int mins = timeLeftSeconds / 60;
                    int secs = timeLeftSeconds % 60;
                    timerLabel.setText(String.format("%02d:%02d", mins, secs));
                } else {
                    ((Timer)e.getSource()).stop();
                    JOptionPane.showMessageDialog(null, "Focus session complete! Logged to database.");
                }
            }
        });

        startButton.addActionListener(e -> pomodoroTimer.start());

        panel.add(Box.createVerticalStrut(50));
        panel.add(header);
        panel.add(Box.createVerticalStrut(30));
        panel.add(timerLabel);
        panel.add(Box.createVerticalStrut(30));
        panel.add(startButton);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SgelaSmartDashboard().setVisible(true);
        });
    }
}