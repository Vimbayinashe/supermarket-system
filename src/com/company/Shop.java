package com.company;

import com.company.categories.Categories;
import com.company.categories.Category;
import com.company.products.DefaultData;
import com.company.products.NewProduct;
import com.company.products.Product;
import com.company.products.Products;
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

        //print numbered list of categories
        printCategories(categories.categories());

        //capture input from user barcode, name, brand, category, price, quantity
        System.out.println("Enter product barcode, name, brand, categoryNumber, price & quantity separated by a comma");
        System.out.println("example: 7123456789900, sparkling water, MyBrand, 8, 12.50, 500");
        //split by "," , trim spaces, c
        //getCategory from input number (position)
        //check validLong(product[0]) -> print fail message & return if(Long.parseLong error)
        //return Optional? new ProductStockItem(Product, StockItem),

        // if(Optional is not empty)
        //create newProduct & add to products    products.addProduct(String[] newProduct)
        //create StockItem & add to stock       stock.addProduct(List newStockItem)

        String[] newProduct1 = pattern.split(scanner.nextLine());

        String[] newProduct = Arrays.stream(newProduct1).map(String::trim).toArray(String[]::new);

        System.out.println(Arrays.toString(newProduct));
        //List<String[]> prods = List.of(newProduct);

        if(newProduct.length != 6) {
            System.out.println("Please enter six properties for the new product.");
            //return Optional.empty();
        }
        if (!validLong(newProduct[0].trim())) {
            System.out.println("Invalid barcode entered.");
            //return;  Optional.empty() ?
        }
        if(invalidInt(newProduct[3].trim()) || Integer.parseInt(newProduct[3].trim()) > categories.categories().size())
            System.out.println("Invalid category entered.");

        if(invalidInt(newProduct[5].trim())){
            System.out.println("Product's quantity is an invalid number.");
            //return;  Optional ?
        }
        if(Integer.parseInt(newProduct[5].trim()) < 0){
            System.out.println("Product's quantity is less than zero.");
            //return;  Optional ?
        }

        //todo: handle wrong price format

        Category category = categories.getCategory(Integer.parseInt(newProduct[3].trim())); //perhaps string

        Product product = new Product(
                Long.parseLong(newProduct[0]), newProduct[1], newProduct[2], category, newProduct[4]
        );

        StockItem stockItem = new StockItem(
                Long.parseLong(newProduct[0].trim()), Integer.parseInt(newProduct[5].trim())
        );

        Optional<NewProduct> result = Optional.of(new NewProduct(product, stockItem));
        //return result;

        //todo: convert result into a "NewProduct" record with (Product, StockItem),
        // return Optional<NewProduct>  & addProduct(NewProduct.product), addProduct(NewProduct, stockItem)


        //todo: everything in a while loop until user cancels or addsNewProduct

        //feedback if producted added or failed(wrong barcode, qty etc)

        //addNewProduct().ifPresent(categories.addCategory(result));

        //addNewProduct();

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

        //todo? : add date-timestamp combo to file name (if time permits),
        // let user enter desire filename? writing over current file is easiest
        saveFile(categories.listOfStrings(), "categories");

        List<String> combined = listOfProductsAndStock(products, stock);
        saveFile(combined, "products and quantities");
    }

    private void printCategories(List<Category> categories) {
        categories.forEach(category -> printCategory(categories, category));
    }

    private void printCategory(List<Category> categories, Category category) {
        System.out.println(categories.indexOf(category) + 1 + ". " + category.name());
    }

    private Optional<String[]> addNewProduct() {
        return Optional.empty();
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
        return new Product(Long.parseLong(product[0]), product[1], product[2], new Category(product[3]),
                product[4]);
    }

}


/*
 * a method in Main that is called to create new products
 *   print out list of possible categories
 *   categories.forEach(category -> System.out.println((categories.indexOf(category) + 1) + " " + category.name()));
 *
 *   choose category by number
 *   get chosen category and insert into new product
 *   categories.get(5);
 *
 *   store categories in a Hashmap instead?
 *   -> choose an int as input value (that represents product key in Hashmap) to choose a category
 *   add / save new product to file?
 *
 *    //save files to csv
 *

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