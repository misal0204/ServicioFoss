package log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
    @Creado el 04 de febrero de 2016, 08:30 PM
    @Autor: José Misael Recinos V.
    @version: 08/02/2016

    Clase log_event, dedicado a la captura de eventos que ocurren
    durante el trabajo de la aplicación, genera un archivo .txt
    el cual escribe cada proceso que realiza el servicio, como
    por ejemplo: lectura de archivo csv, copia de archivo csv. 
*/


public class log_event {

    //Variable de tipo String que proporciona la ruta donde se almacena el archivo txt.
    String ruta = "D:\\Harisa\\Librerias para CSV y Java\\Servicio de windows\\log\\";
    //Variable de tipo String que proporciona la ubicación de el archivo CSV.
    String fichero = "D:/Harisa/Librerias para CSV y Java/Servicio de windows/foss_mini.csv";
    
    
    /*
       formatos de de hora y fechas.
    */
    Date ahora = new Date();
    DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat fecha_file = new SimpleDateFormat("ddMMyyyy");
    String fecha = formateador.format(ahora);
    String hora = hourFormat.format(ahora);   
    String f_f=fecha_file.format(ahora); // f_f =fecha foss
    
    //ubicación mas nombre de archivo.  
    String file_log = "log_"+f_f+"_foss.txt";

    //Mensajes que se escriben el archivo txt, 
    String MensajeInicio = "Inicio de registro: " + fecha + " - Hora: " + hora;
    String fh = "fecha: " + fecha + " - Hora: " + hora;
    String inicio = hora;

    
    public void LogFoss(String mensaje) {
        ruta = ruta += file_log;
        
        File file = new File(ruta);
        BufferedWriter bw;
        try {
            if (file.exists()) {
                Date d = new Date();
                DateFormat hora_existe_fin = new SimpleDateFormat("HH:mm:ss");
                String fin = hora_existe_fin.format(d);

                bw = new BufferedWriter(new FileWriter(file, true));
                bw.newLine();
                bw.write(fh);
                bw.newLine();
                bw.write(mensaje);
                bw.newLine();
                bw.write("------------------------------------------");

            } else {

                Date d = new Date();
                DateFormat hora_existe_fin = new SimpleDateFormat("HH:mm:ss");
                String fin = hora_existe_fin.format(d);

                bw = new BufferedWriter(new FileWriter(file));
                bw.write(MensajeInicio);
                bw.newLine();
                bw.write(mensaje);
                bw.newLine();
                bw.write("------------------------------------------");

            }
            bw.close();
        } catch (Exception e) {
            System.err.println("Error en archivo log: " + e.getMessage());
        }
    }
}
