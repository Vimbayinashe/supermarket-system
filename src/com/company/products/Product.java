package com.company.products;

import com.company.categories.Category;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Product {

    private final long barcode;
    private final String name;
    private final String brand;
    private final Category category;
    private BigDecimal price;


    public Product(long barcode, String name, String brand, Category category, String price) {

        //todo: add Guard.Against  the following
        /*
            category - is an element of Categories List / printout available category options b4 sm1 adds a product
            barcode is a valid long
            Cast to double is a valid double, price.asDouble > 0, price has maximum 2 decimal places
            name, brand, category, price != null
            name != empty string ""
            quantity > 0

            Invalid argument should throw an error

            => create Guards & re-use code in price & quantity setters

            Add try catch block around new Product creation.
         */

        //todo: verify that categories contains suggested category before creating a new Category


        this.barcode = barcode;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.price = convertPrice(price);
    }

    public long barcode() {
        return barcode;
    }

    public String brand() {
        return brand;
    }

    public String name() {
        return name;
    }

    public Category category() {
        return category;
    }

    public String price() {
        return price.toPlainString();
    }

    public void price(String price) {
        Guard.Against.InvalidPriceFormat(price);
        Guard.Against.PriceLessThanZero(price);
        this.price = convertPrice(price);
    }

    private BigDecimal convertPrice(String price) {
        int scale = 2;
        return (new BigDecimal(price)).setScale(scale, RoundingMode.HALF_EVEN);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return barcode == product.barcode && Objects.equals(name, product.name) && Objects.equals(brand, product.brand)
                && Objects.equals(category, product.category) && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(barcode, name, brand, category, price);
    }

    @Override
    public String toString() {
        return "product{" +
                "barcode=" + barcode +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", category=" + category +
                ", price=" + price +
                '}';
    }


    public static void main(String[] args) {
        Category cheese = new Category("cheese");
        Product product = new Product(558895651122L, "Läsk", "Mazoe", cheese, "1.5");
        System.out.println(product);
        System.out.println(Double.parseDouble(product.price()) * 1.5);
    }
}

//NB: using BigDecimal(String) to avoid loss of accuracy similar to using floats & doubles

