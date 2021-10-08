package com.company.products;

import com.company.categories.Category;

import java.util.ArrayList;
import java.util.Comparator;
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

    public List<Product> filterByPriceRange(String start, String  end) {
        Guard.Against.InvalidPriceFormat(start);
        Guard.Against.InvalidPriceFormat(end);
        return products.stream()
                .filter(product -> priceRangeComparison(start, end, product.priceAsDouble()))
                .toList();
    }

    private boolean priceRangeComparison(String start, String end, Double price) {
       return Double.parseDouble(start) <= price && price <= Double.parseDouble(end);
    }

    public List<Product> filterByCategory(Category category) {
        return products.stream().filter(product -> product.category().equals(category)).toList();
    }

    //Note: using startsWith includes results for example ICA, ICA Basic & ICA Premium in a search for ICA
    // else use -> product.brand().equalsIgnoreCase(brand)

    public List<Product> filterByBrand(String brand) {
        return products.stream()
                .filter(product -> product.brand().toLowerCase().startsWith(brand.toLowerCase()))
                .toList();
    }

    public List<Product> filterByName(String name) {
        return products.stream()
                .filter(product -> product.name().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    public List<Product> sortByPriceAscending() {
        return products.stream()
                .sorted(Comparator.comparingDouble(Product::priceAsDouble))
                .toList();
    }

    public List<Product> sortByPriceDescending() {
        return products.stream()
                .sorted(Comparator.comparingDouble(Product::priceAsDouble).reversed())
                .toList();
    }


    // save changes to file
        // ?? when stock quantity changes, product added or product removed or any of the three?

}


