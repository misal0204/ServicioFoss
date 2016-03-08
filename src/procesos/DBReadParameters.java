package procesos;

import DBConnection.DBConnect;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Misael Recinos Fecha: 03 - febrero - 2016 Hora: 10:35pm
 */
public class DBReadParameters {

    public String Readparameters(String condicion) {
        DBConnect db = new DBConnect();
        Connection conn = db.Connect();
        Statement st;
        ResultSet result;
        String query = "";
        String resultado_consulta = null;

        switch (condicion) {
            case "P_PATH_FOSS":
                query = "SELECT VALUE FROM DT_PARAMETERS WHERE PARAMETER='" + condicion + "'";
                break;
            case "P_PATH_FOSS_C":
                query = "SELECT VALUE FROM DT_PARAMETERS WHERE PARAMETER='" + condicion + "'";
                break;
            case "PAIS":
                query = "SELECT IDPAIS FROM DT_PARAMETERS WHERE PARAMETER='" + condicion + "'";
                break;
        }

        try {
            st = conn.createStatement();
            result = st.executeQuery(query);
            while (result.next()) {

                resultado_consulta = result.getString(1);

            }
            result.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.err.println("Error en recuperar parametros: " + e.getMessage());
        } catch (NullPointerException ne) {
            System.err.println("Erro null pointer in DBReadparameters:" + ne.getMessage());          
        }
        return resultado_consulta;
    }

}
