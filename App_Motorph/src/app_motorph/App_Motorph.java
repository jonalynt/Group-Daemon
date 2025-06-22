/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app_motorph;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.imageio.ImageIO;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

public class App_Motorph {
        public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MotorPHLogin().setVisible(true));
    }
}
class MotorPHLogin extends JFrame {

    private JTextField empField;
    private JPasswordField passField;
    
    public MotorPHLogin() {
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

        JLabel empLabel = new JLabel("Employee No. :");
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

        String empNo = empField.getText().trim();
        String password = new String(passField.getPassword()).trim();

            if (authenticate(empNo,password)) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                dispose(); // Close login window
                new EmployeeAdminDashboard(); // Open table window
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
 
        });
        setVisible(true);

        // Add panels to frame
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        // Set size of left panel
        leftPanel.setPreferredSize(new Dimension(400, getHeight()));
    }
    
        // Method to check CSV for credentials
    private boolean authenticate(String empNo, String password) {
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
            }

    }

class EmployeeAdminDashboard extends JFrame {
    
    private JTable table;
    private DefaultTableModel model;
   
    //panels
    JPanel buttons = new JPanel();
    JPanel mainPanel = new JPanel();
    JPanel addemployeepanel = new JPanel();
    JPanel bottomPanel = new JPanel(new BorderLayout());
    
    //employee information
    static JTextField empNo = new JTextField();
    static JTextField emplastname = new JTextField();
    static JTextField empfirstname = new JTextField();
    static JTextField empstatus = new JTextField();
    static JTextField empposition = new JTextField();
    static JTextField empsupervisor = new JTextField();

    //personal employee info
    static JTextField personalinfobday = new JTextField();
    static JTextField personalinfoaddress = new JTextField();
    static JTextField personalinfophone = new JTextField();
    static JTextField personalinfosss = new JTextField();
    static JTextField personalinfophilhealth = new JTextField();
    static JTextField personalinfotin = new JTextField();
    static JTextField personalinfopagibig = new JTextField();
                                                    
    //financial info
    static JTextField fininfobasicsalary = new JTextField();
    static JTextField fininforicesubsidy = new JTextField();
    static JTextField fininfophoneallowance = new JTextField();
    static JTextField fininfoclothingallowance = new JTextField();
    static JTextField fininfogrossrate = new JTextField();
    static JTextField fininfohourlyrate = new JTextField();
    
    //employee information
    String txtempNo;
    String txtlastname;
    String txtfirstname;
    String txtstatus;
    String txtposition;
    String txtsupervisor;

    //personal employee info
    String txtbday;
    String txtaddress;
    String txtphone;
    String txtsss;
    String txtphilhealth;
    String txttin;
    String txtpagibig;
                                                    
    //financial info
    String txtsalary;
    String txtricesubsidy;
    String txtallowance;
    String txtclothing;
    String txtgrossrate;
    String txthourlyrate;
    
    //buttons
    JButton updateBtn = new JButton("Update Employee");
    JButton deleteBtn = new JButton("Delete Employee");
    JButton btnAddemployee = new JButton("Add New Employee");   
    JButton btnclear = new JButton("Clear");
              
