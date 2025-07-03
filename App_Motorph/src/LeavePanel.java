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