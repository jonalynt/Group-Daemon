/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph_application.ui;

/**
 *
 * @author NobbyDobbyCobby
 */
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
import motorph_application.utils.CSVHandler;
import motorph_application.utils.Constants;
import static motorph_application.utils.Constants.ATTENDANCE_CSV;
import static motorph_application.utils.Constants.EMPLOYEE_DATA_CSV;
import motorph_application.utils.EmployeeData;
import motorph_application.utils.UIUtils;

public class FullDetailsPanel extends JPanel {
    
    private String employeeNum;
    private double hourlyRate;
    private double riceSubsidy;
    private double phoneAllowance;
    private double clothingAllowance;
    private double benefits;

    private JTable attendanceTable;
    private DefaultTableModel attendanceModel;
    private JLabel totalSalary;
    private JComboBox<String> comboMonth;

    private JTextField empNo = UIUtils.createTextField(false);
    private JTextField lastName = UIUtils.createTextField(true);
    private JTextField firstName = UIUtils.createTextField(true);
    private JTextField status = UIUtils.createTextField(true);
    private JTextField position = UIUtils.createTextField(true);
    private JTextField supervisor = UIUtils.createTextField(true);
    private JTextField birthday = UIUtils.createTextField(true);
    private JTextField address = UIUtils.createTextField(true);
    private JTextField phone = UIUtils.createTextField(true);
    private JTextField sss = UIUtils.createTextField(true);
    private JTextField philhealth = UIUtils.createTextField(true);
    private JTextField tin = UIUtils.createTextField(true);
    private JTextField pagibig = UIUtils.createTextField(true);
    private JTextField basicSalary = UIUtils.createTextField(true);
    private JTextField riceSubsidyField = UIUtils.createTextField(true);
    private JTextField phoneAllowanceField = UIUtils.createTextField(true);
    private JTextField clothingAllowanceField = UIUtils.createTextField(true);
    private JTextField grossRate = UIUtils.createTextField(true);
    private JTextField hourlyRateField = UIUtils.createTextField(true);

    private JPanel bottomPanel = new JPanel(new BorderLayout());
    private JPanel tablePanel = new JPanel();
    private JPanel centerContainer = new JPanel(new BorderLayout());
    private JPanel buttons = new JPanel(new GridLayout(4, 1, 20, 20));
    
