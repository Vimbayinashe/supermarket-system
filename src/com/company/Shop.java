package com.company;

import com.company.categories.Categories;
import com.company.categories.Category;
import com.company.products.*;

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
        Categories categories = new Categories();
        Products products = new Products();
        DefaultData defaultData = new DefaultData();

        categories = getCategories(defaultData.categories());
        products = getProducts(categories, defaultData.inventoryList());

        //Command[] commands = new Command[] { categories };
        Products finalProducts = products;

        commands = new Command[] {
                products,
                categories
        };
        //commands[0].execute();



        //Menu
        int selection = 0;
        do {
            printMenuOptions();
            selection = Menu.handleSelection(scanner, 5);
            executeSelection(selection, categories, products);
        } while (selection != 0);


        //add a new product todo: as a method on Products?
            //update product qty, price, even Categories.new()
        /*printCategories(categories.categories());
        Product newProduct = addNewProduct(categories);
        products.addProduct(newProduct);

         */


        //todo: Sale
        //product printing, filter (see Sales) performed here
        //produces a list<Product> (as above)
        //sales receipt created & products stock decreases

        //printProductsCustomerView(products.listOfProducts());

        /* implementation of methods for: filtering, searching for price intervals, categories, product name/part of
        pdt name, brand sorting: price (low - high), (high - low), alphabetically (A-Z), (Z-A)  */
        //remember -> return an Optional, handle successful result & no result (Optional.empty)


//        //filtering price intervals
//        List<Product> priceFiltered = products.priceRange("0", "10.5");
//        printProductsCustomerView(priceFiltered);
//        Long productBarcode = priceFiltered.get(2).barcode();
//        System.out.println(productBarcode);
//
//
//        //filtering for a category
//        //user must choose a number from a list of categories => position
//        int position = 7;
//        Category selectedCategory = categories.getCategory(position);
//        List<Product> categoryFiltered = products.filterByCategory(selectedCategory);
//        printProductsCustomerView(categoryFiltered);
//
//
//        //filter by brand name
//        List<Product> brandFiltered = products.filterByBrand("ica");
//        printProductsCustomerView(brandFiltered);
//
//
//        //filter by product name / part of name
//        List<Product> nameFiltered = products.filterByName("apple");
//        printProductsCustomerView(nameFiltered);
//

        //sort by price
//        List<Product> sortedByPrice = products.sortByPriceAscending();
//        List<Product> sortedByPrice = products.sortByPriceDescending();
//        List<Product> sortedByPrice = products.sortByBrandDescending();
//        printProductsCustomerView(sortedByPrice);


        saveFile(categories.listOfStrings(), "categories");

        List<String> combined = commaSeparatedListOfProducts(products);
        saveFile(combined, "products");


    }

    private void executeSelection(int selection, Categories categories, Products products) {
        switch (selection) {
            case 0 -> System.out.println("Shutdown program 0");
            case 1 -> commands[0].execute("view");
            case 2 -> System.out.println("Chose 2: buy products");
            case 3 -> commands[0].execute("add");
            case 4 -> commands[1].execute("");
            case 5 -> System.out.println("Chose 5: update products / price");
        }
    }


    private void printMenuOptions() {
        System.out.println(
                """
                Welcome to The Shop, what would you like to do today?
                1. View Products
                2. Buy some products
                3. Add a new product
                4. Add a new category
                5. Update product price or quantity
                0. Exit program
                """
        );

        /*
            System.out.println(
                """
                Welcome to The Shop, what would you like to do today?
                1. Add a new product
                2. Add a new category
                3. Buy some products
                4. Update product price or quantity
                0. Exit program
                """
        );
         */
    }

    private Product addNewProduct(Categories categories) {

        String[] userInput;
        String[] newProduct = new String[0];
        boolean userTyping = true;

        while (userTyping) {
            System.out.println("Categories can be selected from the list above.");
            System.out.println("Enter product barcode, name, brand, categoryNumber, price & quantity separated by a comma");
            System.out.println("example: 7123456789900, carbonated water, MyBrand, 8, 12.50, 500");

            userInput = pattern.split(scanner.nextLine());
            newProduct = Arrays.stream(userInput).map(String::trim).toArray(String[]::new);

            if (newProduct.length != 6) {
                System.out.println("Please enter six properties for the new product.");
                continue;
            }

            if (!validLong(newProduct[0])) {
                System.out.println("Invalid barcode entered.");
                continue;
            }

            if (!validInt(newProduct[3]) || Integer.parseInt(newProduct[3]) > categories.categories().size()) {
                System.out.println("Invalid category entered.");
                continue;
            }

            if (!validInt(newProduct[5])) {
                System.out.println("Product's quantity is an invalid number.");
                continue;
            }

            if (Integer.parseInt(newProduct[5]) < 0) {
                System.out.println("Product's quantity is less than zero.");
                continue;
            }

            try {
                Guard.Against.InvalidPriceFormat(newProduct[4]);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }

            userTyping = false;
        }

        Category category = categories.getCategory(Integer.parseInt(newProduct[3]));

        return new Product(newProduct[0], newProduct[1], newProduct[2], category, newProduct[4],newProduct[5]);
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

/*
*   todo: purchase loop
*    print product list
*     option to search by name, price range, sort
*    user chooses product by index, print product details, confirm product
*   request quantity, user inputs
*   print list or user chooses (finished selecting products)
*   user see product name, brand, indiv pric, quantity, subtotal per product    & last line: final price
*   user confirms purchase or cancel
*   receipt with (above info again)
*   receipt saved to file
 */


/*
 *   Creating new products
 *   check user input for correct values & give feedback / don't attempt to create new items & crash your program
 *       - int(price), long(barcode), valid category
 *
 *   product details -> create new item in Products (list of available products)
 *   product.barcode & quantity -> added to Stock
 */


/*
 *   check that quantity > 0 && quantity-purchase > 0
 *   check that "new" product barcode is not present in stock   &   product Item is not present in Products (list)
 */


/*
 * adding new category -> english only, check current list first <= request user
 * read Martin's message on Teams also
 */