package com.company.sales;

import com.company.products.Product;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Receipt {
    private final String receiptNumber;
    private final List<SalesItem> items;
    private final String totalPrice;
    private final String discount;

    public Receipt() {
        this.receiptNumber = String.valueOf(Instant.now().getEpochSecond());;
        this.items = new ArrayList<>();
        this.totalPrice = "0.0";
        this.discount = "0%";
    }

    public Receipt(List<SalesItem> items, String totalPrice, String discount) {
        this.receiptNumber = String.valueOf(Instant.now().getEpochSecond());
        this.items = items;
        this.totalPrice = totalPrice;
        this.discount = discount;
    }

    public String receiptNumber() {
        return receiptNumber;
    }

    public List<SalesItem> items() {
        return items;
    }

    public String totalPrice() {
        return totalPrice;
    }

    public String discount() {
        return discount;
    }

    public List<String> commaSeparatedValues() {
        List<String> list = new ArrayList<>();
        list.add(0, receiptNumber + "," + totalPrice + "," + discount);

        items.stream()
                .map(SalesItem::toCommaSeparatedString)
                .forEach(list::add);

        return list;
    }

    public void printReceipt() {
        System.out.println("Receipt number: " + receiptNumber);
        System.out.println("Product name, brand, unit price, quantity");
        items.stream()
                .map(SalesItem::toCustomerViewString)
                .forEach(System.out::println);
        System.out.println("Total price: " + totalPrice + " kr");

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Receipt) obj;
        return Objects.equals(this.receiptNumber, that.receiptNumber) &&
                Objects.equals(this.items, that.items) &&
                Objects.equals(this.totalPrice, that.totalPrice) &&
                Objects.equals(this.discount, that.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receiptNumber, items, totalPrice, discount);
    }

    @Override
    public String toString() {
        return "Receipt[" +
                "receiptNumber=" + receiptNumber + ", " +
                "items=" + items + ", " +
                "totalPrice=" + totalPrice + ", " +
                "discount=" + discount + ']';
    }

}
