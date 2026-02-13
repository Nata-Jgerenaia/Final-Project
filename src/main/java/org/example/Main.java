package org.example;

public class Main {
    // Fixed: Added 'public' and 'String[] args' so Java recognizes this as a starting point
    public static void main(String[] args) {

        // Fixed: Changed 'IO.println' to the standard 'System.out.println'
        System.out.println("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            System.out.println("i = " + i);
        }
    }
}