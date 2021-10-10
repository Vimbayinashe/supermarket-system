package com.company.sales;

import com.company.products.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class SalesItem {
    private final long barcode;
    private final String name;
    private final String brand;
    private final String unitPrice;
    private final String subTotal;
    private final int quantity;

    public SalesItem(Product product, int quantity) {
        this.barcode = product.barcode();
        this.name = product.name();
        this.brand = product.brand();
        this.unitPrice = product.price();
        this.subTotal = subTotal(product.priceAsDouble(), quantity);
        this.quantity = quantity;
    }

    public long barcode() {
        return barcode;
    }

    public String name() {
        return name;
    }

    public String brand() {
        return brand;
    }

    public String unitPrice() {
        return unitPrice;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public int quantity() {
        return quantity;
    }

    public String toCommaSeparatedString() {
        return barcode + "," + name + "," + brand + "," + unitPrice + "," + quantity + "," + subTotal;
    }
    public String toCustomerViewString() {
        return name + " " + brand +  " " + unitPrice + "kr " + quantity + " " + subTotal + "kr";
    }

    private String subTotal(Double price, int quantity) {
        double subTotal = price * quantity;
        int scale = 2;
        return (new BigDecimal(subTotal)).setScale(scale, RoundingMode.HALF_EVEN).toPlainString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesItem salesItem = (SalesItem) o;
        return barcode == salesItem.barcode && quantity == salesItem.quantity && Objects.equals(name, salesItem.name) && Objects.equals(brand, salesItem.brand) && Objects.equals(unitPrice, salesItem.unitPrice) && Objects.equals(subTotal, salesItem.subTotal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(barcode, name, brand, unitPrice, subTotal, quantity);
    }

    @Override
    public String toString() {
        return "SalesItem{" +
                "barcode=" + barcode +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", subTotal='" + subTotal + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
