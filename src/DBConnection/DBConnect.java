package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import log.log_event;

/*
   @Creado el 03 de febrero de 2016, 08:30 AM
   @Autor: José Misael Recinos V.
   @Version: 08 de febrero de 2016

    Clase DBConnect, dedicado a la conexión de base de datos
    y la aplicación.
*/

public class DBConnect {
    
    //Instancia o servicio a la que se conectará.
    private final String INSTANCIA="srvdbha";
    //Puerto utilizado para la comunicación entre la aplicación y la instancia
    private final String PUERTO="1521";
    //Base de datos
    private final String DB="molinos";
    //Usuario de base de datos
    private final String USER="datalabr2";
    //Password de usuario
    private final String PASS="datalabr2";
    
    
    /* Método que realiza la conexión a la base de datos
       @return Devuelve una conexión con la base de datos, en estado open. 
    */
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
    } //Cierre de método Connect
}
