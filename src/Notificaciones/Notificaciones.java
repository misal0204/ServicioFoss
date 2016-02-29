package Notificaciones;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.border.TitledBorder;
import static serviciocsv.ServicioCSV.i;

/**
 *
 * @author Misael Recinos
 */
public class Notificaciones {

    public void Aviso_emergente() {
        JFrame f = new JFrame("Mantenimiento de servicio");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setDefaultCloseOperation(0);
        f.setResizable(false);
        Container content = f.getContentPane();
        JProgressBar progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        TitledBorder border = BorderFactory.createTitledBorder("Mantenimiento en progreso... NO utilice el equipo (FOSS)");
        progressBar.setBorder(border);
        content.add(progressBar, BorderLayout.CENTER);
        f.setSize(450, 100);
        f.setLocation(450, 300);
        f.setVisible(true);

        Timer tiempo_bar;
        tiempo_bar = new Timer();

        TimerTask tiempo_barprogress = new TimerTask() {

            @Override
            public void run() {
                i++;
                progressBar.setValue(i);
                if (i == 1000) {
                    f.dispose();
                    i = 0;
                    tiempo_bar.cancel();
                }
            }
        };
        tiempo_bar.schedule(tiempo_barprogress, 0, 1000);
    }
}
