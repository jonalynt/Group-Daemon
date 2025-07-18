import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

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
        
        JLabel imagelabel = new JLabel(new ImageIcon("src\\images\\motorphimage.png"));
       
        leftPanel.add(welcomeLabel, BorderLayout.NORTH);
        leftPanel.add(imagelabel);

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

        JButton loginButton = new JButton("Log in");
        loginButton.setBounds(160, 170, 90, 30);
        rightPanel.add(loginButton);
        
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(270, 170, 90, 30);
        rightPanel.add(exitButton);

        // button action
        exitButton.addActionListener(e -> System.exit(0));

        loginButton.addActionListener(e -> {
            
            if(empField.getText().isEmpty() || passField.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "Please fill in your username or password.");
            }
            else{
                login();
            }
                    
                    });
        setVisible(true);

        // Add panels to frame
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        // Set size of left panel
        leftPanel.setPreferredSize(new Dimension(400, getHeight()));
    }

    private void login(){
        String inputUsername = empField.getText().trim();
        String inputPassword = new String(passField.getPassword()).trim();
        
        File file = new File("src\\MotorPH_EmployeeLogin.csv");
        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "User credentials file not found.");
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean header = true;
            while ((line = reader.readLine()) != null) {
                if (header) { header = false; continue; }
                String[] parts = line.split(",", -1);
                if (parts.length >= 3) {
                    String employeenum = parts[0].trim();
                    String username = parts[1].trim();
                    String firstname = parts[3].trim();
                    String password = parts[4].trim();
                    String accesslevel = parts[5].trim();

                    if (inputUsername.equalsIgnoreCase(username) && inputPassword.equals(password)) {
                            JOptionPane.showMessageDialog(this, "Welcome, " + firstname + "!");
                            new DashboardPanel(employeenum, accesslevel);
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
    
    
    // old log in method authenticate
   /* private boolean authenticate(String empNo, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader("src\\MotorPH_EmployeeData.csv"))) {
            String headerLine = br.readLine(); // Read header
            if (headerLine == null) return false;

            String[] headers = headerLine.split(",");

            // Find index of employeeNo and password
            int empIndex = -1;
            int passIndex = -1;

            for (int i = 0; i < headers.length; i++) {
                String header = headers[i].trim();
                if (header.equals("EmployeeNo")) {
                    empIndex = i;
                } else if (header.equals("Password")) {
                    passIndex = i;
                }
            }

            // If either index is not found, return false
            if (empIndex == -1 || passIndex == -1) {
                JOptionPane.showMessageDialog(this, "CSV headers missing 'EmployeeNo' or 'Password'.");
                return false;
            }

            // Read the rest of the file and compare values
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > Math.max(empIndex, passIndex)) {
                    String fileEmpNo = parts[empIndex].trim();
                    String filePass = parts[passIndex].trim();
                    if (fileEmpNo.equals(empNo) && filePass.equals(password)) {
                        return true;
                    }
                }
            }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Error reading employee data.");
                    e.printStackTrace();
                }
                return false;
            } */

}
