import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AddEmployeePanel extends JPanel {
    
    
    
    JPanel bottomPanel = new JPanel(new BorderLayout());
    JPanel addemployee = new JPanel();
    JPanel buttons = new JPanel();
    
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
    
    
    // Form section
    JPanel formPanel = new JPanel(new GridLayout(3, 1, 5, 2));
    JPanel empInfo = new JPanel(new GridLayout(3, 2, 5, 5));
    JPanel personalInfo = new JPanel(new GridLayout(7, 2, 5, 5));
    JPanel financialInfo = new JPanel(new GridLayout(6, 2, 5, 5));
    
    JButton btnAddemployee = new JButton("Add New Employee");  
    JButton btnClear = new JButton("Clear");
    
    public AddEmployeePanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, getHeight()));
        
        JLabel addheading = new JLabel("Add Employee");
        addheading.setFont(new Font("Arial", Font.BOLD, 20));
        addheading.setAlignmentX(Component.LEFT_ALIGNMENT);
        addheading.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 0));
        add(addheading, BorderLayout.NORTH);

        btnAddemployee.setBackground(new Color(0, 180, 0));
        btnAddemployee.setForeground(Color.WHITE);
        
        buttons.add(btnAddemployee);
        buttons.add(btnClear);
        
        // Emlpoyee info panel  
        empInfo.setBorder(BorderFactory.createTitledBorder("Employee Information"));
        empInfo.add(new JLabel("EmployeeNo:"));
        empNo.setEditable(false);
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

        bottomPanel.add(formPanel, BorderLayout.CENTER);
        //bottomPanel.add(buttons, BorderLayout.EAST);
        add(buttons,BorderLayout.WEST);
        add(bottomPanel, BorderLayout.SOUTH);
        
        setNextEmployeeNumber();
        
        btnAddemployee.addActionListener(e -> addemployeeoncsv());
        btnClear.addActionListener(e-> clearfields());
        
    }
        
    private void clearfields(){
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

         clearfields();
         setNextEmployeeNumber();
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
    
}

