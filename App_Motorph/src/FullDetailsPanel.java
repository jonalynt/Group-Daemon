import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class FullDetailsPanel extends JPanel {
    
    private String employeeNum;
    private double hourlyRate;
    private double ricesubsidy;
    private double phoneallowance;
    private double clothingallowance;
    private double benefits;
    
    //time sheet table
    private JTable attendancetable;
    private DefaultTableModel attendancemodel;
    private JLabel totalsalary;
    private JComboBox<String> comboMonth;

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("H:mm");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    
    private String csvPath = "src\\MotorPH_AttendanceRecord.csv"; // Path to your attendance CSV
    
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
    
    // Form section
    JPanel formPanel = new JPanel(new GridLayout(3, 1, 5, 2));
    JPanel empInfo = new JPanel(new GridLayout(3, 2, 5, 5));
    JPanel personalInfo = new JPanel(new GridLayout(7, 2, 5, 5));
    JPanel financialInfo = new JPanel(new GridLayout(6, 2, 5, 5));
    JPanel tablepanel = new JPanel();
    JPanel centerContainer = new JPanel(new BorderLayout());
    JPanel buttons = new JPanel(new GridLayout(4, 1, 20, 20));
    
    //buttons
    JButton btncalculate = new JButton("Calculate Salary");
    JButton updateBtn = new JButton("Update Employee");
    JButton deleteBtn = new JButton("Delete Employee");
    JButton setpasswordBtn = new JButton("Set Password");
    
    public FullDetailsPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, getHeight()));

        //MotorPH_AttendanceRecord.csv
        attendancemodel = new DefaultTableModel(new String[]{"Date", "Log In", "Log Out", "Hours Worked", "Daily Salary"}, 0);
        attendancetable = new JTable(attendancemodel);
        
        JScrollPane tableScroll = new JScrollPane(attendancetable);
        tableScroll.setPreferredSize(new Dimension(950, 150));
        tablepanel.add(tableScroll);
        
        totalsalary = new JLabel("Total Salary: ₱0.00");
        totalsalary.setFont(new Font("Arial", Font.BOLD, 16));
        
        comboMonth = new JComboBox<>();
        comboMonth.addItem("All");
        comboMonth.addActionListener(e -> reloadTable());
        
        JPanel topPanel = new JPanel(new GridLayout(1, 1, 5, 5));
        topPanel.add(new JLabel("Filter by Month:"));
        topPanel.add(comboMonth);
        topPanel.add(totalsalary);  
        
        add(topPanel, BorderLayout.NORTH);
        
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

        updateBtn.setBackground(new Color(0, 180, 0));
        updateBtn.setMaximumSize(new Dimension(100, 30));
        updateBtn.setForeground(Color.WHITE);
        updateBtn.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10)); // top, left, bottom, right
        deleteBtn.setBackground(new Color(168, 0, 0)); 
        deleteBtn.setMaximumSize(new Dimension(100, 30));
        deleteBtn.setForeground(Color.WHITE);
        btncalculate.setMaximumSize(new Dimension(100, 30));
        btncalculate.setBackground(new Color(52, 58, 235));
        btncalculate.setForeground(Color.WHITE);
        setpasswordBtn.setMaximumSize(new Dimension(100, 30));
        setpasswordBtn.setBackground(new Color(235, 122, 52));
        setpasswordBtn.setForeground(Color.WHITE);
        
        //actionlistener for buttons
        updateBtn.addActionListener(e -> updateemployee());
        deleteBtn.addActionListener(e -> deleteSelectedRow());
        
        buttons.add(btncalculate);
        buttons.add(setpasswordBtn);
        buttons.add(updateBtn);
        buttons.add(deleteBtn);     

        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(buttons, BorderLayout.EAST);
        
        centerContainer.add(tablepanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        add(centerContainer, BorderLayout.CENTER);
        
        loadAvailableMonths();
        reloadTable();
    }
    
    public void setEmployeeNo(String employeeNo) {
        this.employeeNum = employeeNo;    
        searchEmployee();
        loadAvailableMonths();
        reloadTable();
    }

    public void searchEmployee() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\MotorPH_EmployeeData.csv"))) {
            String line;
            boolean skipHeader = true;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                String[] parts = line.split(",", -1);
                if (parts.length >= 18 && parts[0].trim().equals(employeeNum)) {
                    empNo.setText(parts[0].trim());
                    emplastname.setText(parts[1].trim());
                    empfirstname.setText(parts[2].trim());
                    empposition.setText(parts[3].trim());
                    empstatus.setText(parts[10].trim());
                    empposition.setText(parts[11].trim());
                    empsupervisor.setText(parts[12].trim());
                    personalinfobday.setText(parts[3].trim());
                    personalinfoaddress.setText(parts[4].trim());
                    personalinfophone.setText(parts[5].trim());
                    personalinfosss.setText(parts[6].trim());
                    
                    //converting exponent philhealth
                    String philhealthraw = parts[7].trim();
                    BigDecimal philhealthnum = new BigDecimal(philhealthraw);
                    String philhealth = philhealthnum.toPlainString();
                    personalinfophilhealth.setText(philhealth);
                    personalinfotin.setText(parts[8].trim());
                    
                    String pagibigraw = parts[9].trim();
                    BigDecimal pagibignum = new BigDecimal(pagibigraw);
                    String pagibig = pagibignum.toPlainString();
                    personalinfopagibig.setText(pagibig);
                    
                    fininfobasicsalary.setText(parts[13].trim());
                    fininforicesubsidy.setText(parts[14].trim());
                    fininfophoneallowance.setText(parts[15].trim());
                    fininfoclothingallowance.setText(parts[16].trim());
                    fininfogrossrate.setText(parts[17].trim());
                    fininfohourlyrate.setText(parts[18].trim());
  
                    hourlyRate = Double.parseDouble(fininfohourlyrate.getText());
                    ricesubsidy = Double.parseDouble(fininforicesubsidy.getText());
                    phoneallowance = Double.parseDouble(fininfophoneallowance.getText());
                    clothingallowance = Double.parseDouble(fininfoclothingallowance.getText());
     
                    found = true;
                    break;
                }
            }

            if (!found) {
                //JOptionPane.showMessageDialog(this, "Employee not found.");
                //clearFields();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error reading file: " + ex.getMessage());
        }
    }
    
    //for attendance table
    private void loadAvailableMonths() {
        comboMonth.removeAllItems();
        comboMonth.addItem("All");

        Set<String> months = new LinkedHashSet<>();
        try (Scanner scanner = new Scanner(new File(csvPath), "UTF-8")) {
            boolean skipHeader = true;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                String[] parts = line.split(",", -1);
                if (parts.length >= 6 && parts[0].trim().equals(employeeNum)) {
                    try {
                        LocalDate date = LocalDate.parse(parts[3].trim(), DATE_FORMAT);
                        String monthYear = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + date.getYear();
                        months.add(monthYear);
                    } catch (Exception e) {
                        // skip invalid dates
                    }
                }
            }

            for (String month : months) {
                comboMonth.addItem(month);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading CSV:\n" + e.getMessage());
        }
    }

    private void reloadTable() {
        attendancemodel.setRowCount(0);
        double totalSalary = 0.0;
        double hoursWorked = 0.0;
        double dailySalary = 0.0;
        
        String selectedMonth = (String) comboMonth.getSelectedItem();

        try (Scanner scanner = new Scanner(new File(csvPath), "UTF-8")) {
            boolean skipHeader = true;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                String[] parts = line.split(",", -1);
                if (parts.length >= 6 && parts[0].trim().equals(employeeNum)) {
                    try {
                        LocalDate date = LocalDate.parse(parts[3].trim(), DATE_FORMAT);
                        String monthYear = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + date.getYear();

                        if (!"All".equals(selectedMonth) && !monthYear.equals(selectedMonth)) continue;

                        LocalTime logIn = LocalTime.parse(parts[4].trim(), TIME_FORMAT);
                        LocalTime logOut = LocalTime.parse(parts[5].trim(), TIME_FORMAT);
                        hoursWorked = Duration.between(logIn, logOut).toMinutes() / 60.0;
                        dailySalary = hoursWorked * hourlyRate;
                        benefits = ricesubsidy + phoneallowance + clothingallowance;
                        totalSalary += dailySalary;
                     
                        attendancemodel.addRow(new Object[]{
                            parts[3].trim(), parts[4].trim(), parts[5].trim(),
                            String.format("%.2f", hoursWorked),
                            String.format("₱%.2f", dailySalary)
                        });

                    } catch (Exception e) {
                        // skip malformed entries
                    }
                }
                
            }
            totalSalary += benefits;
            System.out.println("Hours Worked:" + hoursWorked);
            System.out.println("Daily Salary:" + hoursWorked + " x " + hourlyRate + " = " + dailySalary);
            System.out.println("Benefits Total:" + ricesubsidy + phoneallowance + clothingallowance + " = " + benefits);
            System.out.println("TOTAL SALARY:" + totalSalary);
            totalsalary.setText("Total Salary: ₱" + String.format("%.2f", totalSalary));

        } catch (IOException e) { 
            JOptionPane.showMessageDialog(this, "Error reading CSV:\n" + e.getMessage());
        }
    }
    
     private void updateemployee(){

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

     int confirm = JOptionPane.showConfirmDialog(null, "Confirm updated details?", "Confirm Update", JOptionPane.YES_NO_OPTION);
     if (confirm == JOptionPane.YES_OPTION) {
        
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
    JOptionPane.showMessageDialog(this, "Employee details updated.");
    }
     else{
         
     }
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
         
    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
        deleteFromCSV("src\\MotorPH_EmployeeData.csv", employeeNum);
        JOptionPane.showMessageDialog(this, "Employee deleted.");
        clearfields();
        }
        else{
        JOptionPane.showMessageDialog(this, "Please select a row to delete.");
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
    
}