/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package motorph_application;

import motorph_application.ui.LoginPanel;


/**
 *
 * @author NobbyDobbyCobby
 */
public class MotorPH_Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        javax.swing.SwingUtilities.invokeLater(() -> {
        new LoginPanel();});
    
}
}
