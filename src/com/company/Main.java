package com.company;

import com.company.categories.Categories;
import com.company.products.Products;
import com.company.products.Stock;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    static Categories categories = new Categories();
    static Products products = new Products();
    static Stock stock = new Stock();

    public static void main(String[] args) {



        //try to read from file
            //if files present -> update categories (!first), stock and products

            //else fill in details from initial default values
        defaultCategoriesProductsAndStock();






        saveFile(categories.categoriesAsListOfStrings());

    }

    public static void defaultCategoriesProductsAndStock() {
        //default categories
        categories.defaultCategories();
        products.initialStock();

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

        if(Files.exists(path)) {
            String currentName = String.valueOf(path.getFileName());
            int index = currentName.indexOf("-");
            int version = Integer.parseInt(String.valueOf(currentName.charAt(index + 1)));
            String newName = "categories-" + (version + 1) + ".csv";

            System.out.println(version);

            path = Path.of(homeFolder, folder, newName);
        }
        return path;
    }

//    private static List<Item> initialStock() {
//
//    }
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