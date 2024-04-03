/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connector;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author Azis
 */

public class ConnectionDatabases {
    public static Connection openConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost/perpustakaan", "root", "");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error connecting to the database:\n" + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}

