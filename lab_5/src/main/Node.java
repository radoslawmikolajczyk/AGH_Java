package main;

abstract public class Node {
    int sign=1;
    Node minus(){
        sign = -1;
        return this;
    }
    Node plus(){
        sign = 1;
        return this;
    }
    int getSign(){return sign;}
    /**
     * Oblicza wartość wyrażenia dla danych wartości zmiennych
     * występujących w wyrażeniu
     */
    abstract double evaluate();
    /**
     *
     * zwraca tekstową reprezentację wyrażenia
     */
    public String toString(){return "";}
    /**
     *
     * Zwraca liczbę argumentów węzła
     */
    int getArgumentsCount(){return 0;}

    abstract Node diff(Variable var);
    abstract boolean isZero(Variable var);
}
