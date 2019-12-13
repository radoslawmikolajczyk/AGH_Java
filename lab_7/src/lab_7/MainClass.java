package lab_7;

import java.io.IOException;
import java.util.Locale;

public class MainClass {
    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        /*CSVReader c1 = new CSVReader("admin-units.csv",",",true);
        int i = 0;
        while(c1.next() && i < 100){
            int id = c1.getInt(0);
            String name = c1.get(2);
            int fare = c1.getInt(3);
            int pop = c1.getInt(4);
            System.out.printf(Locale.US,"%d %s %d %d\n",id, name, fare,pop);
            i++;
        }
        */
        AdminUnitList a1 = new AdminUnitList();
        a1.read("admin-units.csv");
        a1.fixMissingValues();
        // System.out.println(Arrays.toString(a1.selectByName("Kraków", false).units.toArray()));
        // a1.list(System.out,0,5);

        //AdminUnitList a2 = new AdminUnitList();
        //a2.lineString("admin-units.csv","Kraków");
        /*System.out.println(a1.units.get(4).toString());
        double t1 = System.nanoTime()/1e6;
        System.out.println(Arrays.toString(a1.getNeighbors(a1.units.get(4),100).units.toArray()));
        double t2 = System.nanoTime()/1e6;
        System.out.printf(Locale.US,"t2-t1=%f\n",t2-t1);*/

        //lista.selectByName("Rokietnica",false).sortInplaceByName().list(System.out);

        // wybór (i sortowanie) elementów zaczynających się na „K”
        //lista.filter(a->a.name.startsWith("K")).sortInplaceByName().list(System.out);

        //wybór jednostek będących powiatami, których parent.name to województwo małopolskie
        //lista.filter(a-> a.adminLevel == 6 && a.parent.name.matches("województwo małopolskie")).list(System.out);

        //wybór jednostek będących miejscowosciami, których obszar > 50
        //lista.filter(a-> a.adminLevel == 8 && a.area > 50).list(System.out);

        //wybór jednostek będących gminami, których populacja > 50000
        //lista.filter(a-> a.adminLevel == 7 && a.population > 50000).list(System.out);

        /*AdminUnitQuery query = new AdminUnitQuery()
                .selectFrom(a1)
                .where(a -> a.area > 1000)
                .or(a -> a.name.startsWith("Sz"))
                .sort(Comparator.comparingDouble(a -> a.area))
                .limit(100);
        query.execute().list(System.out);*/

        double t1 = System.nanoTime() / 1e6;
        a1.rTree(a1.units.get(4), 100);
        double t2 = System.nanoTime() / 1e6;
        for (AdminUnit n : a1.units.get(4).neighbours) {
            System.out.println(n.toString());
        }
        System.out.printf(Locale.US, "t2-t1=%f\n", t2 - t1);
    }
}
