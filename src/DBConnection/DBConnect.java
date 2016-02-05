package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import log.log_event;

public class DBConnect {
    
    private final String INSTANCIA="srvdbha";
    private final String PUERTO="1521";
    private final String DB="molinos";
    private final String USER="datalabr2";
    private final String PASS="datalabr2";
    
    public Connection Connect()
    {    
        try {
            Connection conn;
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            conn= DriverManager.getConnection("jdbc:oracle:thin:@"+INSTANCIA+":"+PUERTO+":"+DB,USER,PASS);
            //new log_event().LogFoss("Connect a: "+DB);
            return conn;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Coneccion fallida: "+ex.getMessage());
            new log_event().LogFoss("Conexión a base de datos fallida: "+ex.getMessage());
            return null;
        }
        
    }
}
