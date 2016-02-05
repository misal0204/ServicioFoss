package serviciocsv;

import Notificaciones.ProgressUpdate;
import java.io.IOException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import log.log_event;
import procesos.File_Processs;

public class ServicioCSV {

    public static void main(String[] args) throws IOException {
        
        //ProgressUpdate p=new ProgressUpdate();
        
        //p.setVisible(true);
        
        //log_event log= new log_event();
        //log.procesosWindows();
          
        
        File_Processs file_csv = new File_Processs();

        System.out.println("Tamaño de archivo: ");
        System.out.println(file_csv.SizeFile(file_csv.fichero) + "Mb");
          
        file_csv.ReadCSV();

        /*
         Calendar calendar = Calendar.getInstance();

         calendar.set(Calendar.HOUR_OF_DAY, 22);
         calendar.set(Calendar.MINUTE, 35);
         calendar.set(Calendar.SECOND, 0);
         calendar.set(Calendar.MILLISECOND, 0);

         Timer timer;
         timer = new Timer();

         TimerTask task = new TimerTask() {

         @Override
         public void run() {

         File_Processs file_csv = new File_Processs();

         System.out.println("Tamaño de archivo: ");
         System.out.println(file_csv.SizeFile(file_csv.fichero) + "Mb");
         file_csv.ReadCSV();
         }
         };
         timer.schedule(task, 0, 2000);
         */
        //timer.schedule(task, calendar.getTime(), 1000 * 60 * 60 * 8);
    }
}
