package com.company;

import com.company.categories.Categories;
import com.company.categories.Category;
import com.company.products.*;
import com.company.sales.Sales;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Shop {

    private static final Pattern pattern = Pattern.compile(",");
    private final Scanner scanner = new Scanner(System.in);
    private Command[] commands = new Command[5];

    public static void main(String[] args) {
        Shop shop = new Shop();
        shop.run();
    }

    private void run() {
        DefaultData defaultData = new DefaultData();
        Categories categories = getCategories(defaultData.categories());
        Products products = getProducts(categories, defaultData.inventoryList());
        Sales sales = new Sales();

        commands = new Command[] { products, categories, sales };

        int selection = 0;
        do {
            printMenuOptions();
            selection = Menu.handleSelection(scanner, 5);
            executeSelection(selection, categories, products);
        } while (selection != 0);

    }

    private void saveFiles(Categories categories, Products products) {
        saveFile(categories.listOfStrings(), "categories");
        List<String> combined = commaSeparatedListOfProducts(products);
        saveFile(combined, "products");
    }

    private void executeSelection(int selection, Categories categories, Products products) {
        switch (selection) {
            case 0 -> shutDown(categories, products);
            case 1 -> commands[0].execute("view", categories);
            case 2 -> commands[2].execute("", products);
            case 3 -> commands[0].execute("add", categories);
            case 4 -> commands[1].execute("", "");
            case 5 -> commands[0].execute("update", "");
        }
    }

    private void shutDown(Categories categories, Products products) {
        System.out.println("Saving files...");
        saveFiles(categories, products);
        System.out.println("Shop system shutting down.");
    }

    private void printMenuOptions() {
        System.out.println(
                """
                
                Welcome to The Shop, what would you like to do today?
                1. View Products
                2. Buy some products
                3. Add a new product
                4. Add a new category
                5. Update a product's price
                0. Exit program
                """
        );

    }

    private void printCategories(List<Category> categories) {
        System.out.println("Product categories");
        categories.forEach(category -> printCategory(categories, category));
    }

    private void printCategory(List<Category> categories, Category category) {
        System.out.println(categories.indexOf(category) + 1 + ". " + category.name());
    }

    private Products getProducts(Categories categories, List<String[]> defaultInventory) {
        if(Files.exists(getPath("Default Product Details and Inventory"))) {
            List<String[]> data = readFile("Default Product Details and Inventory");
            return dataToProducts(data, categories);
        }
        else
            return dataToProducts(defaultInventory, categories);
    }

    private Categories getCategories(List<String> defaultCategories) {
        if(Files.exists(getPath("Default Categories"))) {
            List<String> categoriesData = readFileWithOneColumn("Default Categories");
            return dataToCategories(categoriesData);
        } else
            return dataToCategories(defaultCategories);
    }

    private List<String> readFileWithOneColumn(String name) {
        if(readFile(name).isEmpty())
            return List.of();
        return readFile(name).stream().map(item -> item[0]).toList();
    }

    private List<String[]> readFile(String name) {
        Path path = getPath(name);

        try(Stream<String> contents = Files.lines(path)) {
            return contents.map(pattern::split).toList();
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    private void saveFile(List<String> list, String name) {
        Path path = getPath(name);

        try {
            Files.write(path, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Path getPath(String name) {
        String fileName = name.concat(".csv");
        return Path.of("resources", fileName);
    }

    private List<String> commaSeparatedListOfProducts(Products products) {
        return products.streamOfProducts()
                .map(Product::toCommaSeparatedString)
                .toList();
    }

    private void printProductsCustomerView(List<Product> products) {
        if (products.isEmpty())
            System.out.println("No products found.");
        else
            products.forEach(product -> printProduct(products.indexOf(product) + 1, product));
    }

    private void printProduct(int index, Product product) {
        System.out.println(index + " " + product.brand() + " " + product.name() + " - "
                + product.price().replace('.', ',') + " kr");
    }

    private Categories dataToCategories(List<String> list) {
        Categories categories = new Categories();
        list.forEach(categories::addCategory);
        return categories;
    }

    private boolean validLong(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validPriceFormat(String price) {
        try {
            if(price.contains(","))
                Double.parseDouble(price.replace(",", "."));
            else
                Double.parseDouble(price);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private Products dataToProducts(List<String[]> inputProducts, Categories categories) {
        Products products = new Products();

        inputProducts.stream()
                .filter(product -> validLong(product[0]))
                .filter(product -> categories.contains(product[3]))
                .filter(product -> validPriceFormat(product[4]))
                .filter(product -> validInt(product[5]))
                .map(this::productToProductVariable)
                .forEach(products::addProduct);
        return products;
    }

    private Product productToProductVariable(String[] product) {
        return new Product(product[0], product[1], product[2], new Category(product[3]), product[4], product[5]);
    }

}

