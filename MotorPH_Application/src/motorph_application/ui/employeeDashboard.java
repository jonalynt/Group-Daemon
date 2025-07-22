/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph_application.ui;

/**
 *
 * @author NobbyDobbyCobby
 */
import motorph_application.utils.EmployeeData;
import motorph_application.utils.Constants;
import motorph_application.utils.CSVHandler;
import motorph_application.utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class employeeDashboard extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JPanel bottomPanel = new JPanel(new BorderLayout());
    private JPanel formPanel = new JPanel(new GridLayout(3, 1, 5, 2));

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

    public employeeDashboard() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, getHeight()));

        JLabel heading = UIUtils.createHeaderLabel("Employee Database");
        add(heading, BorderLayout.NORTH);

        // Employee table
        model = new DefaultTableModel(Constants.EMPLOYEE_COLUMNS, 0);
        table = new JTable(model);
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setPreferredSize(new Dimension(1000, 300));
        add(tableScroll, BorderLayout.CENTER);

        // Form panels
        formPanel.add(UIUtils.createEmployeeInfoPanel(empNo, lastName, firstName, status, position, supervisor));
        // Uncomment if personal and financial info panels are needed
        // formPanel.add(UIUtils.createPersonalInfoPanel(birthday, address, phone, sss, philhealth, tin, pagibig));
        // formPanel.add(UIUtils.createFinancialInfoPanel(basicSalary, riceSubsidy, phoneAllowance, clothingAllowance, grossRate, hourlyRate));

        bottomPanel.add(formPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Table selection listener
        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                empNo.setText(table.getValueAt(selectedRow, 0).toString());
                lastName.setText(table.getValueAt(selectedRow, 1).toString());
                firstName.setText(table.getValueAt(selectedRow, 2).toString());
                status.setText(table.getValueAt(selectedRow, 10).toString());
                position.setText(table.getValueAt(selectedRow, 11).toString());
                supervisor.setText(table.getValueAt(selectedRow, 12).toString());

            }
        });

        // Load CSV
        CSVHandler.loadEmployeeData(Constants.EMPLOYEE_DATA_CSV, model);
    }

    public String getSelectedEmployeeNo() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            return table.getValueAt(selectedRow, 0).toString();
        }
        return null;
    }

    public EmployeeData getFormData() {
        EmployeeData data = new EmployeeData();
        data.empNo = empNo.getText();
        data.lastName = lastName.getText();
        data.firstName = firstName.getText();
        data.status = status.getText();
        data.position = position.getText();
        data.supervisor = supervisor.getText();

        return data;
    }

    public void reloadCSV() {
        model.setRowCount(0);
        CSVHandler.loadEmployeeData(Constants.EMPLOYEE_DATA_CSV, model);
        clearFields();
    }

    public void clearFields() {
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
        riceSubsidy.setText("");
        phoneAllowance.setText("");
        clothingAllowance.setText("");
        grossRate.setText("");
        hourlyRate.setText("");
    }
}
