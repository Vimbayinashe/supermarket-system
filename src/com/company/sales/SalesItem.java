package com.company.sales;

import com.company.products.Product;

import java.util.Objects;

public final class SalesItem {
    private final long barcode;
    private final String name;
    private final String brand;
    private final String unitPrice;
    private final int quantity;

    public SalesItem(Product product, int quantity) {
        this.barcode = product.barcode();
        this.name = product.name();
        this.brand = product.brand();
        this.unitPrice = product.price();
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

    public int quantity() {
        return quantity;
    }

    public String toCommaSeparatedString() {
        return barcode + "," + name + "," + brand + "," + unitPrice + "," + quantity;
    }
    public String toCustomerViewString() {
        return name + " " + brand +  " " + unitPrice + " " + quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (SalesItem) obj;
        return this.barcode == that.barcode &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.brand, that.brand) &&
                Objects.equals(this.unitPrice, that.unitPrice) &&
                this.quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(barcode, name, brand, unitPrice, quantity);
    }

    @Override
    public String toString() {
        return "SalesItem[" +
                "barcode=" + barcode + ", " +
                "name=" + name + ", " +
                "brand=" + brand + ", " +
                "unitPrice=" + unitPrice + ", " +
                "quantity=" + quantity + ']';
    }


}