    public EmployeeAdminDashboard() {
        setTitle("MotorPH Admin Dashboard");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Left navigation panel
        JPanel navPanel = new JPanel();
        navPanel.setBackground(new Color(128, 0, 0));
        navPanel.setPreferredSize(new Dimension(150, getHeight()));
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));

        JLabel welcome = new JLabel("Welcome to MOTORPH");
        JLabel admin = new JLabel("Admin Dashboard");
        welcome.setHorizontalAlignment(SwingConstants.LEFT);
        admin.setHorizontalAlignment(SwingConstants.LEFT);
        welcome.setForeground(Color.WHITE);
        admin.setForeground(Color.WHITE);

        navPanel.add(welcome,BorderLayout.CENTER);
        navPanel.add(admin,BorderLayout.CENTER);

        JButton btnDatabase = new JButton("Employee Database");
        JButton btnAdd = new JButton("Add Employee");
        JButton btnLogout = new JButton("Log out");

        for (JButton b : new JButton[]{btnDatabase, btnAdd, btnLogout}) {
            b.setMaximumSize(new Dimension(180, 30));
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            navPanel.add(Box.createVerticalStrut(10));
            navPanel.add(b);
        }

        btnDatabase.addActionListener(e->employeedatabase());
        btnAdd.addActionListener(e -> addemployee());
        btnLogout.addActionListener(e -> logout());
        
        add(navPanel, BorderLayout.WEST);

        // Main content
        
        mainPanel.setLayout(new BorderLayout());
        addemployeepanel.setLayout(new BorderLayout());

        JLabel heading = new JLabel("Employee Database");
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(heading, BorderLayout.NORTH);

        //add employee panel
        JLabel addheading = new JLabel("Add Employee");
        btnAddemployee.setBackground(new Color(0, 180, 0));
        btnAddemployee.setForeground(Color.WHITE);
        addheading.setFont(new Font("Arial", Font.BOLD, 20));
        addheading.setAlignmentX(Component.LEFT_ALIGNMENT);
        addheading.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 0));
        
        addemployeepanel.add(addheading, BorderLayout.NORTH);
        
        // Table model
        String[] columnNames = {
                "EmployeeNo", "LastName", "FirstName", "Birthday", "Address", "Phone",
                "SSS", "Philhealth", "TIN", "Pagibig", "Status", "Position", "Supervisor",
                "BasicSalary", "RiceSubsidy", "PhoneAllowance", "ClothingAllowance",
                "GrossRate", "HourlyRate"
        };
        
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane tableScroll = new JScrollPane(table);
        mainPanel.add(tableScroll, BorderLayout.CENTER);

        // Form section
        JPanel formPanel = new JPanel(new GridLayout(3, 1, 5, 2));
        JPanel empInfo = new JPanel(new GridLayout(3, 2, 5, 5));
        JPanel personalInfo = new JPanel(new GridLayout(7, 2, 5, 5));
        JPanel financialInfo = new JPanel(new GridLayout(6, 2, 5, 5));
        
        String[] labels = columnNames;
        int index = 0;

        // Emlpoyee info panel  
        empInfo.setBorder(BorderFactory.createTitledBorder("Employee Information"));
        empInfo.add(new JLabel("EmployeeNo:"));
        empInfo.add(empNo);
        empInfo.add(new JLabel("LastName:"));    
        empInfo.add(emplastname);
        empInfo.add(new JLabel("FirstName:"));  
        empInfo.add(empfirstname);
        empInfo.add(new JLabel("Status:"));    
        empInfo.add(empstatus);
        empInfo.add(new JLabel("Position:"));
        empInfo.add(empposition);
        empInfo.add(new JLabel("Supervisor:"));
        empInfo.add(empsupervisor);
        
        // personal info panel
        personalInfo.setBorder(BorderFactory.createTitledBorder("Personal Information"));
        personalInfo.add(new JLabel("Birthday:"));
        personalInfo.add(personalinfobday);     
        personalInfo.add(new JLabel("Address:"));
        personalInfo.add(personalinfoaddress);       
        personalInfo.add(new JLabel("Phone:"));
        personalInfo.add(personalinfophone);
        personalInfo.add(new JLabel("SSS:"));
        personalInfo.add(personalinfosss);
        personalInfo.add(new JLabel("PhilHealth:"));
        personalInfo.add(personalinfophilhealth);
        personalInfo.add(new JLabel("TIN:"));
        personalInfo.add(personalinfotin);
        personalInfo.add(new JLabel("Pagibig:"));
        personalInfo.add(personalinfopagibig);
        
        // Financial Info
        financialInfo.setBorder(BorderFactory.createTitledBorder("Financial Information"));
        financialInfo.add(new JLabel("Basic Salary:"));
        financialInfo.add(fininfobasicsalary);
        financialInfo.add(new JLabel("Rice Subsidy:"));
        financialInfo.add(fininforicesubsidy);
        financialInfo.add(new JLabel("Phone Allowance:"));
        financialInfo.add(fininfophoneallowance); 
        financialInfo.add(new JLabel("Clothing Allowance:"));
        financialInfo.add(fininfoclothingallowance);
        financialInfo.add(new JLabel("Gross Rate:"));
        financialInfo.add(fininfogrossrate);
        financialInfo.add(new JLabel("Hourly Rate:"));
        financialInfo.add(fininfohourlyrate);

        formPanel.add(empInfo);
        formPanel.add(personalInfo);
        formPanel.add(financialInfo);

        updateBtn.setBackground(new Color(0, 180, 0));
        updateBtn.setForeground(Color.WHITE);
        deleteBtn.setForeground(Color.RED);
        
   
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
        buttons.add(Box.createVerticalStrut(20));
        buttons.add(updateBtn);
        buttons.add(Box.createVerticalStrut(10));
        buttons.add(deleteBtn);
        buttons.add(Box.createVerticalStrut(10));
        buttons.add(btnAddemployee);
        buttons.add(Box.createVerticalStrut(10));
        buttons.add(btnclear);
        
        //actionlistener for buttons
        updateBtn.addActionListener(e -> updateemployee());
        deleteBtn.addActionListener(e -> deleteSelectedRow());
        btnclear.addActionListener(e -> {
            emplastname.setText("");
            empfirstname.setText("");
            empstatus.setText("");
            empposition.setText("");
            empsupervisor.setText("");
            personalinfobday.setText("");
            personalinfoaddress.setText("");
            personalinfophone.setText("");
            personalinfosss.setText("");
            personalinfophilhealth.setText("");
            personalinfotin.setText("");
            personalinfopagibig.setText("");
            fininfobasicsalary.setText("");
            fininforicesubsidy.setText("");
            fininfophoneallowance.setText("");
            fininfoclothingallowance.setText("");
            fininfogrossrate.setText("");
            fininfohourlyrate.setText("");
        });
        
        btnAddemployee.setVisible(false);
        
        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(buttons, BorderLayout.EAST);
        
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);

        // display data from table when clicked
        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                //employee info from click table
                empNo.setText(table.getValueAt(selectedRow, 0).toString());
                emplastname.setText(table.getValueAt(selectedRow, 1).toString());
                empfirstname.setText(table.getValueAt(selectedRow, 2).toString());
                empstatus.setText(table.getValueAt(selectedRow, 10).toString());
                empposition.setText(table.getValueAt(selectedRow, 11).toString());        
                empsupervisor.setText(table.getValueAt(selectedRow, 12).toString());        
                        
                //personal info from click table
                personalinfobday.setText(table.getValueAt(selectedRow, 3).toString());  
                personalinfoaddress.setText(table.getValueAt(selectedRow, 4).toString());
                personalinfophone.setText(table.getValueAt(selectedRow, 5).toString());        
                personalinfosss.setText(table.getValueAt(selectedRow, 6).toString());        
                personalinfophilhealth.setText(table.getValueAt(selectedRow, 7).toString());        
                personalinfotin.setText(table.getValueAt(selectedRow, 8).toString());        
                personalinfopagibig.setText(table.getValueAt(selectedRow, 9).toString());        
                        
                //financial info form click  
                fininfobasicsalary.setText(table.getValueAt(selectedRow, 13).toString());       
                fininforicesubsidy.setText(table.getValueAt(selectedRow, 14).toString());        
                fininfophoneallowance.setText(table.getValueAt(selectedRow, 15).toString());        
                fininfoclothingallowance.setText(table.getValueAt(selectedRow, 16).toString());        
                fininfogrossrate.setText(table.getValueAt(selectedRow, 17).toString());        
                fininfohourlyrate.setText(table.getValueAt(selectedRow, 18).toString());                              
            }
        });

        // Load CSV
        loadCSV("src\\MotorPH_EmployeeData.csv");
        add(mainPanel);
        setVisible(true);
    }

    //employee database button
    private void employeedatabase(){
         mainPanel.setVisible(true);
         mainPanel.add(bottomPanel, BorderLayout.SOUTH);
         add(mainPanel, BorderLayout.CENTER);
         addemployeepanel.setVisible(false);
         updateBtn.setVisible(true);
         deleteBtn.setVisible(true);
         btnAddemployee.setVisible(false);
         empNo.setText("");
    }
    
    //add employee button
    private void addemployee(){
        mainPanel.setVisible(false);        
        addemployeepanel.add(bottomPanel);
        addemployeepanel.setVisible(true);
        btnAddemployee.setVisible(true);
        updateBtn.setVisible(false);
        deleteBtn.setVisible(false);
        add(addemployeepanel);
        
        setNextEmployeeNumber();
        emplastname.setText("");
        empfirstname.setText("");
        empstatus.setText("");
        empposition.setText("");
        empsupervisor.setText("");
        personalinfobday.setText("");
        personalinfoaddress.setText("");
        personalinfophone.setText("");
        personalinfosss.setText("");
        personalinfophilhealth.setText("");
        personalinfotin.setText("");
        personalinfopagibig.setText("");
        fininfobasicsalary.setText("");
        fininforicesubsidy.setText("");
        fininfophoneallowance.setText("");
        fininfoclothingallowance.setText("");
        fininfogrossrate.setText("");
        fininfohourlyrate.setText("");
        
        btnAddemployee.addActionListener(e -> addemployeeoncsv());
    }
    
    //automatic get the new employee number
    private void setNextEmployeeNumber() {
        empNo.setText(String.valueOf(getLastEmployeeNumber() + 1));
        empNo.setEditable(false);
    }
    
    //getting the last employee number on sheet
    private int getLastEmployeeNumber() {
        int maxEmpNo = 10000;
        try (BufferedReader br = new BufferedReader(new FileReader("src\\MotorPH_EmployeeData.csv"))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    try {
                        int currentNo = Integer.parseInt(parts[0].replaceAll("[^\\d]", ""));
                        if (currentNo > maxEmpNo) {
                            maxEmpNo = currentNo;
                        }
                    } catch (NumberFormatException ignored) {}
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to read employee numbers from CSV.");
        }
        return maxEmpNo;
    }
   
    //add new employee
    private void addemployeeoncsv(){      
    
    //employee information
     txtempNo = empNo.getText().trim();
     txtlastname = emplastname.getText().trim();
     txtfirstname = empfirstname.getText().trim();
     txtstatus = empstatus.getText().trim();
     txtposition = empposition.getText().trim();
     txtsupervisor = empsupervisor.getText().trim();

    //personal employee info
     txtbday = personalinfobday.getText().trim();
     txtaddress = personalinfoaddress.getText().trim();
     txtphone = personalinfophone.getText().trim();
     txtsss = personalinfosss.getText().trim();
     txtphilhealth = personalinfophilhealth.getText().trim();
     txttin = personalinfotin.getText().trim();
     txtpagibig = personalinfopagibig.getText().trim();
                                                    
    //financial info
     txtsalary = fininfobasicsalary.getText().trim();
     txtricesubsidy = fininforicesubsidy.getText().trim();
     txtallowance = fininfophoneallowance.getText().trim();
     txtclothing = fininfoclothingallowance.getText().trim();
     txtgrossrate = fininfogrossrate.getText().trim();
     txthourlyrate = fininfohourlyrate.getText().trim();
        
        
     if (txtlastname.isEmpty() || txtfirstname.isEmpty() || txtstatus.isEmpty() || txtposition.isEmpty() || txtsupervisor.isEmpty() || txtbday.isEmpty() || txtaddress.isEmpty() || txtphone.isEmpty() || txtsss.isEmpty() ||
         txtphilhealth.isEmpty() ||      
         txttin.isEmpty() || 
         txtpagibig.isEmpty() ||      
         txtsalary.isEmpty() ||
         txtricesubsidy.isEmpty() ||      
         txtallowance.isEmpty() ||     
         txtclothing.isEmpty() ||     
         txtgrossrate.isEmpty() ||    
         txthourlyrate.isEmpty() 
             )
        {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Missing Info", JOptionPane.WARNING_MESSAGE);
                return;
            }
        /*
         for (int i = 0; i < model.getRowCount(); i++) {
                if (model.getValueAt(i, 0).toString().equals(txtempNo)) {
                    JOptionPane.showMessageDialog(this, "Employee number already exists.", "Duplicate Entry", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } */
         model.addRow(new String[]{
            txtempNo,
            txtlastname,
            txtfirstname,
            txtbday,
            txtaddress,
            txtphone,
            txtsss,
            txtphilhealth,
            txttin,
            txtpagibig,
            txtstatus,
            txtposition,
            txtsupervisor,
            txtsalary,
            txtricesubsidy,
            txtallowance,
            txtclothing,
            txtgrossrate,
            txthourlyrate
         });
         appendToCSV("src\\MotorPH_EmployeeData.csv",
            txtempNo,
            txtlastname,
            txtfirstname,
            txtbday,
            txtaddress,
            txtphone,
            txtsss,
            txtphilhealth,
            txttin,
            txtpagibig,
            txtstatus,
            txtposition,
            txtsupervisor,
            txtsalary,
            txtricesubsidy,
            txtallowance,
            txtclothing,
            txtgrossrate,
            txthourlyrate
         );
         JOptionPane.showMessageDialog(this, "New employee added.");

         empNo.setText("");
         emplastname.setText("");
         empfirstname.setText("");
         empstatus.setText("");
         empposition.setText("");
         empsupervisor.setText("");
         personalinfobday.setText("");
         personalinfoaddress.setText("");
         personalinfophone.setText("");
         personalinfosss.setText("");
         personalinfophilhealth.setText("");
         personalinfotin.setText("");
         personalinfopagibig.setText("");
         fininfobasicsalary.setText("");
         fininforicesubsidy.setText("");
         fininfophoneallowance.setText("");
         fininfoclothingallowance.setText("");
         fininfogrossrate.setText("");
         fininfohourlyrate.setText("");
    }
    
    //add to csv
    public void appendToCSV(String filePath, 
            String txtempNo,
            String txtlastname,
            String txtfirstname,
            String txtbday,
            String txtaddress,
            String txtphone,
            String txtsss,
            String txtphilhealth,
            String txttin,
            String txtpagibig,
            String txtstatus,
            String txtposition,
            String txtsupervisor,
            String txtsalary,
            String txtricesubsidy,
            String txtallowance,
            String txtclothing,
            String txtgrossrate,
            String txthourlyrate
            ) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.newLine();
            writer.write(
                    txtempNo + "," +
                    txtlastname + "," +
                    txtfirstname + "," +
                    txtbday + "," +
                    txtaddress + "," +
                    txtphone + "," +
                    txtsss + "," +
                    txtphilhealth + "," +
                    txttin + "," +
                    txtpagibig + "," +
                    txtstatus + "," +
                    txtposition + "," +
                    txtsupervisor + "," +
                    txtsalary + "," +
                    txtricesubsidy + "," +
                    txtallowance + "," +
                    txtclothing + "," +
                    txtgrossrate + "," +
                    txthourlyrate  
                            );
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to write to CSV.");
        }
    }
   
    //update employee
    private void updateemployee(){
    int selectedRow = table.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a row to update.", "No Selection", JOptionPane.WARNING_MESSAGE);
        return;
    }

     //employee information
     txtempNo = empNo.getText().trim();
     txtlastname = emplastname.getText().trim();
     txtfirstname = empfirstname.getText().trim();
     txtstatus = empstatus.getText().trim();
     txtposition = empposition.getText().trim();
     txtsupervisor = empsupervisor.getText().trim();

    //personal employee info
     txtbday = personalinfobday.getText().trim();
     txtaddress = personalinfoaddress.getText().trim();
     txtphone = personalinfophone.getText().trim();
     txtsss = personalinfosss.getText().trim();
     txtphilhealth = personalinfophilhealth.getText().trim();
     txttin = personalinfotin.getText().trim();
     txtpagibig = personalinfopagibig.getText().trim();
                                                    
    //financial info
     txtsalary = fininfobasicsalary.getText().trim();
     txtricesubsidy = fininforicesubsidy.getText().trim();
     txtallowance = fininfophoneallowance.getText().trim();
     txtclothing = fininfoclothingallowance.getText().trim();
     txtgrossrate = fininfogrossrate.getText().trim();
     txthourlyrate = fininfohourlyrate.getText().trim();

   if (txtlastname.isEmpty() || txtfirstname.isEmpty() || txtstatus.isEmpty() || txtposition.isEmpty() || txtsupervisor.isEmpty() || txtbday.isEmpty() || txtaddress.isEmpty() || txtphone.isEmpty() || txtsss.isEmpty() ||
         txtphilhealth.isEmpty() ||      
         txttin.isEmpty() || 
         txtpagibig.isEmpty() ||      
         txtsalary.isEmpty() ||
         txtricesubsidy.isEmpty() ||      
         txtallowance.isEmpty() ||     
         txtclothing.isEmpty() ||     
         txtgrossrate.isEmpty() ||    
         txthourlyrate.isEmpty() 
             )
        {
        JOptionPane.showMessageDialog(this, "Please fill in all fields when updating.", "Missing Data", JOptionPane.WARNING_MESSAGE);
        return;
    }

   //update jtable
    //employee details
    model.setValueAt(txtlastname, selectedRow, 1);
    model.setValueAt(txtfirstname, selectedRow, 2);       
    model.setValueAt(txtstatus, selectedRow, 10);
    model.setValueAt(txtposition,selectedRow, 11);        
    model.setValueAt(txtsupervisor,selectedRow, 12);        
                        
    //personal info 
    model.setValueAt(txtbday,selectedRow, 3);  
    model.setValueAt(txtaddress,selectedRow, 4);
    model.setValueAt(txtphone,selectedRow, 5);        
    model.setValueAt(txtsss,selectedRow, 6);        
    model.setValueAt(txtphilhealth,selectedRow, 7);        
    model.setValueAt(txttin,selectedRow, 8);        
    model.setValueAt(txtpagibig,selectedRow, 9);        
                        
                //financial info
    model.setValueAt(txtsalary,selectedRow, 13);       
    model.setValueAt(txtricesubsidy, selectedRow, 14);        
    model.setValueAt(txtallowance,selectedRow, 15);        
    model.setValueAt(txtclothing,selectedRow, 16);
    model.setValueAt(txtgrossrate, selectedRow, 17); 
    model.setValueAt(txthourlyrate, selectedRow, 18);
    

    // Update CSV
    updateCSV("src\\MotorPH_EmployeeData.csv",
            txtempNo,
            txtlastname,
            txtfirstname,
            txtbday,
            txtaddress,
            txtphone,
            txtsss,
            txtphilhealth,
            txttin,
            txtpagibig,
            txtstatus,
            txtposition,
            txtsupervisor,
            txtsalary,
            txtricesubsidy,
            txtallowance,
            txtclothing,
            txtgrossrate,
            txthourlyrate
         );

    JOptionPane.showMessageDialog(this, "Row updated.");
    }
    
    //updating csv
    private void updateCSV(
            String filePath, 
            String txtempNo,
            String txtlastname,
            String txtfirstname,
            String txtbday,
            String txtaddress,
            String txtphone,
            String txtsss,
            String txtphilhealth,
            String txttin,
            String txtpagibig,
            String txtstatus,
            String txtposition,
            String txtsupervisor,
            String txtsalary,
            String txtricesubsidy,
            String txtallowance,
            String txtclothing,
            String txtgrossrate,
            String txthourlyrate
            ){
         File inputFile = new File(filePath);
    File tempFile = new File("src\\temp.csv");

    try (
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
    ) {
        String line;
        boolean isHeader = true;

        while ((line = reader.readLine()) != null) {
            if (isHeader) {
                writer.write(line);
                writer.newLine();
                isHeader = false;
                continue;
            }

            String[] data = line.split(",");
            if (data.length > 0 && data[0].equals(txtempNo)) {
                writer.write(
                
                    txtempNo + "," +
                    txtlastname + "," +
                    txtfirstname + "," +
                    txtbday + "," +
                    txtaddress + "," +
                    txtphone + "," +
                    txtsss + "," +
                    txtphilhealth + "," +
                    txttin + "," +
                    txtpagibig + "," +
                    txtstatus + "," +
                    txtposition + "," +
                    txtsupervisor + "," +
                    txtsalary + "," +
                    txtricesubsidy + "," +
                    txtallowance + "," +
                    txtclothing + "," +
                    txtgrossrate + "," +
                    txthourlyrate  
                
                );  // write updated
            } else {
                writer.write(line);  // write original
            }
            writer.newLine();
        }

    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error updating CSV: " + e.getMessage());
        return;
    }

    if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
        JOptionPane.showMessageDialog(this, "Failed to update the CSV file.");
    }
        
        
    }
    
    //delete button action
    private void deleteSelectedRow(){
         
    int selectedRow = table.getSelectedRow();
    
    if (selectedRow != -1) {
        String empNoToDelete = model.getValueAt(selectedRow, 0).toString();
        model.removeRow(selectedRow);
        deleteFromCSV("src\\MotorPH_EmployeeData.csv", empNoToDelete);
        
        emplastname.setText("");
        empfirstname.setText("");
        empstatus.setText("");
        empposition.setText("");
        empsupervisor.setText("");
        personalinfobday.setText("");
        personalinfoaddress.setText("");
        personalinfophone.setText("");
        personalinfosss.setText("");
        personalinfophilhealth.setText("");
        personalinfotin.setText("");
        personalinfopagibig.setText("");
        fininfobasicsalary.setText("");
        fininforicesubsidy.setText("");
        fininfophoneallowance.setText("");
        fininfoclothingallowance.setText("");
        fininfogrossrate.setText("");
        fininfohourlyrate.setText("");

        JOptionPane.showMessageDialog(this, "Employee deleted.");
    } else {
        JOptionPane.showMessageDialog(this, "Please select a row to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
    }
    }  
    
    //delete from csv function
    private void deleteFromCSV(String filePath, String empNoToDelete) {
    File inputFile = new File(filePath);
    File tempFile = new File("src\\temp.csv");

    try (
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
    ) {
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            if (currentLine.startsWith(empNoToDelete + ",")) continue; // skip the row
            writer.write(currentLine);
            writer.newLine();
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error while deleting: " + e.getMessage());
        return;
    }

    if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
        JOptionPane.showMessageDialog(this, "Could not update CSV file after deletion.");
    }
}

    //logout button
    private void logout(){
        dispose();
        new MotorPHLogin();
}
    
    //load csv
    private void loadCSV(String filePath) {
        try (Scanner scanner = new Scanner(new File(filePath),"ISO-8859-1")) {
            String[] headers = scanner.nextLine().split(",");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();; // -1 to include empty
                if (!line.trim().isEmpty()) {
                String[] data = line.split(",", -1);
                model.addRow(data);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load CSV: " + e.getMessage());
        }
    }
}