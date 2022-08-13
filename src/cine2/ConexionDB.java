package cine2;
import java.sql.*;
import javax.swing.JOptionPane;

public class ConexionDB {
    Connection on;
    
    public Connection conexion() 
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
                    
            on = DriverManager.getConnection("jdbc:mysql://localhost/cine", "root", "");
            System.out.println("Conexion Exitosa");
            JOptionPane.showMessageDialog(null, "Conexion Realizada");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Error "+ e.getMessage());
        }
        return on;
    }
    
    Statement createStatement()
    {
        throw new UnsupportedOperationException("No soportado");
    }
    
}