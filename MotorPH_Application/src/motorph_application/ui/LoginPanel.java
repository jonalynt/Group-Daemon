package motorph_application.ui;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import motorph_application.utils.Constants;
import motorph_application.utils.UIUtils;

public class LoginPanel extends JFrame {
    private JTextField empField;
    private JPasswordField passField;

    public LoginPanel() {
        setTitle("MotorPH Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Left Panel
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(128, 0, 0));

        JLabel welcomeLabel = new JLabel("Welcome to MotorPH", SwingConstants.CENTER);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(50, 30, 300, 25);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(60, 0, -5, 0));

        JLabel imageLabel = new JLabel(new ImageIcon("src/images/motorphimage.png"));
        leftPanel.add(welcomeLabel, BorderLayout.NORTH);
        leftPanel.add(imageLabel);

        // Right Panel (Login Form)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBackground(Color.LIGHT_GRAY);

        JLabel loginLabel = new JLabel("Please log in with your credentials");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 14));
        loginLabel.setBounds(50, 30, 300, 25);
        rightPanel.add(loginLabel);

        JLabel empLabel = new JLabel("Username :");
        empLabel.setBounds(50, 80, 100, 25);
        rightPanel.add(empLabel);

        empField = new JTextField();
        empField.setBounds(160, 80, 200, 25);
        rightPanel.add(empField);

        JLabel passLabel = new JLabel("Password :");
        passLabel.setBounds(50, 120, 100, 25);
        rightPanel.add(passLabel);

        passField = new JPasswordField();
        passField.setBounds(160, 120, 200, 25);
        rightPanel.add(passField);

        JButton loginButton = UIUtils.createButton("Log in", Color.white, Color.black);
        loginButton.setBounds(160, 170, 90, 30);
        rightPanel.add(loginButton);

        JButton exitButton = UIUtils.createButton("Exit", Color.white, Color.black);
        exitButton.setBounds(270, 170, 90, 30);
        rightPanel.add(exitButton);

        exitButton.addActionListener(e -> System.exit(0));
        loginButton.addActionListener(e -> {
            if (empField.getText().isEmpty() || passField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in your username or password.");
            } else {
                login();
            }
        });

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
        leftPanel.setPreferredSize(new Dimension(400, getHeight()));
        setVisible(true);
    }

    private void login() {
        String inputUsername = empField.getText().trim();
        String inputPassword = new String(passField.getPassword()).trim();

        File file = new File(Constants.LOGIN_CSV);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "User credentials file not found.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean header = true;
            while ((line = reader.readLine()) != null) {
                if (header) {
                    header = false;
                    continue;
                }
                String[] parts = line.split(",", -1);
                if (parts.length >= 6) {
                    String employeeNum = parts[0].trim();
                    String username = parts[1].trim();
                    String lastName = parts[2].trim();
                    String firstName = parts[3].trim();
                    String password = parts[4].trim();
                    String accessLevel = parts[5].trim();

                    if (inputUsername.equalsIgnoreCase(username) && inputPassword.equals(password)) {
                        JOptionPane.showMessageDialog(this, "Welcome, " + firstName + "!");
                        new DashboardPanel(employeeNum, accessLevel, employeeNum, lastName, firstName);
                        dispose();
                        return;
                    }
                }
            }
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading login file: " + e.getMessage());
        }
    }
}