    public FullDetailsPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, getHeight()));

        // Attendance table
        attendanceModel = new DefaultTableModel(Constants.ATTENDANCE_COLUMNS, 0);
        attendanceTable = new JTable(attendanceModel);
        JScrollPane tableScroll = new JScrollPane(attendanceTable);
        tableScroll.setPreferredSize(new Dimension(950, 150));
        tablePanel.add(tableScroll);

        totalSalary = new JLabel("Total Salary: ₱0.00");
        totalSalary.setFont(new Font("Arial", Font.BOLD, 16));

        comboMonth = new JComboBox<>();
        comboMonth.addItem("All");
        comboMonth.addActionListener(e -> reloadTable());

        JPanel topPanel = new JPanel(new GridLayout(1, 1, 5, 5));
        topPanel.add(new JLabel("Filter by Month:"));
        topPanel.add(comboMonth);
        topPanel.add(totalSalary);
        add(topPanel, BorderLayout.NORTH);

        // Form panels
        JPanel formPanel = new JPanel(new GridLayout(3, 1, 5, 2));
        formPanel.add(UIUtils.createEmployeeInfoPanel(empNo, lastName, firstName, status, position, supervisor));
        formPanel.add(UIUtils.createPersonalInfoPanel(birthday, address, phone, sss, philhealth, tin, pagibig));
        formPanel.add(UIUtils.createFinancialInfoPanel(basicSalary, riceSubsidyField, phoneAllowanceField, clothingAllowanceField, grossRate, hourlyRateField));

        // Buttons
        JButton btnCalculate = UIUtils.createButton("Calculate Salary", new Color(52, 58, 235), Color.WHITE);
        JButton setPasswordBtn = UIUtils.createButton("Set Password", new Color(235, 122, 52), Color.WHITE);
        JButton updateBtn = UIUtils.createButton("Update Employee", new Color(0, 180, 0), Color.WHITE);
        JButton deleteBtn = UIUtils.createButton("Delete Employee", new Color(168, 0, 0), Color.WHITE);

        updateBtn.addActionListener(e -> updateEmployee());
        deleteBtn.addActionListener(e -> deleteSelectedRow());

        buttons.add(btnCalculate);
        buttons.add(setPasswordBtn);
        buttons.add(updateBtn);
        buttons.add(deleteBtn);

        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(buttons, BorderLayout.EAST);
        centerContainer.add(tablePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        add(centerContainer, BorderLayout.CENTER);
    }
    
    public void setEmployeeNo(String employeeNo) {
        this.employeeNum = employeeNo;    
        searchEmployee();
        loadAvailableMonths();
        reloadTable();
    }

    public void searchEmployee() {
        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.EMPLOYEE_DATA_CSV))) {
            String line;
            boolean skipHeader = true;

            while ((line = reader.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                String[] parts = line.split(",", -1);
                if (parts.length >= 19 && parts[0].trim().equals(employeeNum)) {
                    empNo.setText(parts[0].trim());
                    lastName.setText(parts[1].trim());
                    firstName.setText(parts[2].trim());
                    birthday.setText(parts[3].trim());
                    address.setText(parts[4].trim());
                    phone.setText(parts[5].trim());
                    sss.setText(parts[6].trim());
                    philhealth.setText(new BigDecimal(parts[7].trim()).toPlainString());
                    tin.setText(parts[8].trim());
                    pagibig.setText(new BigDecimal(parts[9].trim()).toPlainString());
                    status.setText(parts[10].trim());
                    position.setText(parts[11].trim());
                    supervisor.setText(parts[12].trim());
                    basicSalary.setText(parts[13].trim());
                    riceSubsidyField.setText(parts[14].trim());
                    phoneAllowanceField.setText(parts[15].trim());
                    clothingAllowanceField.setText(parts[16].trim());
                    grossRate.setText(parts[17].trim());
                    hourlyRateField.setText(parts[18].trim());

                    hourlyRate = Double.parseDouble(hourlyRateField.getText());
                    riceSubsidy = Double.parseDouble(riceSubsidyField.getText());
                    phoneAllowance = Double.parseDouble(phoneAllowanceField.getText());
                    clothingAllowance = Double.parseDouble(clothingAllowanceField.getText());
                    break;
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error reading file: " + ex.getMessage());
        }
    }
    
    //for attendance table
    
    private void loadAvailableMonths() {
        comboMonth.removeAllItems();
        comboMonth.addItem("All");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        CSVHandler.loadAvailableMonths(Constants.ATTENDANCE_CSV, employeeNum, dateFormat)
                .forEach(comboMonth::addItem);
    }

    private void reloadTable() {
        attendanceModel.setRowCount(0);
        double totalSalaryValue = 0.0;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern(Constants.TIME_FORMAT);
        String selectedMonth = (String) comboMonth.getSelectedItem();

        try (Scanner scanner = new Scanner(new File(Constants.ATTENDANCE_CSV), "UTF-8")) {
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
                        LocalDate date = LocalDate.parse(parts[3].trim(), dateFormat);
                        String monthYear = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + date.getYear();

                        if (!"All".equals(selectedMonth) && !monthYear.equals(selectedMonth)) continue;

                        LocalTime logIn = LocalTime.parse(parts[4].trim(), timeFormat);
                        LocalTime logOut = LocalTime.parse(parts[5].trim(), timeFormat);
                        double hoursWorked = Duration.between(logIn, logOut).toMinutes() / 60.0;
                        double dailySalary = hoursWorked * hourlyRate;
                        benefits = riceSubsidy + phoneAllowance + clothingAllowance;
                        totalSalaryValue += dailySalary;

                        attendanceModel.addRow(new Object[]{
                            parts[3].trim(), parts[4].trim(), parts[5].trim(),
                            String.format("%.2f", hoursWorked),
                            String.format("₱%.2f", dailySalary)
                        });
                    } catch (Exception e) {
                        // Skip malformed entries
                    }
                }
            }
            totalSalaryValue += benefits;
            totalSalary.setText("Total Salary: ₱" + String.format("%.2f", totalSalaryValue));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading CSV: " + e.getMessage());
        }
    }
    
    //delete button action
    private void deleteSelectedRow(){
         
    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
        deleteFromCSV(EMPLOYEE_DATA_CSV, employeeNum);
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
    
        private void updateEmployee() {
            EmployeeData data = new EmployeeData();
            data.empNo = empNo.getText().trim();
            data.lastName = lastName.getText().trim();
            data.firstName = firstName.getText().trim();
            data.birthday = birthday.getText().trim();
            data.address = address.getText().trim();
            data.phone = phone.getText().trim();
            data.sss = sss.getText().trim();
            data.philhealth = philhealth.getText().trim();
            data.tin = tin.getText().trim();
            data.pagibig = pagibig.getText().trim();
            data.status = status.getText().trim();
            data.position = position.getText().trim();
            data.supervisor = supervisor.getText().trim();
            data.basicSalary = basicSalary.getText().isEmpty() ? 0 : Double.parseDouble(basicSalary.getText().trim());
            data.riceSubsidy = riceSubsidyField.getText().isEmpty() ? 0 : Double.parseDouble(riceSubsidyField.getText().trim());
            data.phoneAllowance = phoneAllowanceField.getText().isEmpty() ? 0 : Double.parseDouble(phoneAllowanceField.getText().trim());
            data.clothingAllowance = clothingAllowanceField.getText().isEmpty() ? 0 : Double.parseDouble(clothingAllowanceField.getText().trim());
            data.grossRate = grossRate.getText().isEmpty() ? 0 : Double.parseDouble(grossRate.getText().trim());
            data.hourlyRate = hourlyRateField.getText().isEmpty() ? 0 : Double.parseDouble(hourlyRateField.getText().trim());

            int confirm = JOptionPane.showConfirmDialog(null, "Confirm updated details?", "Confirm Update", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (data.lastName.isEmpty() || data.firstName.isEmpty() || data.status.isEmpty() || data.position.isEmpty() ||
                    data.supervisor.isEmpty() || data.birthday.isEmpty() || data.address.isEmpty() || data.phone.isEmpty() ||
                    data.sss.isEmpty() || data.philhealth.isEmpty() || data.tin.isEmpty() || data.pagibig.isEmpty() ||
                    basicSalary.getText().isEmpty() || riceSubsidyField.getText().isEmpty() || phoneAllowanceField.getText().isEmpty() ||
                    clothingAllowanceField.getText().isEmpty() || grossRate.getText().isEmpty() || hourlyRateField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all fields when updating.", "Missing Data", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                CSVHandler.updateCSV(Constants.EMPLOYEE_DATA_CSV, data.empNo, data.toCSVArray());
                JOptionPane.showMessageDialog(this, "Employee details updated.");
            }
    }
    
    private void clearfields(){
        empNo.setText("");
        lastName.setText("");
        firstName.setText("");
        status.setText("");
        position.setText("");
        supervisor.setText("");
        birthday.setText("");
        address.setText("");
        phone.setText("");
        sss.setText("");
        philhealth.setText("");
        tin.setText("");
        pagibig.setText("");
        basicSalary.setText("");
        riceSubsidyField.setText("");
        phoneAllowanceField.setText("");
        clothingAllowanceField.setText("");
        grossRate.setText("");
        hourlyRateField.setText("");
    }
    
}
