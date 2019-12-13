package lab_7;

import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * i had to copy CSVReader class from previous laboratory, cause
 * i've created it in different project (here are only packages from this laboratory)
  */

public class AdminUnitList {
    List<AdminUnit> units = new ArrayList<>();
    Map<Long, AdminUnit> idToAdminUnit = new HashMap<>();
    Map<AdminUnit,Long> adminUnitToParentId = new HashMap<>();
    Map<Long,List<AdminUnit>> parentid2child = new HashMap<>();

    public AdminUnitList(){}

    protected AdminUnitList(Stream<AdminUnit> units) {
        this.units = units.collect(Collectors.toList());
    }
    CSVReader c1;

    public void read(String filename) throws IOException {
        c1 = new CSVReader(filename,",",true);
        while(c1.next()){
            AdminUnit adminUnit = new AdminUnit();
            adminUnit.adminLevel = c1.getInt(3);
            adminUnit.name = c1.get(2);
            adminUnit.population = c1.getDouble(4);
            adminUnit.area = c1.getDouble(5);
            adminUnit.density = c1.getDouble(6);
            adminUnit.bbox.xmin = c1.getDouble("x1");
            adminUnit.bbox.xmax = c1.getDouble("x3");
            adminUnit.bbox.ymin = c1.getDouble("y1");
            adminUnit.bbox.ymax = c1.getDouble("y3");

            long parId = c1.getLong("parent");  // if there will be no parent it will default set value to -1

            idToAdminUnit.put(c1.getLong("id"),adminUnit);
            adminUnitToParentId.put(adminUnit,parId);

            units.add(adminUnit);

            if (parId != -1) {
                if (parentid2child.containsKey(parId)) {
                    parentid2child.get(parId).add(adminUnit);
                } else {
                    List<AdminUnit> children = new ArrayList<>();
                    children.add(adminUnit);
                    parentid2child.put(parId, children);
                }
            }
        }
        for (Map.Entry<AdminUnit, Long> m : adminUnitToParentId.entrySet()){
            AdminUnit key = m.getKey();
            long value = m.getValue();
            for (Map.Entry<Long, AdminUnit> m1 : idToAdminUnit.entrySet()){
                if (m1.getKey() == value && m1.getKey() != -1) {
                    key.parent = m1.getValue();
                    m1.getValue().children.add(key);
                } else if (value == -1){
                    AdminUnit fiction = new AdminUnit();
                    fiction.name = "brak";
                    key.parent = fiction;
                }
            }
        }
    }

    void list(PrintStream out){
        for (AdminUnit a: units)
            out.println(a.toString());
    }

    void list(PrintStream out, int offset, int limit ){
        try {
            for (int i = offset; i < offset + limit; i++) {
                out.println(units.get(i).toString());
            }
        }catch (Exception e){
            e.printStackTrace();
            out.println("List index out of bounds");
        }
    }

    AdminUnitList selectByName(String pattern, boolean regex){
        AdminUnitList ret = new AdminUnitList();
        for (AdminUnit a: units){
            if (regex){
                if (a.name.matches(pattern))
                    ret.units.add(a);
            } else {
                if (a.name.contains(pattern))
                    ret.units.add(a);
            }
        }
        return ret;
    }

    AdminUnitList getNeighbors(AdminUnit unit, double maxdistance){
        AdminUnitList ret = new AdminUnitList();

        if (unit.bbox.isEmpty()) return ret;

        for (AdminUnit u: units){
            if (u.adminLevel!=unit.adminLevel)
                continue;
            if(unit.adminLevel == 8){
                if (!u.bbox.isEmpty() && unit.bbox.distanceTo(u.bbox) <= maxdistance)
                    ret.units.add(u);
            } else {
                if (!u.bbox.isEmpty() && unit.bbox.intersects(u.bbox))
                    ret.units.add(u);
            }
        }
        return ret;
    }

    void rTree(AdminUnit unit, double maxdistance){    //getNeighbors usprawniony algorytm
        if (unit.parent == null) {
            return;
        }
        rTree(unit.parent,maxdistance);

        for (AdminUnit sib : unit.parent.children){
            if (unit == sib)
                continue;
            if (unit.adminLevel >= 8){
                if (unit.bbox.maxdistanceRTree(sib.bbox,maxdistance))
                    unit.neighbours.add(sib);
            } else if (unit.adminLevel < 8 && unit.bbox.intersects(sib.bbox))
                unit.neighbours.add(sib);
        }

        for (AdminUnit parn : unit.parent.neighbours){
            for (AdminUnit sib : parn.children){
                if (unit == sib)
                    continue;
                if (unit.adminLevel >= 8 && unit.bbox.maxdistanceRTree(sib.bbox,maxdistance))
                    unit.neighbours.add(sib);
                else if (unit.adminLevel < 8 && unit.bbox.intersects(sib.bbox))
                    unit.neighbours.add(sib);
            }
        }
    }

    public void fixMissingValues() {
        this.units.forEach(AdminUnit::fixMissingValues);
    }

    public void lineString(String filename,String name) throws IOException {
        c1 = new CSVReader(filename,",",true);
        while(c1.next()){
            String unitName = c1.get("name");
            if (unitName.equals(name)){
            double x1 = c1.getDouble("x1");
            double y1 = c1.getDouble("y1");
            double x2 = c1.getDouble("x2");
            double y2 = c1.getDouble("y2");
            double x3 = c1.getDouble("x3");
            double y3 = c1.getDouble("y3");
            double x4 = c1.getDouble("x4");
            double y4 = c1.getDouble("y4");

            System.out.print("LINESTRING(" + x1 + " " + y1 + ", " + x2 + " " + y2 + ", " + x3 + " " + y3 + ", " + x4 + " " + y4 + ", " + x1 + " " + y1 + ")");
            break;
            }
        }
    }
    AdminUnitList sortInplaceByName(){
        class Wew implements Comparator<AdminUnit>{
            @Override
            public int compare(AdminUnit o1, AdminUnit o2) {
                return o1.name.compareTo(o2.name);
            }
        }
        Collections.sort(units,new Wew());
        return this;
    }

    AdminUnitList sortInplaceByArea(){
        Comparator<AdminUnit> c = new Comparator<>() {
            @Override
            public int compare(AdminUnit o1, AdminUnit o2) {
                return Double.compare(o1.area,o2.area);
            }
        };
        Collections.sort(units,c);
        return this;
    }

    AdminUnitList sortInplaceByPopulation(){
        Collections.sort(units, Comparator.comparingDouble((AdminUnit o) -> o.population));
        return this;
    }

    AdminUnitList sortInplace(Comparator<AdminUnit> cmp){
        Collections.sort(units,cmp);
        return this;
    }

    AdminUnitList sort(Comparator<AdminUnit> cmp) throws CloneNotSupportedException {
        AdminUnitList ad = this;
        ad.sortInplace(cmp);
        return ad;
    }

    AdminUnitList filter(Predicate<AdminUnit> pred){
        AdminUnitList ret = new AdminUnitList();
        for (AdminUnit unit:this.units) {
            if (pred.test(unit)) ret.units.add(unit);
        }
        return ret;
    }

    AdminUnitList filter(Predicate<AdminUnit> pred,int offset, int limit){
        AdminUnitList ret = this.filter(pred);

        if (limit+offset>ret.units.size())
            limit=ret.units.size()-offset;

        if (limit<=0){
            throw new RuntimeException("illegal arguments");
        }
        ret.units = ret.units.subList(offset,offset+limit);
        return ret;
    }

    AdminUnitList filter(Predicate<AdminUnit> pred, int limit){
        return this.filter(pred,0,limit);
    }
}