/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph_application.ui;

import java.awt.*;
import java.io.File;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import motorph_application.utils.EmployeeData;
import motorph_application.utils.UIUtils;

public class DashboardPanel extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private String userRole;
    private String userEmpNo;
    private String userLoggedIn;
    private String userFirstname;
    private String userLastname;
    private String selectedEmpNo;

    private employeeDashboard homePanel = new employeeDashboard();
    private AddEmployeePanel addEmpPanel = new AddEmployeePanel();
    private FullDetailsPanel fullEmpPanel = new FullDetailsPanel();
    private LeavePanel leaveApp = new LeavePanel();
    private TimePanel timeEmpPanel = new TimePanel();

    public DashboardPanel(String employeenum, String accesslevel, String loginnum, String lastname, String firstname) {
        userRole = accesslevel;
        userEmpNo = employeenum;
        userLoggedIn = loginnum;
        userFirstname = firstname;
        userLastname = lastname;

        setTitle("MotorPH Admin Dashboard");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Navigation panel
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

        JButton btnDatabase = UIUtils.createNavButton("Employee Database", Color.white, Color.black);
        JButton btnAdd = UIUtils.createNavButton("Add Employee", Color.white, Color.black);
        JButton btnFullDetails = UIUtils.createNavButton("View Full Details", Color.white, Color.black);
        JButton btnTime = UIUtils.createNavButton("Time", Color.white, Color.black);
        JButton btnLeave = UIUtils.createNavButton("Leave Application", Color.white, Color.black);
        JButton btnEmpHome = UIUtils.createNavButton("Home", Color.white, Color.black);
        JButton btnLogout = UIUtils.createNavButton("Log out", Color.white, Color.black);

        // Card Panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        if (userRole.equalsIgnoreCase("Admin")) {
            navPanel.add(Box.createVerticalStrut(20));
            navPanel.add(welcome);
            navPanel.add(Box.createVerticalStrut(5));
            navPanel.add(admin);
            navPanel.add(Box.createVerticalStrut(20));
            navPanel.add(btnDatabase);
            navPanel.add(Box.createVerticalStrut(10));
            navPanel.add(btnFullDetails);
            navPanel.add(Box.createVerticalStrut(10));
            navPanel.add(btnAdd);
            navPanel.add(Box.createVerticalStrut(10));
            navPanel.add(btnTime);
            navPanel.add(Box.createVerticalStrut(50));
            navPanel.add(btnLogout);
            cardPanel.add(homePanel, "Home");

            btnFullDetails.addActionListener(e -> {
                selectedEmpNo = homePanel.getSelectedEmployeeNo();
                fullEmpPanel.setEmployeeNo(selectedEmpNo);
                if (selectedEmpNo == null || selectedEmpNo.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please select an employee.", "No Selection", JOptionPane.WARNING_MESSAGE);
                } else {
                    switchPanel(fullEmpPanel);
                }
            });
        } else {
            fullEmpPanel.setEmployeeNo(employeenum);
            navPanel.add(Box.createVerticalStrut(20));
            navPanel.add(welcome);
            navPanel.add(Box.createVerticalStrut(20));
            navPanel.add(btnEmpHome);
            navPanel.add(Box.createVerticalStrut(20));
            navPanel.add(btnTime);
            navPanel.add(Box.createVerticalStrut(20));
            navPanel.add(btnLeave);
            navPanel.add(Box.createVerticalStrut(50));
            navPanel.add(btnLogout);
            cardPanel.add(fullEmpPanel, "Home");

            btnEmpHome.addActionListener(e -> switchPanel(fullEmpPanel));
            btnLeave.addActionListener(e -> switchPanel(leaveApp));
        }

        btnDatabase.addActionListener(e -> {
            selectedEmpNo = null;
            homePanel.reloadCSV();
            homePanel.clearFields();
            switchPanel(homePanel);
        });

        btnAdd.addActionListener(e -> switchPanel(addEmpPanel));

        btnTime.addActionListener(e -> {
            timeEmpPanel.setLoggedIn(userLoggedIn, userLastname, userFirstname);
            switchPanel(timeEmpPanel);
        });

        btnLogout.addActionListener(e -> {
            new LoginPanel();
            dispose();
        });

        add(navPanel, BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void switchPanel(JPanel panel) {
        cardPanel.removeAll();
        cardPanel.add(panel);
        cardPanel.revalidate();
        cardPanel.repaint();
    }
}
