package lab_7;

import java.util.Comparator;
import java.util.function.Predicate;

public class AdminUnitQuery {
    AdminUnitList src;
    Predicate<AdminUnit> p = a->true;
    Comparator<AdminUnit> cmp;
    int limit = Integer.MAX_VALUE;
    int offset = 0;

    /**
     * Ustawia listę jako przetwarzane źródło
     * @param src
     * @return this
     */
    AdminUnitQuery selectFrom(AdminUnitList src){
        this.src = src;
        return this;
    }

    /**
     *
     * @param pred - ustawia predykat p
     * @return this
     */
    AdminUnitQuery where(Predicate<AdminUnit> pred){
        this.p = pred;
        return this;
    }

    /**
     * Wykonuje operację p = p and pred
     * @param pred
     * @return this
     */
    AdminUnitQuery and(Predicate<AdminUnit> pred){
        this.p = this.p.and(pred);
        return this;
    }
    /**
     * Wykonuje operację p = p or pred
     * @param pred
     * @return this
     */
    AdminUnitQuery or(Predicate<AdminUnit> pred){
        this.p = this.p.or(pred);
        return this;
    }

    /**
     * Ustawia komparator cmp
     * @param cmp
     * @return this
     */
    AdminUnitQuery sort(Comparator<AdminUnit> cmp){
        this.cmp = cmp;
        return this;
    }

    /**
     * Ustawia limit
     * @param limit
     * @return this
     */
    AdminUnitQuery limit(int limit){
        if (limit>=0) this.limit = limit;
        else throw new RuntimeException("limit must be positive");
        return this;
    }
    /**
     * Ustawia offset
     * @param offset
     * @return this
     */
    AdminUnitQuery offset(int offset){
        if (offset>=0) this.offset = offset;
        else throw new RuntimeException("offset must be positive");

        return this;
    }

    /**
     * Wykonuje zapytanie i zwraca wynikową listę
     * @return przefiltrowana i opcjonalnie posortowana lista (uwzględniamy także offset/limit)
     */
    AdminUnitList execute() throws CloneNotSupportedException {
        if (this.src == null) throw new RuntimeException("nie ustawiono AdminUnitList");

        if (this.cmp!=null) return this.src.filter(this.p,this.offset,this.limit).sort(this.cmp);
        else return this.src.filter(this.p,this.offset,this.limit);
    }
}
