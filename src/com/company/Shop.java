package com.company;

import com.company.categories.Categories;
import com.company.categories.Category;
import com.company.products.DefaultProducts;
import com.company.products.Product;
import com.company.products.Products;
import com.company.stock.Stock;
import com.company.stock.StockItem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Shop {

    public static void main(String[] args) {
        Shop shop = new Shop();
        shop.run();
    }

    private void run() {
        Categories categories = new Categories();
        Products products = new Products();
        Stock stock = new Stock();
        DefaultProducts defaultProducts = new DefaultProducts();


        //try to read from file
        // check for all 3 separate files -> add arg that shows what of categories, products & stock needsdefaults?
        //if files present -> update categories (!first), stock and products (Read from file)

        //else fill in details from initial default values
        categories = defaultCategories();
        try {       //does this interrupt the result of both methods if one of them has a problem
            products = generateProducts(defaultProducts.products(), categories);
            stock = generateStock(defaultProducts.products(), categories);
        } catch (Exception e) {
            e.printStackTrace();
        }


        printProductsCustomerView(products.listOfProducts());

        //Search for product in Stock using barcode
        System.out.println(products.getProduct(1).barcode());
        System.out.println(products.productBarcode(1));


        /* filtering, searching for price intervals, categories, product name/part of pdt name, brand
           sorting: price (low - high), (high - low), alphabetically (A-Z), (Z-A)  */
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

        //Saving categories and a list of products & quantities to file
        saveFile(categories.listOfStrings(), "categories");

        List<String> combined = listOfProductsAndStock(products, stock);
        saveFile(combined, "products & quantities");
    }

    private List<String> listOfProductsAndStock(Products products, Stock stock) {
        return products.streamOfProducts()
                .map(product -> product.toCommaSeparatedString() + "," + stock.getQuantity(product.barcode()))
                .toList();
    }

    private void saveFile(List<String> list, String name) {
        String fileName = name.concat(".csv");
        Path path = Path.of("resources", fileName);

        try {
            Files.write(path, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private Categories defaultCategories() {
        Categories categories = new Categories();

        categories.addCategory("bakery");
        categories.addCategory("dairy products");
        categories.addCategory("meat and poultry");
        categories.addCategory("frozen foods");
        categories.addCategory("ready meals");
        categories.addCategory("food cupboard");
        categories.addCategory("fruit and vegetables");
        categories.addCategory("drinks");
        categories.addCategory("sweets and ice-cream");
        categories.addCategory("garden");
        categories.addCategory("toys");
        categories.addCategory("household");
        categories.addCategory("toiletries");
        categories.addCategory("health");
        categories.addCategory("beauty");

        return categories;
    }

    private Stock generateStock(List<String[]> products, Categories categories) {
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
    
    private Products generateProducts(List<String[]> inputProducts, Categories categories) {
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