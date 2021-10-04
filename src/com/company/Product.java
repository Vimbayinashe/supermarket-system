package com.company;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class Product {
    private final int id;
    private final String name;
    private final String brand;
    private final String category;
    private final BigDecimal price;
    private static int count;

    public Product(String name, String brand, String category, String price) {

        //todo: add Guard.Against  the following
        /*
            category - is an element of Categories List / printout available category options b4 sm1 adds a product
            Cast to double is a valid double, price.asDouble > 0, price has maximum 2 decimal places
            name, brand, category, price != null
            name != empty string ""

            Invalid argument should throw an error

            Add try catch block around new Product creation.
         */

        int scale = 2;

        this.id = ++count;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.price = (new BigDecimal(price)).setScale(scale, RoundingMode.HALF_EVEN);

        //todo: Is it necessary to store price as BigDecimal? (as according to article)
        // input String datatype for price -> convert to BigDecimal.

    }

    public String id() {
        return String.valueOf(id);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Objects.equals(name, product.name) && Objects.equals(brand, product.brand) &&
                Objects.equals(category, product.category) && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, brand, category, price);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                '}';
    }

    public static void main(String[] args) {
        Product product = new Product("Läsk", "Mazoe", "chees", "-1");
        System.out.println(product);
    }
}

//NB: use BigDecimal(String) to avoid loss of accuracy similar to using floats & doubles
