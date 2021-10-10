package com.company.sales;

import com.company.products.Product;
import com.company.products.Products;

import java.util.ArrayList;
import java.util.List;

public class Sales {
    private final List<Receipt> receipts = new ArrayList<>();

    private List<Receipt> receipts() {
        return List.copyOf(receipts);
    }

    public void addReceipt(Receipt receipt) {
        receipts.add(receipt);
    }

    public void sale(Products products) {

        //while loop until exit
            //see usual list
            //filter options

        //print to customer list

        //customer choose product
        //confirm product to customer & customer choose quantity

        //enter new product number, f for filter, x for exit, l for product list

        //on exit: show receipt details, confirm purchase or cancel

        //confirm - message & save

        // user see product name, brand, indiv pric, quantity, subtotal per product    & last line: final price
        // user confirms purchase or cancel receipt with (above info again) receipt saved to file



    }

    public void printProductList(List<Product> list) {

    }

    public void saveReceipt(Receipt receipt) {
        //add to receipts

        String fileName = "receipt - " + receipt.receiptNumber();

        //save file


    }
}
