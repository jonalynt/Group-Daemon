
import java.awt.*;
import java.io.File;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DashboardPanel extends JFrame {
    CardLayout cardLayout;
    JPanel cardPanel;

    String userRole;
    String userEmpNo;
    
    employeeDashboard homePanel = new employeeDashboard();
    AddEmployeePanel addEmpPanel = new AddEmployeePanel();
    FullDetailsPanel fullEmpPanel = new FullDetailsPanel();
    LeavePanel leaveapp = new LeavePanel();
    
    //nav buttons
    JButton btnDatabase = new JButton("Employee Database");
    JButton btnAdd = new JButton("Add Employee");
    JButton btnLogout = new JButton("Log out");
    JButton btnfulldetails = new JButton("View Full Details");
    
    JButton btnLeave = new JButton("Leave Application");
    JButton btnEmpHome = new JButton("Home");
    
    String selectedEmpNo;
    
    public DashboardPanel(String username, String role, String firstname) {

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
        
        btnEmpHome.setMaximumSize(new Dimension(180, 30));
        btnEmpHome.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        btnLeave.setMaximumSize(new Dimension(180, 30));
        btnLeave.setAlignmentX(Component.CENTER_ALIGNMENT);
        //for employees button
        
        
        // Card Panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        if (role.equalsIgnoreCase("Chief Executive Officer") ||
            role.equalsIgnoreCase("Chief Operating Officer")  ||  
            role.equalsIgnoreCase("Chief Finance Officer")    ||
            role.equalsIgnoreCase("Chief Marketing Officer")||
            role.equalsIgnoreCase("IT Operations and Systems")  ||      
            role.equalsIgnoreCase("HR Manager")      
        ){
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
            cardPanel.add(homePanel, "Home");
            
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
            
        }
        else {
            fullEmpPanel.setEmployeeNo(username);
            navPanel.add(Box.createVerticalStrut(20));
            navPanel.add(welcome);
            navPanel.add(Box.createVerticalStrut(20));
            navPanel.add(btnEmpHome);
            navPanel.add(Box.createVerticalStrut(20));
            navPanel.add(btnLeave);
            navPanel.add(Box.createVerticalStrut(50));
            navPanel.add(btnLogout);
            cardPanel.add(fullEmpPanel, "Home");
            
            btnEmpHome.addActionListener(e-> {
                cardPanel.removeAll();
                cardPanel.add(fullEmpPanel);
                cardPanel.revalidate();
                cardPanel.repaint();
            });
            
            btnLeave.addActionListener(e->{
            
                cardPanel.removeAll();
                cardPanel.add(leaveapp);
                cardPanel.revalidate();
                cardPanel.repaint();
                    
            });
        }

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
        btnLogout.addActionListener(e -> {
            new LoginPanel();
            dispose();
        });

        
        add(navPanel, BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}
