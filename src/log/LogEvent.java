/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Misael Recinos
 */
public class LogEvent {

    //String PATH = new DBCrud().findPath(null, "P_PATH_ALVEO_LOG", "SV");
    //Variable de tipo String que proporciona la ruta donde se almacena el archivo txt.
    String ruta = "D:\\Harisa\\Librerias para CSV y Java\\Servicio de windows\\log\\";

    /*
     formatos de de hora y fechas.
     */
    Date ahora = new Date();
    DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat fecha_file = new SimpleDateFormat("ddMMyyyy");
    String fecha = formateador.format(ahora);
    String hora = hourFormat.format(ahora);
    String f_f = fecha_file.format(ahora); // f_f =fecha foss

    String file_log = "log_" + f_f + "_foss.txt";

    //Mensajes que se escriben el archivo txt, 
    String MensajeInicio = "Inicio de registro: ";
    String fh = "Fecha: " + fecha + " - Hora: " + hora;
    String inicio = hora;

    public void LogAlveo(String mensaje) {

        ruta = ruta + "/" + file_log;

        File file = new File(ruta);
        BufferedWriter bw;
        try {
            if (file.exists()) {
                Date d = new Date();
                DateFormat hora_existe_fin = new SimpleDateFormat("HH:mm:ss");
                String fin = hora_existe_fin.format(d);

                bw = new BufferedWriter(new FileWriter(file, true));
                bw.write(mensaje);
                bw.newLine();
            } else {

                Date d = new Date();
                DateFormat hora_existe_fin = new SimpleDateFormat("HH:mm:ss");
                String fin = hora_existe_fin.format(d);

                bw = new BufferedWriter(new FileWriter(file));
                bw.write(MensajeInicio);
                bw.newLine();
                bw.write(fh);
                bw.newLine();
                bw.write(mensaje);
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            System.err.println("Error en archivo log: " + e.getMessage());
        }
    }

}
