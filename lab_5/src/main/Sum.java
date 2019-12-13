package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sum extends Node {

    List<Node> args = new ArrayList<>();

    Sum(){}

    Sum(Node n1, Node n2){
        args.add(n1);
        args.add(n2);
    }

    Sum(Node...m){
        this.add(m);
    }
    Sum add(Node n){
        args.add(n);
        return this;
    }
    Sum add(Node... n){
        args.addAll(Arrays.asList(n));
        return this;
    }

    Sum add(double c){
        args.add(new Constant(c));
        return this;
    }

    Sum add(double c, Node n) {
        Node mul = new Prod(c,n);
        args.add(mul);
        return this;
    }

    @Override
    double evaluate() {
        double result =0;
        ///oblicz sumę wartości zwróconych przez wywołanie evaluate skłądników sumy
        for (Node a: args) {
            result += a.evaluate();
        }
        return sign*result;
    }

    int getArgumentsCount(){return args.size();}

    @Override
    Node diff(Variable var) {
        return new Sum(
                args.stream()
                        .filter(node -> !node.isZero(var))
                        .map(node -> node.diff(var))
                        .toArray(Node[]::new));
    }

    @Override
    boolean isZero(Variable var) {
        return args.stream().allMatch(node -> node.isZero(var));
    }

    public String toString(){
        StringBuilder b =  new StringBuilder();

        for (Node arg : args) {
            if (arg.sign < 0) b.append("(");
            b.append(arg.toString());
            if (arg.sign < 0) b.append(")");
            b.append(" + ");
        }
        if(!args.isEmpty())
            b.deleteCharAt(b.length()-2);
        return b.toString();
    }


}
