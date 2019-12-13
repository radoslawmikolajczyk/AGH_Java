package main;

import java.io.IOException;
import java.util.Locale;

public class MainClass {
    public static void main(String[] args) throws IOException {
        CSVtitanic();
    }

    public static void CSVwith_header() throws IOException {
        CSVReader reader = new CSVReader("with-header.csv",";",true);
        while(reader.next()){
            int id = reader.getInt("id");
            String name = reader.get("imię");
            int nrdomu = reader.getInt("nrdomu");

            System.out.printf(Locale.US,"%d %s %d\n",id, name, nrdomu);
        }
    }

    public static void CSVno_header() throws IOException {
        CSVReader reader1 = new CSVReader("no-header.csv",";",false);
        while(reader1.next()){
            int id = reader1.getInt(0);
            String name = reader1.get(1);
            int nrdomu = reader1.getInt(4);
            System.out.printf(Locale.US,"%d %s %d\n",id, name, nrdomu);
        }
    }

    public static void CSVaccelerator() throws IOException {
        CSVReader reader1 = new CSVReader("accelerator.csv",";",true);
        while(reader1.next()){
            double X = reader1.getDouble(0);
            double Y = reader1.getDouble(1);
            double time = reader1.getDouble(6);
            System.out.printf(Locale.US,"%f %f %f\n",X, Y, time);
        }
    }

    public static void CSVelec() throws IOException {
        CSVReader reader1 = new CSVReader("elec.csv",",",true);
        while(reader1.next()){
            double period = reader1.getDouble(2);
            double nswprice = reader1.getDouble(3);
            String clas = reader1.get(8);
            System.out.printf(Locale.US,"%f %f %s\n",period, nswprice, clas);
        }
    }

    public static void CSVmissing_values() throws IOException{
        CSVReader reader1 = new CSVReader("missing-values.csv",";",true);
        while(reader1.next()){
            int id = reader1.getInt(0);
            String name = reader1.get(2);
            double density = reader1.getDouble(4);

            System.out.printf(Locale.US,"%d %s %f\n",id, name, density);
        }
    }

    public static void CSVtitanic() throws IOException{
        CSVReader reader1 = new CSVReader("titanic-part.csv",",",true);
        while(reader1.next()){
            int id = reader1.getInt(0);
            String name = reader1.get("Name");
            String sex = reader1.get("Sex");

            System.out.printf(Locale.US,"%d %s %s\n",id, name, sex);
        }
    }
}
