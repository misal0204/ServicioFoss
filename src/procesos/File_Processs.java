package procesos;

import DBConnection.DBConnect;
import com.csvreader.CsvReader;
import entidad.FileCSV;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import log.log_event;

public class File_Processs {

    private final String name_path_foss = "P_PATH_FOSS";
    private final String name_path_foss_copy = "P_PATH_FOSS_C";
    private String name_file_foss = "foss.csv";

    //public String fichero = "D:/Harisa/Librerias para CSV y Java/Servicio de windows/foss_mini.csv";
    //public String fichero_salida = "D:/Harisa/Librerias para CSV y Java/Servicio de windows/salida/";
    public String fichero = new DBReadParameters().Readparameters(name_path_foss) + name_file_foss;
    public String fichero_salida = new DBReadParameters().Readparameters(name_path_foss_copy);
    public String pais_foss = new DBReadParameters().Readparameters(name_path_foss);

    public String campo;
    public String valor;
    public String mensaje_log = "|-|Muestra ingresada: ";
    List<FileCSV> valores_csv = new ArrayList<>();

    private String[] valores_campos
            = {"SampleId=String", "AmDescription=String", "AnalyseCounter=Number",
                "Constituent1/Name=String", "Constituent1/DisplayedResult=String", "Constituent1/PredictedValue=Number",
                "Constituent1/MinPredictedValue=Number", "Constituent1/MaxPredictedValue=Number",
                "Constituent2/Name=String", "Constituent2/DisplayedResult=String", "Constituent2/PredictedValue=Number",
                "Constituent2/MinPredictedValue=Number", "Constituent2/MaxPredictedValue=Number",
                "Constituent3/Name=String", "Constituent3/DisplayedResult=String", "Constituent3/PredictedValue=Number",
                "Constituent3/MinPredictedValue=Number", "Constituent3/MaxPredictedValue=Number",
                "Constituent4/Name=String", "Constituent4/DisplayedResult=String", "Constituent4/PredictedValue=Number",
                "Constituent4/MinPredictedValue=Number", "Constituent4/MaxPredictedValue=Number"};

    private String[] resultado_analisis
            = {
                "DisplayedResult=String", "PredictedValue=Number",
                "MinPredictedValue=Number", "MaxPredictedValue=Number"
            };
    private String[] nombre_resultado
            = {"Constituent1", "Constituent2", "Constituent3", "Constituent4"};

    private final int fila_default = 4;

    private int count_campos = 0;
    private int count = 0;
    private int count_resultados = 0;
    private int cabecera = 1;
    private int count_filas = 0;
    private int ingresar = 0;
    private int registro = 0;
    private int imp = 0;
    private String fecha_muestra;
    private String tipo_analisis;

