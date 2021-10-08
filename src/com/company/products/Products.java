package com.company.products;

import java.util.ArrayList;
import java.util.List;

public class Products {

    private final List<Product> products = new ArrayList<>();

    //todo: can only getIndexOf(Product product) => index => getProduct(index). Also unnecessary to search for pdt here

    public Long productBarcode(int index) {
        return products.get(index).barcode();
    }

    public Product getProduct(int index) {
        return products.get(index);
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> listOfProducts() {
        return List.copyOf(products);
    }


    // save changes to file
        // ?? when stock quantity changes, product added or product removed or any of the three?

}


