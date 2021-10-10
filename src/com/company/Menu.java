package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    public static int handleSelection (Scanner scanner, int count) {
        int selection = 0;
        while (true) {
            try {
                selection = Integer.parseInt(scanner.nextLine());
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number corresponding to the menu.");
                continue;
            }

            if(selection < 0 || selection > count) {
                System.out.println("Try again. Please enter a number corresponding to the menu.");
            } else
                break;
        }
        return selection;
    }
}
