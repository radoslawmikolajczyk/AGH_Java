package main;

import java.util.ArrayList;
import java.util.List;

public class Prod extends Node {
    List<Node> args = new ArrayList<>();

    Prod(){}

    Prod(Node n1){
        args.add(n1);
    }
    Prod(double c){
        //wywołaj konstruktor jednoargumentowy przekazując new Constant(c)
        args.add(new Constant(c));
    }

    Prod(Node n1, Node n2){
        args.add(n1);
        args.add(n2);
    }
    Prod(double c, Node n){
        //wywołaj konstruktor dwuargumentowy
        Node m = new Constant(c);
        args.add(m);
        args.add(n);
    }



    Prod mul(Node n){
        args.add(n);
        return this;
    }

    Prod mul(double c){
        Node m = new Constant(c);
        args.add(m);
        return this;
    }

    @Override
    double evaluate() {
        double result =1;
        // oblicz iloczyn czynników wołąjąc ich metodę evaluate
        for (Node a : args){
            result*=a.evaluate();
        }
        return sign*result;
    }
    int getArgumentsCount(){return args.size();}

    @Override
    Node diff(Variable var) {
        Sum r = new Sum();
        for(int i=0;i<args.size();i++){
            Prod m= new Prod();
            for(int j=0;j<args.size();j++){
                Node f = args.get(j);
                if(j==i)m.mul(f.diff(var));
                else m.mul(f);
            }
            if (!m.isZero(var))
                r.add(m);
        }
        return r;
    }

    @Override
    boolean isZero(Variable var) {
        return args.stream().anyMatch(node -> (node instanceof Constant && node.evaluate() ==0));
    }


    public String toString(){
        StringBuilder b =  new StringBuilder();

        for (Node arg : args) {
            if (arg.sign < 0) b.append("(");
            b.append(arg.toString());
            if (arg.sign < 0) b.append(")");
            b.append("*");
        }
        b.deleteCharAt(b.length()-1);
        return b.toString();
    }


}
