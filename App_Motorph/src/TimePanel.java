import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

public class TimePanel extends JPanel {

    private String employeeNum;
    private double hourlyRate;
    private double ricesubsidy;
    private double phoneallowance;
    private double clothingallowance;
    private double benefits;
    
    public String loggedinemployee;
    public String loggedinfirstname;
    public String loggedinlastname;
    
    //time sheet table
    private JTable loggedinattendancetable;
    private DefaultTableModel loggedinattendancemodel;
    private JLabel loggedintotalsalary;
    private JComboBox<String> loggedincomboMonth;

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("H:mm");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    
    public String csvPath = "src\\MotorPH_AttendanceRecord.csv"; // Path to your attendance CSV
    
    JPanel loggedintablepanel = new JPanel();
    JPanel loggedinCenterContainer = new JPanel(new BorderLayout());
    JPanel loggedintopPanel = new JPanel(new GridLayout(1, 1, 5, 5));
    
    JButton btnTimeIn = new JButton("Check In");
    JButton btnTimeOut = new JButton("Check Out");
    
    //for time
    LocalDate date = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    String formattedDate = date.format(formatter);

    public TimePanel() {
        
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, getHeight()));
        
        JLabel timeheading = new JLabel("Time");
        timeheading.setFont(new Font("Arial", Font.BOLD, 20));
        timeheading.setAlignmentX(Component.LEFT_ALIGNMENT);
        timeheading.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 0));

        //MotorPH_AttendanceRecord.csv
        loggedinattendancemodel = new DefaultTableModel(new String[]{"Date", "Log In", "Log Out"}, 0);
        loggedinattendancetable = new JTable(loggedinattendancemodel);
        
        JScrollPane loggedintableScroll = new JScrollPane(loggedinattendancetable);
        loggedintableScroll.setPreferredSize(new Dimension(950, 650));
        loggedintablepanel.add(loggedintableScroll);
        
        loggedincomboMonth = new JComboBox<>();
        loggedincomboMonth.addItem("All");
        loggedincomboMonth.addActionListener(e -> reloadTable());
        
        btnTimeIn.setMaximumSize(new Dimension(180, 30));
        btnTimeOut.setMaximumSize(new Dimension(180, 30));
        
        
        loggedintopPanel.add(timeheading, BorderLayout.NORTH);
        loggedintopPanel.add(btnTimeIn);
        add(loggedintopPanel, BorderLayout.NORTH);
        
        loggedinCenterContainer.add(loggedincomboMonth, BorderLayout.NORTH);
        loggedinCenterContainer.add(loggedintablepanel);
        add(loggedinCenterContainer, BorderLayout.SOUTH);
        
        //action for buttons time in time out
        btnTimeIn.addActionListener(e -> checkin());
        btnTimeOut.addActionListener(e -> checkout());

}
    
    public void setloggedin(String userLoggedIn, String userLastname, String userFirstname) {
        this.loggedinemployee = userLoggedIn;
        this.loggedinlastname = userLastname;
        this.loggedinfirstname = userFirstname;
        
        loadAvailableMonths();
        reloadTable();
        
    }
    
    //for attendance table
    private void loadAvailableMonths() {
        
        loggedincomboMonth.removeAllItems();
        loggedincomboMonth.addItem("All");
        
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
                if (parts.length >= 6 && parts[0].trim().equals(loggedinemployee)) {
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
                loggedincomboMonth.addItem(month);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading CSV:\n" + e.getMessage());
        }
    }
    
    
    private void reloadTable() {
        
        loggedinattendancemodel.setRowCount(0);
        
        String selectedMonth = (String) loggedincomboMonth.getSelectedItem();

        try (Scanner scanner = new Scanner(new File(csvPath), "UTF-8")) {
            boolean skipHeader = true;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                String[] parts = line.split(",", -1);
                if (parts.length >= 6 && parts[0].trim().equals(loggedinemployee)) {
                    try {
                        LocalDate date = LocalDate.parse(parts[3].trim(), DATE_FORMAT);
                        String monthYear = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + date.getYear();

                        if (!"All".equals(selectedMonth) && !monthYear.equals(selectedMonth)) continue;

                        LocalTime logIn = LocalTime.parse(parts[4].trim(), TIME_FORMAT);
                        LocalTime logOut = LocalTime.parse(parts[5].trim(), TIME_FORMAT);
                     
                        loggedinattendancemodel.addRow(new Object[]{
                            parts[3].trim(), parts[4].trim(), parts[5].trim(),
                        });

                    } catch (Exception e) {
                        // skip malformed entries
                    }
                }
                
            }

        } catch (IOException e) { 
            JOptionPane.showMessageDialog(this, "Error reading CSV:\n" + e.getMessage());
        }
    }
    
    //check in action
    private void checkin(){
        
        String timeIn = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

        int checkinconfirm = JOptionPane.showConfirmDialog(null, "The time now is: " + timeIn + "\nConfirm Check In?", "Confirm Check In", JOptionPane.YES_NO_OPTION);

        if (checkinconfirm == JOptionPane.YES_OPTION) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvPath, true))){
             writer.write(loggedinemployee + "," + loggedinlastname + "," + loggedinfirstname + "," + formattedDate + "," + timeIn);
             loadAvailableMonths();
             reloadTable();
             JOptionPane.showMessageDialog(this, "Checked In Successfully!");
             loggedintopPanel.remove(btnTimeIn);
             loggedintopPanel.add(btnTimeOut);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error writing to file.");
        }
        }
    }
    
    //check out add to last column
    private void checkout(){

    String timeOut = LocalTime.now().plusHours(8).format(DateTimeFormatter.ofPattern("HH:mm"));

    int checkoutconfirm = JOptionPane.showConfirmDialog(null, "The time now is: " + timeOut + "\nConfirm Check Out?", "Confirm Check Out", JOptionPane.YES_NO_OPTION);
    
    BufferedReader reader = null;
    BufferedWriter writer = null;

    if(checkoutconfirm == JOptionPane.YES_OPTION){
    try {
        reader = new BufferedReader(new FileReader(csvPath));
        StringBuilder content = new StringBuilder();
        String line;
        boolean updated = false;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",", -1);

            if (!updated &&
                parts.length == 5 &&
                parts[3].equals(formattedDate) &&
                !parts[4].isEmpty()) {

                line += "," + timeOut; //Add time out
                updated = true;
            }

            content.append(line).append("\n");
        }
        reader.close();

        writer = new BufferedWriter(new FileWriter(csvPath));
        writer.write(content.toString());
        writer.close();
  
        JOptionPane.showMessageDialog(this, "Checked Out at " + timeOut);
        loggedintopPanel.add(btnTimeIn);
        loggedintopPanel.remove(btnTimeOut);

    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error during Check-Out.");
    } finally {
        try {
            if (reader != null) reader.close();
            if (writer != null) writer.close();
        } catch (IOException ignored) {}
    }
    }
    loadAvailableMonths();
    reloadTable();
    }
    
}