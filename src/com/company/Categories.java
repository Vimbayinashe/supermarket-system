package com.company;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Categories {

    //todo: maybe just Collection instead of List & List.of instead of ArrayList?
    private List<String> categories;

    Categories() {
        categories = initialCategories();
    }

    private List<String> initialCategories() {

        //ArrayList -> can be accessed using index number e.g. choosing category for a new food item
        //          -> can add new category

        //todo: add more categories
        List<String> categories = new ArrayList<>();

        categories.add("bread");
        categories.add("milk");
        categories.add("meat");
        categories.add("dry ingredients");
        categories.add("tinned food");
        categories.add("fruit");
        categories.add("vegetables");

        return categories;
    }

    public List<String> categories() {
        return List.copyOf(categories);
//        return Collections.unmodifiableList(categories);
    }

    //Overkill: List already has contains() method
//    public boolean contains(String query) {
//        return categories.contains(query);
//    }

}

//todo:
/*
 * adding new category -> english only, convert ot lowerCase & verify for duplicates
 */
