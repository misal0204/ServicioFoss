/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviciocsv;

import Notificaciones.Notificaciones;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import procesos.File_Processs;

/**
 *
 * @author Misael Recinos
 */
public class CopiaDatos {

    private final int tiempo_trabajo = 10;
    private final static long ONCE_PER_DAY = 1000 * 60 * 60 * 24;

    Timer tiempo_notificacion;

    public CopiaDatos() {
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

    public void Servicio_CopyFile() {
        //Timer tiempo_notificacion;
        Notificaciones noti = new Notificaciones();

        TimerTask realizar_copia = new TimerTask() {

            @Override
            public void run() {

                //JOptionPane.showMessageDialog(null, "Se iniciara el mantenimiento, no utilice el equipo (foss)");
                File_Processs copy_csv = new File_Processs();
                copy_csv.copyFile();
                noti.Aviso_emergente();

            }
        };
        tiempo_notificacion.schedule(realizar_copia, getTimeExecute().getTime(), tiempo_trabajo * 60 * 1000);
    }
}
