/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph_application.utils;

/**
 *
 * @author NobbyDobbyCobby
 */
public class EmployeeData {
    
    public String empNo;
    public String lastName;
    public String firstName;
    public String birthday;
    public String address;
    public String phone;
    public String sss;
    public String philhealth;
    public String tin;
    public String pagibig;
    public String status;
    public String position;
    public String supervisor;
    public double basicSalary;
    public double riceSubsidy;
    public double phoneAllowance;
    public double clothingAllowance;
    public double grossRate;
    public double hourlyRate;

    public String[] toCSVArray() {
        return new String[]{
            empNo, lastName, firstName, birthday, address, phone, sss, philhealth, tin, pagibig,
            status, position, supervisor, String.valueOf(basicSalary), String.valueOf(riceSubsidy),
            String.valueOf(phoneAllowance), String.valueOf(clothingAllowance), String.valueOf(grossRate),
            String.valueOf(hourlyRate)
        };
    }
}