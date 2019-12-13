package main;

import java.util.Scanner;

public class Problem610A {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int [] tab = new int[n];
        for (int i=0; i<n; i++)
            tab[i] = i+1;
        int result = 0;
        for (int i = 0; i < n;i++){
            for (int j=0; j<n;j++){
                if(tab[i]!=tab[j] && 2*tab[i] + 2*tab[j] == n){
                    result++;
                }
            }
        }
        System.out.println("Length of stick: " + n + ".\nAnd the final result of possible cuts its '" + result/2+"'.");
    }
}
