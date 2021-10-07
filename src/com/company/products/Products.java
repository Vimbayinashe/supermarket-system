package com.company.products;

import java.util.ArrayList;
import java.util.List;

public class Products {

    private List<Product> stock = new ArrayList<>();

    //todo: getProduct by index or barcode instead? => if so, how to get index when given barcode


    public Product getProductByBarcode(int index) {
        return stock.get(index);
    }

    public Product getProductByIndex(int index) {
        return stock.get(index);
    }

    public void printProductsAsNumberedList() {
        stock.forEach(category -> System.out.println((stock.indexOf(category) + 1) + " " + category.toDisplayString()));
    }


    public void addProduct(Product product) {
        stock.add(product);
    }


    // save changes to file
        // ?? when stock quantity changes, product added or product removed or any of the three?

}


