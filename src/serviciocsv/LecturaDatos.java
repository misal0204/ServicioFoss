/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviciocsv;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import procesos.File_Processs;

/**
 *
 * @author Misael Recinos
 */
public class LecturaDatos {

    private final int tiempo_trabajo = 10;
    private final static long ONCE_PER_DAY = 1000 * 60 * 60 * 24;

    Timer tiempo_notificacion;

    public LecturaDatos() {
        tiempo_notificacion = new Timer();
    }

    private static Calendar getTimeExecute() {

        Calendar date = Calendar.getInstance();
        date.set(Calendar.HOUR_OF_DAY, 15);
        date.set(Calendar.MINUTE, 43);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        return date;
    }

    public void Servicio_ReadCSV() {

        /*Timer tiempo_muestras;
         tiempo_muestras = new Timer();*/
        File_Processs file_csv = new File_Processs();

        //System.out.println("Tamaño de archivo: ");
        //System.out.println(file_csv.SizeFile(file_csv.fichero) + "Mb");
        file_csv.ReadCSV();

        Calendar calendar_read_csv = Calendar.getInstance();

        //calendar_read_csv.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar_read_csv.set(Calendar.HOUR_OF_DAY, 9);
        calendar_read_csv.set(Calendar.MINUTE, 45);
        calendar_read_csv.set(Calendar.SECOND, 0);
        calendar_read_csv.set(Calendar.MILLISECOND, 0);

        TimerTask recoger_muestras = new TimerTask() {

            @Override
            public void run() {

                File_Processs file_csv = new File_Processs();

                System.out.println("Tamaño de archivo: ");
                System.out.println(file_csv.SizeFile(file_csv.fichero) + "Mb");
                file_csv.ReadCSV();
            }
        };
        tiempo_notificacion.schedule(recoger_muestras, getTimeExecute().getTime(), tiempo_trabajo * 60 * 1000);
    }
}
