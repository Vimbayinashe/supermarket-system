package com.company.products;

import com.company.Command;
import com.company.Menu;
import com.company.categories.Categories;
import com.company.categories.Category;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Products implements Command {

    private final List<Product> products = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public Product getProduct(int index) {
        return products.get(index);
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> products() {
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

    public List<Product> filterByNameAndBrand(String query) {
        return products.stream()
                .filter(product -> nameOrBrand(query, product))
                .toList();
    }

    private boolean nameOrBrand(String query, Product product) {
        return product.brand().toLowerCase().startsWith(query.toLowerCase()) ||
                product.name().toLowerCase().contains(query.toLowerCase());
    }

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

    public List<Product> sortByBrandAscending() {
        return products.stream()
                .sorted(Comparator.comparing(Product::brand))
                .toList();
    }

    public List<Product> sortByBrandDescending() {
        return products.stream()
                .sorted(Comparator.comparing(Product::brand).reversed())
                .toList();
    }

    //sorting by Product name: requires a different view with name, brand & price sequence
    public List<Product> sortByNameAscending() {
        return products.stream()
                .sorted(Comparator.comparing(Product::name))
                .toList();
    }

    public List<Product> sortByNameDescending() {
        return products.stream()
                .sorted(Comparator.comparing(Product::name).reversed())
                .toList();
    }

    public List<Product> sortByQuantityAscending() {
        return products.stream()
                .sorted(Comparator.comparing(Product::quantity))
                .toList();
    }

    public List<Product> sortByQuantityDescending() {
        return products.stream()
                .sorted(Comparator.comparing(Product::quantity).reversed())
                .toList();
    }

    public Stream<Product> streamOfProducts() {
        return products.stream();
    }

    @Override
    public void execute(String option, Object categories) {

        switch(option) {
            case "view" -> viewProducts();
            case "add"  -> handleAddProduct();
            //case "update" -> updateProduct();
            default -> System.out.println("");

        }

        //add product

        //update

    }

    private void handleAddProduct() {
    }

    private void viewProducts() {
        printProductsCustomerView(products);
        int selection = 0;
        do {
            printProductsViewMenu();
            selection = Menu.handleSelection(scanner, 6);
            executeSelection(selection);
        } while (selection != 0);

    }

    private void executeSelection(int selection) {
        switch (selection) {
            case 1 -> printProductsCustomerView(sortByNameAscending());
            case 2 -> printProductsCustomerView(sortByNameDescending());
            case 3 -> printProductsCustomerView(sortByPriceAscending());
            case 4 -> printProductsCustomerView(sortByPriceDescending());
            case 5 -> handleProductFilter();
            case 6 -> handleCategoryFilter();
        }
    }

    private void handleCategoryFilter() {
        System.out.println("Enter product category");
        String input = scanner.nextLine().trim();
        printProductsCustomerView(filterByCategory(new Category(input)));
    }

    private void handleProductFilter() {
        System.out.println("Enter product name or brand");
        String input = scanner.nextLine().trim();
            printProductsCustomerView(filterByNameAndBrand(input));
    }

    private void printProductsViewMenu() {
        System.out.println(
                """
                
                Select a more specific product view
                1. Sorted (A-Z)
                2. Sorted (Z-A)
                3. Sorted (cheapest first)
                4. Sorted (most expensive first)
                5. Filtered by name or brand
                6. Filtered by category
                0. Return to previous menu
                """
        );
    }

    private void printProductsCustomerView(List<Product> products) {
        if (products.isEmpty())
            System.out.println("No products found.");
        else
            products.forEach(
                    product ->  System.out.println((products.indexOf(product) + 1) + ". " + product.toCustomerViewString())
            );
    }
}


