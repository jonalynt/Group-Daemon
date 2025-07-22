/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph_application.utils;

/**
 *
 * @author NobbyDobbyCobby
 */
public class Constants {
    public static final String EMPLOYEE_DATA_CSV = "src\\MotorPH_EmployeeData.csv";
    public static final String ATTENDANCE_CSV = "src\\MotorPH_AttendanceRecord.csv";
    public static final String LOGIN_CSV = "src\\MotorPH_EmployeeLogin.csv";
    public static final String DATE_FORMAT = "MM/dd/yyyy";
    public static final String TIME_FORMAT = "H:mm";
    public static final String[] EMPLOYEE_COLUMNS = {
        "EmployeeNo", "LastName", "FirstName", "Birthday", "Address", "Phone",
        "SSS", "Philhealth", "TIN", "Pagibig", "Status", "Position", "Supervisor",
        "BasicSalary", "RiceSubsidy", "PhoneAllowance", "ClothingAllowance",
        "GrossRate", "HourlyRate"
    };
    public static final String[] ATTENDANCE_COLUMNS = {"Date", "Log In", "Log Out", "Hours Worked", "Daily Salary"};
}