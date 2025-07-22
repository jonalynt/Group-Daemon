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
import java.io.FileReader;
import java.io.IOException;

import motorph_application.utils.Constants;

import motorph_application.utils.Constants;

import motorph_application.utils.CSVHandler;

import javax.swing.JOptionPane;

import motorph_application.utils.CSVHandler;
import motorph_application.utils.EmployeeData;
import motorph_application.utils.UIUtils;

public class AddEmployeePanel extends JPanel {
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
    private JTextField riceSubsidy = UIUtils.createTextField(true);
    private JTextField phoneAllowance = UIUtils.createTextField(true);
    private JTextField clothingAllowance = UIUtils.createTextField(true);
    private JTextField grossRate = UIUtils.createTextField(true);
    private JTextField hourlyRate = UIUtils.createTextField(true);

    private JPanel bottomPanel = new JPanel(new BorderLayout());
    private JPanel buttons = new JPanel();

    public AddEmployeePanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, getHeight()));

        JLabel addHeading = UIUtils.createHeaderLabel("Add Employee");
        add(addHeading, BorderLayout.NORTH);

        JButton btnAddEmployee = UIUtils.createButton("Add New Employee", new Color(0, 180, 0), Color.WHITE);
        JButton btnClear = UIUtils.createButton("Clear", null, null);

        buttons.add(btnAddEmployee);
        buttons.add(btnClear);

        JPanel formPanel = new JPanel(new GridLayout(3, 1, 5, 2));
        formPanel.add(UIUtils.createEmployeeInfoPanel(empNo, lastName, firstName, status, position, supervisor));
        formPanel.add(UIUtils.createPersonalInfoPanel(birthday, address, phone, sss, philhealth, tin, pagibig));
        formPanel.add(UIUtils.createFinancialInfoPanel(basicSalary, riceSubsidy, phoneAllowance, clothingAllowance, grossRate, hourlyRate));

        bottomPanel.add(formPanel, BorderLayout.CENTER);
        add(buttons, BorderLayout.WEST);
        add(bottomPanel, BorderLayout.SOUTH);

        setNextEmployeeNumber();

        btnAddEmployee.addActionListener(e -> addEmployeeToCSV());
        btnClear.addActionListener(e -> clearFields());
    }

    private void clearFields() {
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
        riceSubsidy.setText("");
        phoneAllowance.setText("");
        clothingAllowance.setText("");
        grossRate.setText("");
        hourlyRate.setText("");
    }

    private void setNextEmployeeNumber() {
        empNo.setText(String.valueOf(getLastEmployeeNumber() + 1));
    }

    private int getLastEmployeeNumber() {
        int maxEmpNo = 10000;
        try (BufferedReader br = new BufferedReader(new FileReader(Constants.EMPLOYEE_DATA_CSV))) {
            br.readLine(); // Skip header
            String line;
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

    private void addEmployeeToCSV() {
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
        data.riceSubsidy = riceSubsidy.getText().isEmpty() ? 0 : Double.parseDouble(riceSubsidy.getText().trim());
        data.phoneAllowance = phoneAllowance.getText().isEmpty() ? 0 : Double.parseDouble(phoneAllowance.getText().trim());
        data.clothingAllowance = clothingAllowance.getText().isEmpty() ? 0 : Double.parseDouble(clothingAllowance.getText().trim());
        data.grossRate = grossRate.getText().isEmpty() ? 0 : Double.parseDouble(grossRate.getText().trim());
        data.hourlyRate = hourlyRate.getText().isEmpty() ? 0 : Double.parseDouble(hourlyRate.getText().trim());

        if (data.lastName.isEmpty() || data.firstName.isEmpty() || data.status.isEmpty() || data.position.isEmpty() ||
            data.supervisor.isEmpty() || data.birthday.isEmpty() || data.address.isEmpty() || data.phone.isEmpty() ||
            data.sss.isEmpty() || data.philhealth.isEmpty() || data.tin.isEmpty() || data.pagibig.isEmpty() ||
            basicSalary.getText().isEmpty() || riceSubsidy.getText().isEmpty() || phoneAllowance.getText().isEmpty() ||
            clothingAllowance.getText().isEmpty() || grossRate.getText().isEmpty() || hourlyRate.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Missing Info", JOptionPane.WARNING_MESSAGE);
            return;
        }

        CSVHandler.appendToCSV(Constants.EMPLOYEE_DATA_CSV, data.toCSVArray());
        JOptionPane.showMessageDialog(this, "New employee added.");
        clearFields();
        setNextEmployeeNumber();
    }
}