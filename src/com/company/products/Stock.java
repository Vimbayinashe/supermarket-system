package com.company.products;

import com.company.categories.Categories;
import com.company.categories.Category;

import java.util.ArrayList;
import java.util.List;

public class Stock {

    private static List<Item> stock;

    //todo: add stock, fill up stock with initial products, reduce / increase stock items


    private List<Item> initialStock() {
        List<Item> stock = new ArrayList<>();

//        Categories

        stock.add(new Item(5588944L, "Honeycrunch apple", "ICA Selection", new Category("fruit and vegetables"), "2", 500));

        return stock;
    }


    //create initial stock items


    //add a stock item method
    public void addProduct(ProductDetails details) {

        stock.add(new Item(
                details.barcode(),
                details.name(),
                details.brand(),
                details.category(),
                details.price(),
                details.quantity()
        ));
        
    }


    //remove a stock item



    // save changes to file
        // ?? when stock quantity changes, item added or item removed or any of the three?




    //todo:

    /*
    * private constructor -> cannot create new instances, only one stock object?
    *
     */
}


