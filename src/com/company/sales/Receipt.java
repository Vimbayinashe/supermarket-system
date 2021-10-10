package com.company.sales;

import com.company.products.Product;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public final class Receipt {
    private final long receiptNumber;
    private final List<Product> items;
    private final String totalPrice;
    private final String discount;

    public Receipt(List<Product> items, String totalPrice, String discount) {
        this.receiptNumber = Instant.now().getEpochSecond();
        this.items = items;
        this.totalPrice = totalPrice;
        this.discount = discount;
    }

    public long receiptNumber() {
        return receiptNumber;
    }

    public List<Product> items() {
        return items;
    }

    public String totalPrice() {
        return totalPrice;
    }

    public String discount() {
        return discount;
    }

    public List<String> commaSeparatedValues() {
        List<String> list = items.stream().map(Product::toCommaSeparatedString).toList();
        list.add(0, receiptNumber + "," + totalPrice + "," + discount);

        return list;
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
