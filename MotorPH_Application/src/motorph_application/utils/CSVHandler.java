/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph_application.utils;

/**
 *
 * @author NobbyDobbyCobby
 */
import javax.swing.*;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import javax.swing.table.DefaultTableModel;

public class CSVHandler {

    public static void appendToCSV(String filePath, String... data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(String.join(",", data));
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to write to CSV: " + e.getMessage());
        }
    }

    public static void updateCSV(String filePath, String empNo, String... data) {
        File inputFile = new File(filePath);
        File tempFile = new File("src/temp.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    writer.write(line);
                    writer.newLine();
                    isHeader = false;
                    continue;
                }

                String[] parts = line.split(",", -1);
                if (parts.length > 0 && parts[0].equals(empNo)) {
                    writer.write(String.join(",", data));
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error updating CSV: " + e.getMessage());
            return;
        }

        if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
            JOptionPane.showMessageDialog(null, "Failed to update the CSV file.");
        }
    }

    public static void deleteFromCSV(String filePath, String empNo) {
        File inputFile = new File(filePath);
        File tempFile = new File("src/temp.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(empNo + ",")) continue;
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error while deleting: " + e.getMessage());
            return;
        }

        if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
            JOptionPane.showMessageDialog(null, "Could not update CSV file after deletion.");
        }
    }

    public static Set<String> loadAvailableMonths(String filePath, String empNo, DateTimeFormatter dateFormat) {
        
        Set<String> months = new LinkedHashSet<>();
        try (Scanner scanner = new Scanner(new File(filePath), "UTF-8")) {
            boolean skipHeader = true;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                String[] parts = line.split(",", -1);
                if (parts.length >= 6 && parts[0].trim().equals(empNo)) {
                    try {
                        LocalDate date = LocalDate.parse(parts[3].trim(), dateFormat);
                        String monthYear = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + date.getYear();
                        months.add(monthYear);
                    } catch (Exception e) {
                        // Skip invalid dates
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading CSV: " + e.getMessage());
        }
        return months;
    }

    public static void loadEmployeeData(String filePath, DefaultTableModel model) {
        try (Scanner scanner = new Scanner(new File(filePath), "ISO-8859-1")) {
            String[] headers = scanner.nextLine().split(",");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.trim().isEmpty()) {
                    String[] data = line.split(",", -1);
                    for (int i = 0; i < data.length; i++) {
                        if (data[i].matches(".*[Ee].*")) {
                            try {
                                BigDecimal bd = new BigDecimal(data[i]);
                                data[i] = bd.toPlainString();
                            } catch (NumberFormatException ignored) {}
                        }
                    }
                    model.addRow(data);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to load Employee CSV: " + e.getMessage());
        }
    }
}
