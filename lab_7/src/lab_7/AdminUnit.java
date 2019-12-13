package lab_7;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AdminUnit {
    String name;
    int adminLevel;
    double population;
    double area;
    double density;
    AdminUnit parent;
    BoundingBox bbox = new BoundingBox();
    List<AdminUnit> children = new LinkedList<>();

    List<AdminUnit> neighbours = new ArrayList<>();  //na potrzeby rTree

    public void fixMissingValues() {
        if (this.parent != null && (this.parent.area == -1 || this.parent.density== -1))
            this.parent.fixMissingValues();
        if (this.parent != null && this.area == -1)
            this.area = this.parent.area;
        if (this.parent != null && this.density == -1)
            this.density = this.parent.density;
        if (this.population == -1)
            this.population = this.area * this.density;
    }
    String getChildrenNames(List<AdminUnit> children){
        if (children.isEmpty())
            return "brak";
        StringBuilder names = new StringBuilder();
        for (AdminUnit a : children)
            names.append(a.name).append(", ");
        names.deleteCharAt(names.length()-2);
        names.deleteCharAt(names.length()-1);
        return names.toString();
    }

    @Override
    public String toString(){
        return "AdminUnit { Nazwa jednostki: " + name +
                " , Typ jednostki: " + adminLevel +
                ", Powierzchnia: " + area +
                ", Gęstość: " + density +
                ", rodzic: "+ this.parent.name +
                ", dzieci: "+ getChildrenNames(children) +
                ", bbox: " + bbox + " }" +
                "\n";
    }
}
