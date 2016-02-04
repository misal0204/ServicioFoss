package log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class log_event {

    public void LogFoss(String mensaje) {

        String ruta = "D:\\Harisa\\Librerias para CSV y Java\\Servicio de windows\\log\\log_foss.txt";

        Date ahora = new Date();
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");

        String fecha = formateador.format(ahora);
        String hora = hourFormat.format(ahora);

        String MensajeInicio = "Inicio de registro: " + fecha + " - Hora: " + hora;
        String fh = "fecha: " + fecha + " - Hora: " + hora;
        String inicio = hora;

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
                bw.write("Inicio: " + inicio);
                bw.newLine();
                bw.write(mensaje);
                bw.newLine();
                bw.write("Finalizado: " + fin);
                bw.newLine();
                bw.write("------------------------------------------");

            } else {

                Date d = new Date();
                DateFormat hora_existe_fin = new SimpleDateFormat("HH:mm:ss");
                String fin = hora_existe_fin.format(d);

                bw = new BufferedWriter(new FileWriter(file));
                bw.write(MensajeInicio);
                bw.newLine();
                bw.write("Inicio: " + inicio);
                bw.newLine();
                bw.write(mensaje);
                bw.newLine();
                bw.write("Finalizado: " + fin);
                bw.newLine();
                bw.write("------------------------------------------");

            }
            bw.close();
        } catch (Exception e) {
            System.err.println("Error en archivo log: " + e.getMessage());
        }
    }
}
