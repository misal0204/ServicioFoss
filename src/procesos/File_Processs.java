package procesos;

import com.csvreader.CsvReader;
import entidad.FileCSV;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class File_Processs {

    protected String campo;
    protected String valor;
    List<FileCSV> valores_csv = new ArrayList<>();

    private String[] valores_campos
            = {"SampleId=String", "ApplicationModel=String","AmDescription=String",
               "Constituent1/Name=String","Constituent1/DisplayedResult=String","Constituent1/PredictedValue=Number",
               "Constituent1/MinPredictedValue=Number","Constituent1/MaxPredictedValue=Number",
               "Constituent2/Name=String","Constituent2/DisplayedResult=String","Constituent2/PredictedValue=Number",
               "Constituent2/MinPredictedValue=Number","Constituent2/MaxPredictedValue=Number",
               "Constituent3/Name=String","Constituent3/DisplayedResult=String","Constituent3/PredictedValue=Number",
               "Constituent3/MinPredictedValue=Number","Constituent3/MaxPredictedValue=Number",
               "Constituent4/Name=String","Constituent4/DisplayedResult=String","Constituent4/PredictedValue=Number",
               "Constituent4/MinPredictedValue=Number","Constituent4/MaxPredictedValue=Number"};
    private int count_campos = 0;

    private int count = 0;

    public void ReadCSV() {

        try {
            CsvReader read_csv = new CsvReader("D:/Harisa/Librerias para CSV y Java/Servicio de windows/foss2.csv");

            while (read_csv.readRecord()) {
                String cam = read_csv.get(0);
                String val = read_csv.get(1);
                //System.out.println(read_csv.get(0)+" "+read_csv.get(1));

                valores_csv.add(new FileCSV(cam, val));
            }

            read_csv.close();

            for (FileCSV file : valores_csv) {
                StringBuffer campo_raiz;

                if (file.getCampo().length() > 10) {
                    String c = file.getCampo();

                    campo_raiz = new StringBuffer(c.substring(0, 32));

                    StringBuffer campo_comparar = campo_raiz.append(valores_campos[count_campos]);

                    if (c.contentEquals(campo_comparar)) {
                        System.out.println(c + " " + file.getValor());

                        if ((count_campos) < valores_campos.length - 1) {
                            count_campos++;
                        } else {
                            count_campos = 0;
                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Error en archivo: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error en I/O: " + e.getMessage());
        }

    }

    public void CreateFile() {

    }
}
