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

public class LeavePanel extends JPanel {

    public LeavePanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, getHeight()));
        
        JLabel leaveheading = new JLabel("Leave Application");
        leaveheading.setFont(new Font("Arial", Font.BOLD, 20));
        leaveheading.setAlignmentX(Component.LEFT_ALIGNMENT);
        leaveheading.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 0));
        add(leaveheading, BorderLayout.NORTH);
    }
}