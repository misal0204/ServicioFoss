package serviciocsv;

public class ServicioCSV {

    public static int i = 0;

    public static void main(String[] args) {

        /*
        Servicios service_read = new Servicios();
        Servicios service_copy=new Servicios();

        service_copy.Servicio_CopyFile();
        
        service_read.Servicio_ReadCSV();
        */
        
        Servicios service = new Servicios();
        service.Servicio_ReadCSV();
        service.Servicio_CopyFile();
    }
}
