package com.company;

import com.company.categories.Categories;
import com.company.categories.Category;
import com.company.products.DefaultProducts;
import com.company.products.Product;
import com.company.products.Products;
import com.company.stock.Stock;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Categories categories = new Categories();
        Products products = new Products();
        Stock stock = new Stock();
        DefaultProducts defaultProducts = new DefaultProducts();


        //try to read from file
            // check for all 3 separate files -> add arg that shows what of categories, products & stock needsdefaults?
            //if files present -> update categories (!first), stock and products

            //else fill in details from initial default values
            categories = defaultCategories();
            try {       //does this interrupt the result of both methods if one of them has a problem
                products = defaultProducts(defaultProducts.products(), categories);
                stock = defaultStock(defaultProducts.products(), categories);
            } catch (Exception e) {
                e.printStackTrace();
            }


        products.printProductsAsNumberedList();



        /*
        *   create a new List<String []> populated by a stream
        *       products.stream.map(item -> new String[](a, b , c, d.toString, e. stock.get(a) )).toList
        *       Save to csv file.
        *
        *   test: saving csv file from a List<String []>
         */

        saveFile(categories.categoriesAsListOfStrings());
        //saveFile(List.of("five,six,seven", "eight,nine,zero", "ten,twelve,eleven")); //saving 3 rows x 3 columns

    }

    private static Categories defaultCategories() {
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

    private static Stock defaultStock(List<String[]> products, Categories categories) {
        Stock stock = new Stock();

        products.forEach(product -> {

            if(categories.doesNotContain(product[3]))
                throw new IllegalArgumentException("Invalid product category. Barcode: " + product[0]);
            //returna Optional -> som innehåller en kategorie eller empty

            try {
                stock.addProduct(Long.parseLong(product[0]), Integer.parseInt(product[5]));
            }catch (NumberFormatException e) {
                e.printStackTrace();
                //throw new NumberFormatException("Invalid barcode or quantity on product " + product[0]);
            }
        });
        return stock;
    }

    public static Products defaultProducts(List<String[]> defaultProducts, Categories categories) {
        Products products = new Products();

        //change to a stream?
        defaultProducts.forEach(product -> {
            if(categories.doesNotContain(product[3]))
                throw new IllegalArgumentException("Invalid product category. Barcode: " + product[0]);

            try {
                products.addProduct(
                    new Product(Long.parseLong(product[0]), product[1], product[2], new Category(product[3]), product[4])
                );
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Invalid barcode: " + product[0]);
            }
        });
        return products;
    }


    public static void saveFile (List<String> list) {
        String homeFolder = System.getProperty("user.home");
        Path path = Path.of(homeFolder, "store", "categories-1.csv");

        //trying to make new file
        //Path path = createPath(homeFolder, "store", "categories-1.csv");


        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //try (Stream<String> file = list.stream() ){       //there must be sth streamable here
        try{
            Files.write(path, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //method is supposed to increase file suffix by 1 letter : currently not working
    private static Path createPath(String homeFolder,String folder, String fileName) {
        Path path = Path.of(homeFolder, folder, fileName);


        //testa använda datum+klockslagen för automatiskt sparning (nytt filnamn) men användaren väljer att spara kan
        // man skriva över den gamla

        if(Files.exists(path)) {
            String currentName = String.valueOf(path.getFileName());

            int index = currentName.indexOf("-");
            int version = Integer.parseInt(String.valueOf(currentName.charAt(index + 1)));

            String newName = "categories-" + (version + 1) + ".csv";

            System.out.println(version);

            return Path.of(homeFolder, folder, newName);
        }
        return path;
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