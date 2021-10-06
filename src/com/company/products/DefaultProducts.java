package com.company.products;

import com.company.categories.Category;

import java.util.ArrayList;
import java.util.List;

public class DefaultProducts {
    List<String[]> products;

    public DefaultProducts() {

        products = List.of(
                new String[]{"5588944", "Honeycrunch apple", "ICA Premium", "fruit and vegetables", "3.5", "500"},
                new String[]{"55825944", "apple", "ICA Basic", "fruit and vegetables", "2", "5000"},
                new String[]{"7415825944", "grapes", "ICA", "fruit and vegetables", "35", "250"}

        );

    }
}
