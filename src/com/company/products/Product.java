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
    private int quantity;

    public Product(String barcode, String name, String brand, Category category, String price, String quantity) {

        Guard.Against.InvalidBarcode(barcode);
        Guard.Against.EmptyString("name", name);
        Guard.Against.EmptyString("brand", brand);
        Guard.Against.InvalidPriceFormat(price);
        Guard.Against.InvalidInteger(quantity);
        Guard.Against.QuantityLessThanZero(quantity);

        this.barcode = Long.parseLong(barcode);
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.price = convertPrice(price);
        this.quantity = Integer.parseInt(quantity);
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

    public Double priceAsDouble() {
        return Double.parseDouble(price.toPlainString());
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

    public int quantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void decreaseQuantity(int quantity) {
        Guard.Against.StockIsZero(quantity);
        Guard.Against.InsufficientStock(quantity, this.quantity);
        this.quantity -= quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return barcode == product.barcode && quantity == product.quantity && Objects.equals(name, product.name) &&
                Objects.equals(brand, product.brand) && Objects.equals(category, product.category) &&
                Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(barcode, name, brand, category, price, quantity);
    }

    @Override
    public String toString() {
        return "Product{" +
                "barcode=" + barcode +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    public String toCommaSeparatedString() {
        return barcode + "," + name + "," + brand + "," + category.name() + "," + price + "," + quantity;
    }

    public String toCustomerViewString() {
        return name + " " + brand + " - " + price.toPlainString().replace('.', ',') + " kr";
    }

    public static void main(String[] args) {
        Category cheese = new Category("cheese");
        Product product = new Product("558895651122L", "Läsk", "Mazoe", cheese, "1.5", "55");
        System.out.println(product);
        System.out.println("Price as double: " + product.priceAsDouble());
        System.out.println(Double.parseDouble(product.price()) * 1.5);
    }
}

//NB: using BigDecimal(String) to avoid loss of accuracy similar to using floats & doubles

