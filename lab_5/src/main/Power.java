package main;

public class Power extends Node {
    double p;
    Node arg;
    Power(Node n,double p){
        arg = n;
        this.p = p;
    }

    @Override
    double evaluate() {
        double argVal = arg.evaluate();
        return Math.pow(argVal,p);
    }


    int getArgumentsCount(){return 1;}

    @Override
    Node diff(Variable var) {
        Prod r =  new Prod(sign*p,new Power(arg,p-1));
        r.mul(arg.diff(var));
        return r;
    }

    @Override
    boolean isZero(Variable var) {
        return false;
    }


    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        if(sign<0)b.append("-");
        int argSign = arg.getSign();
        int cnt = arg.getArgumentsCount();
        boolean useBracket = false;
        if(argSign<0 ||cnt>1)useBracket = true;
        String argString = arg.toString();
        if(useBracket)b.append("(");
        b.append(argString);
        if(useBracket)b.append(")");
        b.append("^");
        b.append(p);
        return b.toString();
    }

}
