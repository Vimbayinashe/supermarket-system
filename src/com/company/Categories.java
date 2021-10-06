package com.company;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Categories {

    //todo: maybe just Collection instead of List & List.of instead of ArrayList?
    private final Set<Category> categories;

    Categories() {
        categories = initialCategories();
    }

    private Set<Category> initialCategories() {

        //ArrayList -> can be accessed using index number e.g. choosing category for a new food item
        //          -> can add new category

        //todo: add more categories

        Set<Category> categories =  new HashSet<>();

        categories.add(new Category("bread"));
        categories.add(new Category("milk"));
        categories.add(new Category("meat"));
        categories.add(new Category("dry ingredients"));
        categories.add(new Category("tinned food"));
        categories.add(new Category("fruit"));
        categories.add(new Category("vegetables"));

        return categories;
    }

    //todo: is this being used?
    public List<Category> copyOfCategories() {
        return List.copyOf(categories);
    }

    public boolean doesNotContain(String query) {
        return !categories.contains(new Category(query));
    }


    @Override
    public String toString() {
        return "Categories{" +
                "categories=" + categories +
                '}';
    }

    public static void main(String[] args) {
        Categories categories = new Categories();
        System.out.println(categories);

        System.out.println(categories.doesNotContain("cake"));


    }

}

//todo:
/*
 * adding new category -> english only, convert ot lowerCase & verify for duplicates
 */
