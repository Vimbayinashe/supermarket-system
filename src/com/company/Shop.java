package com.company;

import com.company.categories.Categories;
import com.company.categories.Category;
import com.company.products.*;
import com.company.stock.Stock;
import com.company.stock.StockItem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Shop {

    private static final Pattern pattern = Pattern.compile(",");
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Shop shop = new Shop();
        shop.run();
    }

    private void run() {
        Categories categories = new Categories();
        Products products = new Products();
        Stock stock = new Stock();
        DefaultData defaultData = new DefaultData();

        categories = getCategories(defaultData.categories());
        products = getProducts(categories, defaultData.inventoryList());
        stock = getStock(categories, defaultData.inventoryList());

        boolean addProduct = false;

        //menu Options
            //option that changes addProduct to true

        while(addProduct) {
            Optional<NewProduct> newProduct = addNewProduct(categories);

            if (newProduct.isPresent()) {
                products.addProduct((newProduct.get().product()));
                stock.addProduct(newProduct.get().stockItem());
            }
            addProduct = false;
        }

        //printProductsCustomerView(products.listOfProducts());

        //Search for product in Stock using barcode
//        System.out.println(products.getProduct(1).barcode());
//        System.out.println(products.productBarcode(1));


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

        //todo? : let user enter desire filename? writing over current file is easiest
        saveFile(categories.listOfStrings(), "categories");

        List<String> combined = listOfProductsAndStock(products, stock);
        saveFile(combined, "products and quantities");


    }

    private Optional<NewProduct> addNewProduct(Categories categories) {

        printCategories(categories.categories());

        //todo: everything in a while loop until user cancels or addsNewProduct

        String[] newProduct;
        while (true) {
            System.out.println("Enter product barcode, name, brand, categoryNumber, price & quantity separated by a comma");
            System.out.println("example: 7123456789900, sparkling water, MyBrand, 8, 12.50, 500");

            String[] userInput = pattern.split(scanner.nextLine());
            newProduct = Arrays.stream(userInput).map(String::trim).toArray(String[]::new);

            if (newProduct.length != 6) {
                System.out.println("Please enter six properties for the new product.");
                //return Optional.empty();
            }

            if (!validLong(newProduct[0])) {
                System.out.println("Invalid barcode entered.");
                //return Optional.empty();
            }

            if (invalidInt(newProduct[3]) || Integer.parseInt(newProduct[3]) > categories.categories().size()) {
                System.out.println("Invalid category entered.");
                //return Optional.empty();
            }

            if (invalidInt(newProduct[5])) {
                System.out.println("Product's quantity is an invalid number.");
                //return Optional.empty();
            }

            if (Integer.parseInt(newProduct[5]) < 0) {
                System.out.println("Product's quantity is less than zero.");
                //return Optional.empty();
                break;
            }

            try {
                Guard.Against.InvalidPriceFormat(newProduct[4]);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                //return Optional.empty();
            }
        }

        Category category = categories.getCategory(Integer.parseInt(newProduct[3]));

        Product product = new Product(newProduct[0], newProduct[1], newProduct[2], category, newProduct[4],newProduct[5]);

        StockItem stockItem = new StockItem(Long.parseLong(newProduct[0]), Integer.parseInt(newProduct[5]));

        return Optional.of(new NewProduct(product, stockItem));
    }

    private void printCategories(List<Category> categories) {
        System.out.println("Product categories");
        categories.forEach(category -> printCategory(categories, category));
    }

    private void printCategory(List<Category> categories, Category category) {
        System.out.println(categories.indexOf(category) + 1 + ". " + category.name());
    }

    private Stock getStock(Categories categories, List<String[]> defaultInventory) {
        if(Files.exists(getPath("Default Product Details and Inventory"))) {
            List<String[]> data = readFile("Default Product Details and Inventory");
            return dataToStock(data, categories);
        }
        else
            return dataToStock(defaultInventory, categories);
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

    private List<String> listOfProductsAndStock(Products products, Stock stock) {
        return products.streamOfProducts()
                .map(product -> product.toCommaSeparatedString() + "," + stock.getQuantity(product.barcode()))
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

    private Stock dataToStock(List<String[]> products, Categories categories) {
        Stock stock = new Stock();

        products.stream()
                .filter(product -> categories.contains(product[3]))
                .filter(product -> validLong(product[0]))
                .map(this::productToStockItem)
                .forEach(stock::addProduct);
        return stock;
    }

    private StockItem productToStockItem(String[] product) {
        return new StockItem(Long.parseLong(product[0]), Integer.parseInt(product[5]));
    }

    private boolean validLong(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean invalidInt(String value) {
        try {
            Integer.parseInt(value);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private Products dataToProducts(List<String[]> inputProducts, Categories categories) {
        Products products = new Products();

        inputProducts.stream()
                .filter(product -> categories.contains(product[3]))
                .filter(product -> validLong(product[0]))
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