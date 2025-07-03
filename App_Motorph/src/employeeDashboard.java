
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class employeeDashboard extends JPanel {
    
    public JTable table;
    public DefaultTableModel model;

    //panels
    JPanel buttons = new JPanel();
    JPanel mainPanel = new JPanel();
    JPanel bottomPanel = new JPanel(new BorderLayout());
    
    // Form section
    JPanel formPanel = new JPanel(new GridLayout(3, 1, 5, 2));
    JPanel empInfo = new JPanel(new GridLayout(3, 2, 5, 5));
    JPanel personalInfo = new JPanel(new GridLayout(7, 2, 5, 5));
    JPanel financialInfo = new JPanel(new GridLayout(6, 2, 5, 5));
    
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
    
    public employeeDashboard(){
        
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, getHeight()));

        JLabel heading = new JLabel("Employee Database");
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(heading, BorderLayout.NORTH);
        
        // employees table
        String[] columnNames = {
                "EmployeeNo", "LastName", "FirstName", "Birthday", "Address", "Phone",
                "SSS", "Philhealth", "TIN", "Pagibig", "Status", "Position", "Supervisor",
                "BasicSalary", "RiceSubsidy", "PhoneAllowance", "ClothingAllowance",
                "GrossRate", "HourlyRate"
        };

        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setPreferredSize(new Dimension(1000, 300));
        add(tableScroll, BorderLayout.CENTER);

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
        //formPanel.add(personalInfo);
       // formPanel.add(financialInfo);

        bottomPanel.add(formPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
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
                        
             /*   //personal info from click table
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
                fininfohourlyrate.setText(table.getValueAt(selectedRow, 18).toString());         */                      
            }
        });
        
        // Load CSV
        loadCSV("src\\MotorPH_EmployeeData.csv");
    }
    
    //load csv
    private void loadCSV(String filePath) {
        try (Scanner scanner = new Scanner(new File(filePath),"ISO-8859-1")) {
            String[] headers = scanner.nextLine().split(",");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine(); // -1 to include empty
                if (!line.trim().isEmpty()) {
                String[] data = line.split(",", -1);
                model.addRow(data);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load Employee CSV: " + e.getMessage());
        }
    }
    public String getSelectedEmployeeNo() {
        int selectedemployee = table.getSelectedRow();
        if (selectedemployee != -1) {
            return table.getValueAt(selectedemployee, 0).toString();
        }
        return null;
    }
    
    public EmployeeData getFormData() {
        EmployeeData data = new EmployeeData();
        data.fullempNo = empNo.getText();
        return data;
    }
    
    public void reloadCSV() {
    model.setRowCount(0); // clear the table
    loadCSV("src\\MotorPH_EmployeeData.csv");
    empNo.setText("");
    }
    
    public void clearfields(){
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