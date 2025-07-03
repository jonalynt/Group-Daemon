
import java.awt.*;
import java.io.File;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DashboardPanel extends JFrame {
    CardLayout cardLayout;
    JPanel cardPanel;

    public employeeDashboard homePanel = new employeeDashboard();
    
    //nav buttons
    JButton btnDatabase = new JButton("Employee Database");
    JButton btnAdd = new JButton("Add Employee");
    JButton btnLogout = new JButton("Log out");
    JButton btnfulldetails = new JButton("View Full Details");
    
    String selectedEmpNo;
    
    public DashboardPanel(String username) {

        setTitle("MotorPH Admin Dashboard");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Left navigation panel
        JPanel navPanel = new JPanel();
        navPanel.setBackground(new Color(128, 0, 0));
        navPanel.setPreferredSize(new Dimension(200, getHeight()));
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));

        JLabel welcome = new JLabel("Welcome to MOTORPH");
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcome.setForeground(Color.WHITE);
        
        JLabel admin = new JLabel("Admin Dashboard");
        admin.setFont(new Font("Arial", Font.BOLD, 20));
        admin.setAlignmentX(Component.CENTER_ALIGNMENT);
        admin.setForeground(Color.WHITE);

        btnDatabase.setMaximumSize(new Dimension(180, 30));
        btnDatabase.setAlignmentX(Component.CENTER_ALIGNMENT);
 
        btnAdd.setMaximumSize(new Dimension(180, 30));
        btnAdd.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnfulldetails.setMaximumSize(new Dimension(180, 30));
        btnfulldetails.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        btnLogout.setMaximumSize(new Dimension(180, 30));
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        navPanel.add(Box.createVerticalStrut(20));
        navPanel.add(welcome);
        navPanel.add(Box.createVerticalStrut(5));
        navPanel.add(admin);
        navPanel.add(Box.createVerticalStrut(20));
        navPanel.add(btnDatabase);
        navPanel.add(Box.createVerticalStrut(10));
        navPanel.add(btnfulldetails);
        navPanel.add(Box.createVerticalStrut(10));
        navPanel.add(btnAdd);
        navPanel.add(Box.createVerticalStrut(50));
        navPanel.add(btnLogout);
 
        // Card Panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        AddEmployeePanel addEmpPanel = new AddEmployeePanel();
        FullDetailsPanel fullEmpPanel = new FullDetailsPanel();
        
        cardPanel.add(homePanel, "Home");
        
        btnDatabase.addActionListener(e-> {
            selectedEmpNo = null;
            homePanel.reloadCSV();
            homePanel.clearfields();
            cardPanel.removeAll();
            cardPanel.add(homePanel);
            cardPanel.revalidate();
            cardPanel.repaint();
        });
        btnAdd.addActionListener(e -> {
            cardPanel.removeAll();
            cardPanel.add(addEmpPanel);
            cardPanel.revalidate();
            cardPanel.repaint();
        });
        btnfulldetails.addActionListener(e -> {
            selectedEmpNo = homePanel.getSelectedEmployeeNo();
            fullEmpPanel.setEmployeeNo(selectedEmpNo);
            if (selectedEmpNo == null || selectedEmpNo.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select an employee.", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
            else {
            EmployeeData data = homePanel.getFormData();

            cardPanel.removeAll();
            cardPanel.add(fullEmpPanel);
            cardPanel.revalidate();
            cardPanel.repaint();
            }
        });
            
        btnLogout.addActionListener(e -> {
            new LoginPanel();
            dispose();
        });

        add(navPanel, BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}
