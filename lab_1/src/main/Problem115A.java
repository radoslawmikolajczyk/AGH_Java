package main;

import java.util.Scanner;

public class Problem115A {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        if (n < 1 || n > 2000) throw new RuntimeException("The number of employees can be between 1 and 2000.");

        int[] employees = new int[n];

        for (int i = 0; i < n; i++) {
            int p = scanner.nextInt();
            if ((p < 1 || p > n) && p != -1) throw new RuntimeException("Employee should have a manager that exists or not have manager at all");

            employees[i] = (p == -1) ? p : p - 1;
        }

        System.out.println(minimumNumerOfGroups(employees));
    }

    private static int minimumNumerOfGroups(int[] employees) {
        int top = 0;

        for (int employee : employees) {
            int depth = 0;
            int j = employee;
            while (j != -1) {
                depth++;
                j = employees[j];
            }
            top = Math.max(top, depth);
        }

        return top + 1;
    }
}