package com.company.categories;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Categories {

    //todo: maybe just Collection instead of List & List.of instead of ArrayList?
    private final List<Category> categories;

    public Categories() {
        categories = initialCategories();
    }

    private List<Category> initialCategories() {

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

    public void addCategory(String name) {
        String formattedName = name.toLowerCase();
        if (categories.contains(new Category(formattedName)))
            throw new IllegalArgumentException(name + " category already exists.");
        categories.add(new Category(formattedName));
    }


    public List<Category> categories() {
        return List.copyOf(categories);
    }

    public List<String> categoryArrays() {
        return categories.stream()
                .map(category -> category.name())
                .collect(Collectors.toList());
    }

    public boolean doesNotContain(String query) {
        return !categories.contains(new Category(query));
    }

    public void printCategoriesAsNumberedList() {
        categories.forEach(category -> System.out.println((categories.indexOf(category) + 1) + " " + category.name()));
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

        categories.printCategoriesAsNumberedList();
        System.out.println(categories.doesNotContain("cake"));

        categories.addCategory("MEATS");
        categories.printCategoriesAsNumberedList();
        //categories.addCategory("toys");

    }

}

//todo:
/*
 * adding new category -> english only, check current list first <= request user
 */
