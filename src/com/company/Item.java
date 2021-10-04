package com.company;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Item  {
    private static int count;

    private final int id;
    private final String name;
    //    private final String brand; //todo: is brand necessary?
    private final String category;
    private final BigDecimal price;
    private final int quantity;


    public Item(String name, String category, String price, int quantity) {

        //todo: add Guard.Against  the following
        /*
            category - is an element of Categories List / printout available category options b4 sm1 adds a product
            Cast to double is a valid double, price.asDouble > 0, price has maximum 2 decimal places
            name, brand, category, price != null
            name != empty string ""
            quantity > 0

            Invalid argument should throw an error

            Add try catch block around new Product creation.
         */

        int scale = 2;

        this.id = ++count;
        this.name = name;
        this.category = category;
        this.price = (new BigDecimal(price)).setScale(scale, RoundingMode.HALF_EVEN);
        this.quantity = quantity;

        //todo: Is it necessary to store price as BigDecimal? (as according to article)
        // input String datatype for price -> convert to BigDecimal.

    }

    public int id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String category() {
        return category;
    }

    public String price() {
        return price.toPlainString();
    }

    public int quantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id && quantity == item.quantity && Objects.equals(name, item.name) &&
                Objects.equals(category, item.category) && Objects.equals(price, item.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, price, quantity);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }


    public static void main(String[] args) {
        Product product = new Product("Läsk", "cheese", "-1");
        System.out.println(product);
        System.out.println(product.hashCode());
    }
}

//NB: use BigDecimal(String) to avoid loss of accuracy similar to using floats & doubles

