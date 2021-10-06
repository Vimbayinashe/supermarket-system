package com.company.categories;

import java.util.ArrayList;
import java.util.List;

public class Categories {

    //todo: maybe just Collection instead of List & List.of instead of ArrayList?
    private final List<Category> categories;

    Categories() {
        categories = initialCategories();
    }

    private List<Category> initialCategories() {


        //todo: add more categories

        List<Category> categories =  new ArrayList<>();

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

//        categories.printCategoriesAsNumberedList();
        System.out.println(categories.doesNotContain("cake"));

    }

}

//todo:
/*  public void printCategoriesAsNumberedList() {
        categories.forEach(category -> System.out.println((categories.indexOf(category) + 1) + " " + category.name()));
    }
 * adding new category -> english only, convert ot lowerCase & verify for duplicates
 */
