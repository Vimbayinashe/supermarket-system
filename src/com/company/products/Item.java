package com.company.products;

import com.company.categories.Category;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Item {

    private final long barcode;
    private final String name;
    private final String brand;
    private final Category category;
    private BigDecimal price;
    private int quantity;


    public Item(long barcode, String name, String brand, String category, String price, int quantity) {

        //todo: add Guard.Against  the following
        /*
            category - is an element of Categories List / printout available category options b4 sm1 adds a product
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
        this.category = new Category(category);
        this.price = convertPrice(price);
        this.quantity = quantity;


        //todo: Is it necessary to store price as BigDecimal? (as according to article)

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

    public int quantity() {
        return quantity;
    }

    public void price(String price) {
        try {
            if (Integer.parseInt(price) < 0)
                throw new IllegalArgumentException("Item's price cannot be set to a value less than zero.");
            this.price = convertPrice(price);
        } catch (Exception e) {
            throw new IllegalArgumentException("Price could not be converted to a valid number.");
        }
    }

    public void increaseQuantity(int value) {
        this.quantity += value;
    }

    public void decreaseQuantity(int value) {
        if (this.quantity == 0 || value > this.quantity)
            throw new IllegalArgumentException("There is insufficient stock for this transaction");
        this.quantity -= value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return barcode == item.barcode && quantity == item.quantity && Objects.equals(name, item.name)
                && Objects.equals(brand, item.brand) && Objects.equals(category, item.category)
                && Objects.equals(price, item.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(barcode, name, brand, category, price, quantity);
    }

    @Override
    public String toString() {
        return "Item {" +
                "barcode=" + barcode +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    private BigDecimal convertPrice(String price) {
        int scale = 2;
        return (new BigDecimal(price)).setScale(scale, RoundingMode.HALF_EVEN);
    }


    public static void main(String[] args) {
        Item product = new Item(558895651122L, "Läsk", "Mazoe", "cheese", "1.5", 50);
        System.out.println(product);
        System.out.println(Double.parseDouble(product.price()) * 1.5);
    }
}

//NB: use BigDecimal(String) to avoid loss of accuracy similar to using floats & doubles

