package com.company;

import com.company.products.Product;

import java.util.InputMismatchException;
import java.util.List;
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

    public static void printProductsCustomerView(List<Product> products) {
        if (products.isEmpty())
            System.out.println("No products found.");
        else
            products.forEach(
                    product ->  System.out.println((products.indexOf(product) + 1) + ". " + product.toCustomerViewString())
            );
    }
}
