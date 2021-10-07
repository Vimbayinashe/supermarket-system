package com.company.products;

import java.util.ArrayList;
import java.util.List;

public class Products {

    private List<Item> stock = new ArrayList<>();

    //todo: getProduct by index or barcode instead? => if so, how to get index when given barcode

    public Item getProduct(int index) {
        return stock.get(index);
    }

    public void printProductsAsNumberedList() {
        stock.forEach(category -> System.out.println((stock.indexOf(category) + 1) + " " + category.toDisplayString()));
    }


    public void addProduct(Item item) {
        stock.add(item);
    }


    // save changes to file
        // ?? when stock quantity changes, item added or item removed or any of the three?

}


