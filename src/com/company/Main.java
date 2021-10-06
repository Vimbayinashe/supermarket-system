package com.company;

import com.company.categories.Categories;
import com.company.products.Item;
import com.company.products.Stock;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        Categories categories = new Categories();
        Stock stock = new Stock();


        saveFile(categories.categoriesAsListOfStrings());

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

    private static List<Item> initialStock() {

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
 */