/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph_application.utils;

/**
 *
 * @author NobbyDobbyCobby
 */

import motorph_application.utils.EmployeeData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class PayrollCalculator {
    private static final double SSS_RATE_MIN = 135.00; // Minimum SSS contribution
    private static final double SSS_RATE_MAX = 1125.00; // Maximum SSS contribution
    private static final double PHILHEALTH_RATE = 0.04; // 4% PhilHealth contribution
    private static final double PHILHEALTH_CAP = 4000.00; // Max PhilHealth contribution
    private static final double PAGIBIG_CONTRIBUTION = 100.00; // Fixed Pag-IBIG contribution
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(Constants.TIME_FORMAT);

    // Simplified SSS contribution table (monthly salary ranges)
    private static final double[][] SSS_TABLE = {
        {4000, 4250, 135.00}, {4250, 4750, 157.50}, {4750, 5250, 180.00}, // Example ranges
        {5250, 5750, 202.50}, {5750, 6250, 225.00}, {6250, 6750, 247.50},
        // Add more ranges as needed up to {29750, 999999, 1125.00}
    };

    // Simplified withholding tax table (monthly taxable income)
    private static final double[][] TAX_TABLE = {
        {20833, 33333, 0.20, 0.00},        // 20% over 20,833
        {33333, 66667, 0.25, 2083.33},    // 25% over 33,333 + 2,083.33
        {66667, 166667, 0.30, 7083.33},   // 30% over 66,667 + 7,083.33
        {166667, 666667, 0.32, 22083.33}, // 32% over 166,667 + 22,083.33
        {666667, Double.MAX_VALUE, 0.35, 102083.33} // 35% over 666,667 + 102,083.33
    };

    public static class PayrollResult {
        public double grossSalary;
        public double sssDeduction;
        public double philhealthDeduction;
        public double pagibigDeduction;
        public double withholdingTax;
        public double netSalary;

        public PayrollResult(double grossSalary, double sssDeduction, double philhealthDeduction,
                             double pagibigDeduction, double withholdingTax, double netSalary) {
            this.grossSalary = grossSalary;
            this.sssDeduction = sssDeduction;
            this.philhealthDeduction = philhealthDeduction;
            this.pagibigDeduction = pagibigDeduction;
            this.withholdingTax = withholdingTax;
            this.netSalary = netSalary;

        }
    }

    public static PayrollResult calculatePayroll(EmployeeData employee, String attendanceFile, String selectedMonth) {
        double hoursWorked = calculateTotalHoursWorked(employee.empNo, attendanceFile, selectedMonth);
        double grossSalary = calculateGrossSalary(employee, hoursWorked);
        double sssDeduction = calculateSSSDeduction(employee.basicSalary);
        double philhealthDeduction = calculatePhilHealthDeduction(employee.basicSalary);
        double pagibigDeduction = calculatePagIBIGDeduction();
        double taxableIncome = grossSalary - (sssDeduction + philhealthDeduction + pagibigDeduction);
        double withholdingTax = calculateWithholdingTax(taxableIncome);
        double netSalary = grossSalary - (sssDeduction + philhealthDeduction + pagibigDeduction + withholdingTax);

        return new PayrollResult(grossSalary, sssDeduction, philhealthDeduction, pagibigDeduction,
                                withholdingTax, netSalary);
    }

    private static double calculateTotalHoursWorked(String empNo, String attendanceFile, String selectedMonth) {
        double totalHours = 0.0;
        try (BufferedReader reader = new BufferedReader(new FileReader(attendanceFile))) {
            String line;
            boolean skipHeader = true;
            while ((line = reader.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }
                String[] parts = line.split(",", -1);
                if (parts.length >= 6 && parts[0].trim().equals(empNo)) {
                    try {
                        LocalDate date = LocalDate.parse(parts[3].trim(), DATE_FORMATTER);
                        String monthYear = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + date.getYear();
                        if ("All".equals(selectedMonth) || monthYear.equals(selectedMonth)) {
                            LocalTime logIn = LocalTime.parse(parts[4].trim(), TIME_FORMATTER);
                            LocalTime logOut = parts[5].trim().isEmpty() ? LocalTime.now() : LocalTime.parse(parts[5].trim(), TIME_FORMATTER);
                            totalHours += Duration.between(logIn, logOut).toMinutes() / 60.0;
                        }
                    } catch (Exception e) {
                       
                    }
                }
            }
        } catch (IOException e) {
           
        }
        return totalHours;
    }

    private static double calculateGrossSalary(EmployeeData employee, double hoursWorked) {
        return (employee.hourlyRate * hoursWorked) + employee.riceSubsidy +
               employee.phoneAllowance + employee.clothingAllowance;
    }

    private static double calculateSSSDeduction(double basicSalary) {
        if (basicSalary < 4000) return SSS_RATE_MIN;
        if (basicSalary >= 29750) return SSS_RATE_MAX;
        for (double[] range : SSS_TABLE) {
            if (basicSalary >= range[0] && basicSalary < range[1]) {
                return range[2];
            }
        }
        return SSS_RATE_MIN; // Default to minimum if no range matches
    }

    private static double calculatePhilHealthDeduction(double basicSalary) {
        double contribution = basicSalary * PHILHEALTH_RATE;
        return Math.min(contribution, PHILHEALTH_CAP);
    }

    private static double calculatePagIBIGDeduction() {
        return PAGIBIG_CONTRIBUTION;
    }

    private static double calculateWithholdingTax(double taxableIncome) {
        for (double[] range : TAX_TABLE) {
            if (taxableIncome >= range[0] && taxableIncome < range[1]) {
                return ((taxableIncome - range[0]) * range[2]) + range[3];
            }
        }
        return 0.0; // Default to no tax if no range matches
    }
}