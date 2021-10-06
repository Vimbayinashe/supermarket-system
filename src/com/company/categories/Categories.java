package com.company.categories;

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


        //todo: add more categories

        Set<Category> categories =  new HashSet<>();

        categories.add(new Category("bakery"));
        categories.add(new Category("dairy products"));
        categories.add(new Category("meat and poultry"));
        categories.add(new Category("frozen foods"));
        categories.add(new Category("food cupboard"));
        categories.add(new Category("fruit and vegetables"));
        categories.add(new Category("drinks"));
        categories.add(new Category("sweets and ice-cream"));
        categories.add(new Category("garden"));
        categories.add(new Category("toys"));
        categories.add(new Category("household"));
        categories.add(new Category("health"));
        categories.add(new Category("beauty"));

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
