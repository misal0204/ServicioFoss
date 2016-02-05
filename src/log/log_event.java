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

public class log_event {

    String ruta = "D:\\Harisa\\Librerias para CSV y Java\\Servicio de windows\\log\\log_foss.txt";
    String fichero = "D:/Harisa/Librerias para CSV y Java/Servicio de windows/foss_mini.csv";

    Date ahora = new Date();
    DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");

    String fecha = formateador.format(ahora);
    String hora = hourFormat.format(ahora);

    String MensajeInicio = "Inicio de registro: " + fecha + " - Hora: " + hora;
    String fh = "fecha: " + fecha + " - Hora: " + hora;
    String inicio = hora;

    public void LogFoss(String mensaje) {

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

    public void procesosWindows() throws IOException {
        // LLAMAMOS LA VARIABLE DE ENTORNO WINDOWS Y EL PROGRAMA Q GESTIONA
        // LOS PROCESOS
        String consola = System.getenv("windir") + "\\System32\\" + "tasklist.exe";
        // Ejecutamos el comando
        Process proceso = Runtime.getRuntime().exec(consola);
        //OBTENEMOS EL BUFFER DE SALIDA
        BufferedReader entrada = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
        String tmp;
        while ((tmp = entrada.readLine()) != null) {
            System.out.println(tmp);

        }
        entrada.close();
    }
}
