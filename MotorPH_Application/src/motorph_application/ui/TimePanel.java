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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;
import javax.swing.table.DefaultTableModel;
import motorph_application.utils.CSVHandler;
import motorph_application.utils.Constants;
import motorph_application.utils.UIUtils;

public class TimePanel extends JPanel {
    private String loggedInEmployee;
    private String loggedInLastName;
    private String loggedInFirstName;
    private JTable loggedInAttendanceTable;
    private DefaultTableModel loggedInAttendanceModel;
    private JComboBox<String> loggedInComboMonth;
    private JPanel loggedInTablePanel = new JPanel();
    private JPanel loggedInCenterContainer = new JPanel(new BorderLayout());
    private JPanel loggedInTopPanel = new JPanel(new GridLayout(1, 1, 5, 5));
    private JButton btnTimeIn = UIUtils.createButton("Check In", null, null);
    private JButton btnTimeOut = UIUtils.createButton("Check Out", null, null);
    private LocalDate date = LocalDate.now();
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
    private String formattedDate = date.format(dateFormatter);

    public TimePanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, getHeight()));

        JLabel timeHeading = UIUtils.createHeaderLabel("Time");
        loggedInTopPanel.add(timeHeading, BorderLayout.NORTH);
        loggedInTopPanel.add(btnTimeIn);
        add(loggedInTopPanel, BorderLayout.NORTH);

        loggedInAttendanceModel = new DefaultTableModel(new String[]{"Date", "Log In", "Log Out"}, 0);
        loggedInAttendanceTable = new JTable(loggedInAttendanceModel);
        JScrollPane loggedInTableScroll = new JScrollPane(loggedInAttendanceTable);
        loggedInTableScroll.setPreferredSize(new Dimension(950, 650));
        loggedInTablePanel.add(loggedInTableScroll);

        loggedInComboMonth = new JComboBox<>();
        loggedInComboMonth.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20)); // top, left, bottom, right
        loggedInComboMonth.addItem("All");
        loggedInComboMonth.addActionListener(e -> reloadTable());

        loggedInCenterContainer.add(loggedInComboMonth, BorderLayout.NORTH);
        loggedInCenterContainer.add(loggedInTablePanel);
        add(loggedInCenterContainer, BorderLayout.SOUTH);

        btnTimeIn.addActionListener(e -> checkIn());
        btnTimeOut.addActionListener(e -> checkOut());
    }

    public void setLoggedIn(String userLoggedIn, String userLastName, String userFirstName) {
        this.loggedInEmployee = userLoggedIn;
        this.loggedInLastName = userLastName;
        this.loggedInFirstName = userFirstName;
        loadAvailableMonths();
        reloadTable();
    }

    private void loadAvailableMonths() {
        loggedInComboMonth.removeAllItems();
        loggedInComboMonth.addItem("All");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        CSVHandler.loadAvailableMonths(Constants.ATTENDANCE_CSV, loggedInEmployee, dateFormat)
                .forEach(loggedInComboMonth::addItem);
    }

    private void reloadTable() {
        loggedInAttendanceModel.setRowCount(0);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern(Constants.TIME_FORMAT);
        String selectedMonth = (String) loggedInComboMonth.getSelectedItem();

        try (Scanner scanner = new Scanner(new File(Constants.ATTENDANCE_CSV), "UTF-8")) {
            boolean skipHeader = true;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                String[] parts = line.split(",", -1);
                if (parts.length >= 6 && parts[0].trim().equals(loggedInEmployee)) {
                    try {
                        LocalDate date = LocalDate.parse(parts[3].trim(), dateFormat);
                        String monthYear = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + date.getYear();
                        if (!"All".equals(selectedMonth) && !monthYear.equals(selectedMonth)) continue;

                        loggedInAttendanceModel.addRow(new Object[]{
                            parts[3].trim(), parts[4].trim(), parts[5].trim()
                        });
                    } catch (Exception e) {
                        // Skip malformed entries
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading CSV: " + e.getMessage());
        }
    }

    private void checkIn() {
        String timeIn = LocalTime.now().format(DateTimeFormatter.ofPattern(Constants.TIME_FORMAT));
        int checkInConfirm = JOptionPane.showConfirmDialog(null, "The time now is: " + timeIn + "\nConfirm Check In?", "Confirm Check In", JOptionPane.YES_NO_OPTION);

        if (checkInConfirm == JOptionPane.YES_OPTION) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.ATTENDANCE_CSV, true))) {
                writer.write(String.join(",", loggedInEmployee, loggedInLastName, loggedInFirstName, formattedDate, timeIn, ""));
                writer.newLine();
                loadAvailableMonths();
                reloadTable();
                JOptionPane.showMessageDialog(this, "Checked In Successfully!");
                loggedInTopPanel.remove(btnTimeIn);
                loggedInTopPanel.add(btnTimeOut);
                loggedInTopPanel.revalidate();
                loggedInTopPanel.repaint();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error writing to file.");
            }
        }
    }

    private void checkOut() {
        String timeOut = LocalTime.now().format(DateTimeFormatter.ofPattern(Constants.TIME_FORMAT));
        int checkOutConfirm = JOptionPane.showConfirmDialog(null, "The time now is: " + timeOut + "\nConfirm Check Out?", "Confirm Check Out", JOptionPane.YES_NO_OPTION);

        if (checkOutConfirm == JOptionPane.YES_OPTION) {
            try (BufferedReader reader = new BufferedReader(new FileReader(Constants.ATTENDANCE_CSV));
                 BufferedWriter writer = new BufferedWriter(new FileWriter("src\\temp.csv"))) {
                String line;
                boolean updated = false;

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",", -1);
                    if (!updated && parts.length >= 5 && parts[3].equals(formattedDate) && !parts[4].isEmpty() && parts[5].isEmpty()) {
                        line = String.join(",", parts[0], parts[1], parts[2], parts[3], parts[4], timeOut);
                        updated = true;
                    }
                    writer.write(line);
                    writer.newLine();
                }
                reader.close();
                writer.close();

                new File(Constants.ATTENDANCE_CSV).delete();
                new File("src/temp.csv").renameTo(new File(Constants.ATTENDANCE_CSV));

                JOptionPane.showMessageDialog(this, "Checked Out at " + timeOut);
                loggedInTopPanel.remove(btnTimeOut);
                loggedInTopPanel.add(btnTimeIn);
                loggedInTopPanel.revalidate();
                loggedInTopPanel.repaint();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error during Check-Out.");
            }
            loadAvailableMonths();
            reloadTable();
        }
    }
}
