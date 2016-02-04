package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnect {
    
    public Connection Connect()
    {
        
        try {
            Connection conn;
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            conn= DriverManager.getConnection("jdbc:oracle:thin:@srvdbha:1521:molinos","datalabr2","datalabr2");
            
            return conn;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Coneccion fallida: "+ex.getMessage());
            return null;
        }
        
    }
}
