package main;

import java.util.Scanner;

public class Fibo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        int [] tab = new int[n];
        if (n>45 || n < 1)
            return;
        try {
            int fib1 = 1;
            int fib2 = 1;
            tab[0] = 1;
            tab[1] = 1;
            for (int i = 2; i < n; i++) {
                tab[i] = fib1 + fib2;
                fib1 = fib2;
                fib2 = tab[i];
            }
            for (int i = 0; i < n; i++)
                System.out.println(tab[i]);
        } catch (ArrayIndexOutOfBoundsException | NegativeArraySizeException exception){
            System.out.println("Index out of bounds! Please try again with higher number.\n");
            main(new String[]{"Sec", "Chan"});
        }

    }
}
