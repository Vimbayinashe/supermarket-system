package com.company.products;

import com.company.categories.Category;

public record ProductDetails (long barcode, String name, String brand, Category category, String price, int quantity){}
