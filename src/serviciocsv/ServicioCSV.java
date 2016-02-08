package serviciocsv;

public class ServicioCSV {

    public static int i = 0;

    public static void main(String[] args) {

        Servicios service_read = new Servicios();
        Servicios service_copy=new Servicios();

        service_read.Servicio_ReadCSV();
        
        ////////////////////////////////////////////////////////////////////////////////////////
        //////TIMER DE Copy CSV//////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////

        service_copy.Servicio_CopyFile();

    }
}
