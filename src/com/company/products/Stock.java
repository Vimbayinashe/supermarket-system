package com.company.products;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Stock {
    Map<Long, Integer> stockList = new HashMap<>();

    public boolean contains(Long barcode) {
        return stockList.containsKey(barcode);
    }

    public void addProduct(Long barcode, Integer quantity) {
        if (stockList.containsKey(barcode))
            throw new IllegalArgumentException("Product already exists in stock");
        stockList.put(barcode, quantity);
    }

    public int getQuantity(Long barcode) {
        return stockList.get(barcode);
    }

    public void increaseQuantity(Long barcode, int quantity) {
        int newQuantity = stockList.get(barcode) + quantity;
        stockList.replace(barcode, newQuantity);
    }

    public void decreaseQuantity(Long barcode, int quantity) {
        if (stockList.get(barcode) == 0 || stockList.get(barcode) < quantity)
            throw new IllegalArgumentException("Amount in stock is less than the requested quantity.");

        int newQuantity = stockList.get(barcode) - quantity;
        stockList.replace(barcode, newQuantity);
    }

    public Set<Map.Entry<Long, Integer>> listOfStock() {
        return stockList.entrySet();
    }



    public static void main(String[] args) {
        Stock stock = new Stock();

        stock.addProduct(584223L, 500);
        stock.addProduct(74963L, 700);

        stock.listOfStock().forEach(System.out::println);

    }
}
