package serviciocsv;

import Notificaciones.Notificaciones;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import procesos.File_Processs;

/**
 *
 * @author Misael Recinos
 */
public class Servicios {

    private int segundos_read = 5;
    private int segundos_copy = 8;
    Timer tiempo_notificacion;
    
    Servicios()
    {
        tiempo_notificacion = new Timer();
    }

    public void Servicio_CopyFile() {
        //Timer tiempo_notificacion;
        Notificaciones noti = new Notificaciones();
        Calendar calendar_copy = Calendar.getInstance();

        //calendar_copy.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar_copy.set(Calendar.HOUR, 9);
        calendar_copy.set(Calendar.MINUTE, 30);
        calendar_copy.set(Calendar.SECOND, 0);
        calendar_copy.set(Calendar.MILLISECOND, 0);

        TimerTask realizar_copia = new TimerTask() {

            @Override
            public void run() {

                //JOptionPane.showMessageDialog(null, "Se iniciara el mantenimiento, no utilice el equipo (foss)");
                File_Processs copy_csv = new File_Processs();
                copy_csv.copyFile();
                noti.Aviso_emergente();

            }
        };
        tiempo_notificacion.schedule(realizar_copia,calendar_copy.getTime(), segundos_copy * 60 * 1000);
    }

    public void Servicio_ReadCSV() {

        /*Timer tiempo_muestras;
         tiempo_muestras = new Timer();*/
        File_Processs file_csv = new File_Processs();

        System.out.println("Tamaño de archivo: ");
        System.out.println(file_csv.SizeFile(file_csv.fichero) + "Mb");

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
        tiempo_notificacion.schedule(recoger_muestras, 0, segundos_read * 60 * 1000);
    }
}
