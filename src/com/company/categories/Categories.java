package com.company.categories;

import com.company.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Categories implements Command {
    private final List<Category> categories = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    public void addCategory(String name) {
        String formattedName = name.toLowerCase();
        if (categories.contains(new Category(formattedName)))
            throw new IllegalArgumentException(name + " category already exists.");
        categories.add(new Category(formattedName));
    }


    public List<Category> categories() {
        return List.copyOf(categories);
    }

    public List<String> listOfStrings() {
        return categories.stream()
                .map(Category::name)
                .collect(Collectors.toList());
    }

    public List<String []> categoriesAsArraysOfStrings() {
        return categories.stream()
                .map(category -> new String[] {category.name()})
                .collect(Collectors.toList());
    }

    public boolean doesNotContain(String query) {
        return !categories.contains(new Category(query));
    }

    public boolean contains(String query) {
        return categories.contains(new Category(query));
    }

    public void printCategoriesAsNumberedList() {
        categories.forEach(category -> System.out.println((categories.indexOf(category) + 1) + " " + category.name()));
    }

    public Category getCategory(int position) {
        return categories.get(position - 1);
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
    }

    @Override
    public void execute(String option, Object object) {
        System.out.println("Enter a new category name");
        String input = scanner.nextLine();

        try {
            addCategory(input);
            System.out.println(input + " successfully added.");
        } catch(IllegalArgumentException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}