    public void ReadCSV() {
        try {
            DBConnect db = new DBConnect();
            Connection con = db.Connect();
            Statement q;

            ResultSet result;
            q = con.createStatement();

            Calendar fecha = new GregorianCalendar();
            int anno = fecha.get(Calendar.YEAR);

            String muestra = null;
            String muestra_nueva = null;
            String t, tipo = null;
            String countAnalisis = null;
            String resultado_fecha = "";
            String campo_evaluar = null;

            result = q.executeQuery("SELECT MAX(TO_CHAR(TIME_ANALISIS,'YYYY-MM-DD HH24:MI:SS')) FROM SM_FOSS");

            while (result.next()) {
                resultado_fecha = result.getString(1) != null ? result.getString(1) : "";
            }

            if (!resultado_fecha.isEmpty() || !resultado_fecha.equals("")) {

                //Recuperar el número de serial del equipo, se encuentra en el archivo IrTemp
                campo_evaluar = "RD/12417419/" + resultado_fecha + "/Constituent4/PasswordRequired=Bool";

            } else {
                ingresar = 1;
            }

            CsvReader read_csv = new CsvReader(fichero);

            while (read_csv.readRecord()) {
                String cam = read_csv.get(0);
                String val = read_csv.get(1);
                //Muestra todos los datos
                //System.out.println(read_csv.get(0)+" "+read_csv.get(1));

                valores_csv.add(new FileCSV(cam, val));
            }
            read_csv.close();

            for (FileCSV file : valores_csv) {
                StringBuffer campo_raiz;

                if (file.getCampo().length() > 15) {
                    String c = file.getCampo();
                    //Verificar si existe registro
                    if (ingresar != 1 && registro != 1) {

                        if (c.equals(campo_evaluar)) {
                            ingresar = 2;
                        }
                    }

                    if (ingresar == 1 || registro == 1) {

                        campo_raiz = new StringBuffer(c.substring(0, 32));

                        StringBuffer campo_comparar = campo_raiz.append(valores_campos[count_campos]);

                        if (c.contentEquals(campo_comparar)) {
                            if (valores_campos[count_campos].equals(valores_campos[0])) {
                                muestra = file.getValor();
                                fecha_muestra = c.substring(12, 31);
                                muestra_nueva = muestra;
                                //cambiar parametro "2015" por la variable anno                             
                                muestra_nueva = muestra.replace(String.valueOf(anno), "SV01");
                                //muestra_nueva = muestra.replace(String.valueOf("2015"), "M201");

                            } else if (valores_campos[count_campos].equals(valores_campos[1])) {
                                t = file.getValor();
                                tipo = t.equals("Harina de Trigo") ? "H" : "T";

                            } else if (valores_campos[count_campos].equals(valores_campos[2])) {
                                countAnalisis = file.getValor();
                                cabecera = 1;
                                mensaje_log = mensaje_log += muestra_nueva + " número análisis: " + countAnalisis;
                            } else {

                                if (valores_campos[count_campos].equals(valores_campos[3])
                                        || valores_campos[count_campos].equals(valores_campos[8])
                                        || valores_campos[count_campos].equals(valores_campos[13])
                                        || valores_campos[count_campos].equals(valores_campos[18])) {
                                    tipo_analisis = file.getValor();
                                } else {
                                    if (tipo_analisis.equals("Proteina")) {
                                        count_filas++;
                                        insertSMFossv(q, cabecera, countAnalisis, muestra_nueva, muestra, tipo, count_filas, fecha_muestra, tipo_analisis, c, file.getValor());
                                        count_filas = count_filas == 4 ? 0 : count_filas;
                                    } else {
                                        count_filas++;
                                        cabecera = 2;
                                        insertSMFossv(q, cabecera, countAnalisis, muestra_nueva, muestra, tipo, count_filas, fecha_muestra, tipo_analisis, c, file.getValor());
                                        count_filas = count_filas == fila_default ? 0 : count_filas;
                                        imp = 1;
                                    }
                                }
                            }

                            if ((count_campos) < valores_campos.length - 1) {
                                count_campos++;
                            } else {
                                count_campos = 0;
                            }
                        }
                    } else if (ingresar == 2) {
                        registro = 1;
                    }

                    if (imp == 1) {
                        new log_event().LogFoss(mensaje_log + " Registro ingresado correctamente");
                        mensaje_log = "";
                    }
                }
            }

            if (imp == 0) {
                new log_event().LogFoss("No se ingreso ningún registro");
            }

            q.close();
            con.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error en archivo: " + e.getMessage());
            new log_event().LogFoss("Error en archivo: método ReadCSV: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error en I/O: " + e.getMessage());
            new log_event().LogFoss("Error en I/O: método ReadCSV: " + e.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(File_Processs.class.getName()).log(Level.SEVERE, null, ex);
            new log_event().LogFoss("SQLException, método ReadCSV: " + ex.getSQLState());
        }
    }

    public void copyFile() {
        String Nombre_archivo;

        Calendar fecha = new GregorianCalendar();
        int a = fecha.get(Calendar.YEAR);
        int m = fecha.get(Calendar.MONTH);
        int d = fecha.get(Calendar.DAY_OF_MONTH);
        Nombre_archivo = "" + d + "" + (m + 1) + "" + a + "_foss.csv";

        fichero_salida = fichero_salida += Nombre_archivo;

        boolean alreadyExists = new File(fichero_salida).exists();

        Path FROM = Paths.get(fichero);
        Path TO = Paths.get(fichero_salida);

        CopyOption[] options = new CopyOption[]{
            StandardCopyOption.REPLACE_EXISTING,
            StandardCopyOption.COPY_ATTRIBUTES
        };

        if (alreadyExists) {
            try {

                Files.deleteIfExists(TO);
                Files.copy(FROM, TO, options);
                //Files.deleteIfExists(FROM);
                new log_event().LogFoss("Copia de registro");

            } catch (Exception e) {
                System.err.println("Error en copia de registro");
                new log_event().LogFoss("Error en copia de registro");
            }
        } else {
            try {

                Files.copy(FROM, TO, options);
                //Files.deleteIfExists(FROM);
                new log_event().LogFoss("Creación de registro");
            } catch (Exception e) {
                System.err.println("Error en creacion de registro");
                new log_event().LogFoss("Error en copia de registro");
            }
        }
    }

    public String SizeFile(String file) {
        File f = new File(file);
        DecimalFormat format = new DecimalFormat("##.##");

        double val = (f.length() / 1024f) / 1024f;
        String size = format.format(val);

        return size;
    }

    public void insertSMFossv(Statement query, int cab, String count, String muestra, String nueva_mues, String t, int c_fila, String fecha, String t_analisis, String campo, String valor) throws SQLException {
        Double campo_valor = Double.parseDouble(valor);

        switch (cab) {
            case 1:
                query.executeUpdate("INSERT INTO SM_FOSS(IDPAIS,IDPLANTA,COUNTANALISIS,IDMUESTRA_ORIGINAL,IDMUESTRA,IDTIPO,LINEA,VALOR1,TIME_ANALISIS) "
                        + "VALUES('SV','SV01','" + count + "','" + nueva_mues + "','" + muestra + "','" + t + "'," + c_fila + "," + campo_valor + ",TO_DATE('" + fecha + "','YYYY-MM-DD HH24:MI:SS'))");
                System.out.println(valor);
                break;
            case 2:
                switch (t_analisis) {
                    case "Humedad":
                        query.executeUpdate("UPDATE SM_FOSS SET VALOR2='" + campo_valor + "' WHERE LINEA=" + c_fila + " AND COUNTANALISIS='" + count + "'");
                        break;
                    case "Ceniza":
                        query.executeUpdate("UPDATE SM_FOSS SET VALOR3='" + campo_valor + "' WHERE LINEA=" + c_fila + " AND COUNTANALISIS='" + count + "'");
                        break;
                    case "Gluten Humedo":
                        query.executeUpdate("UPDATE SM_FOSS SET VALOR4='" + campo_valor + "' WHERE LINEA=" + c_fila + " AND COUNTANALISIS='" + count + "'");
                        break;
                }
                System.out.println(valor);
                break;
        }
    }

    public int findMuestra(Statement query, String cam) {
        String campo_evaluar;
        String resultado_fecha = null;
        ResultSet result;
        int ret = 0;

        try {
            result = query.executeQuery("SELECT MAX(TO_CHAR(TIME_ANALISIS,'YYYY-MM-DD HH24:MI:SS')) FROM SM_FOSS");

            while (result.next()) {
                resultado_fecha = result.getString(1);
            }

            if (resultado_fecha != null || !resultado_fecha.equals("")) {
                //Recuperar el número de serial del equipo, se encuentra en el archivo IrTemp
                campo_evaluar = "RD/12417419/" + resultado_fecha + "/Constituent4/PasswordRequired=Bool";
                if (cam.equals(campo_evaluar)) {
                    ret = 1;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(File_Processs.class.getName()).log(Level.SEVERE, null, ex);
            ret = 0;
        }
        return ret;
    }
}
