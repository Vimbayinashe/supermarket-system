package com.company;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class Product {
    private final String name;
    private final String brand;
    private final String category;
    private final BigDecimal price;
    private final int scale;

    public Product(String name, String brand, String category, String price) {

        //todo: add Guard.Against  the following
        /*
            category - is an element of Categories List / printout available category options b4 sm1 adds a product
            Cast to double is a valid double, price.asDouble > 0, price has maximum 2 decimal places
            name, brand, category, price != null
            name != empty string ""
         */

        this.scale = 2;

        this.name = name;
        this.brand = brand;
        this.category = category;
        this.price = (new BigDecimal(price)).setScale(scale, RoundingMode.HALF_EVEN);

        //todo: Is it necessary to store price as BigDecimal? (as according to article)
        // input String datatype for price -> convert to BigDecimal.

    }

    public String name() {
        return name;
    }

    public String brand() {
        return brand;
    }

    public String category() {
        return category;
    }

    public String price() {
        return price.toPlainString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Product) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.brand, that.brand) &&
                Objects.equals(this.category, that.category) &&
                Objects.equals(this.price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, brand, category, price);
    }

    @Override
    public String toString() {
        return "Product[" +
                "name=" + name + ", " +
                "brand=" + brand + ", " +
                "category=" + category + ", " +
                "price=" + price + ']';
    }

    public static void main(String[] args) {
        Product product = new Product("Läsk", "Mazoe", "chees", "-1");
        System.out.println(product);
    }
}

//NB: use BigDecimal(String) to avoid loss of accuracy similar to using floats & doubles
