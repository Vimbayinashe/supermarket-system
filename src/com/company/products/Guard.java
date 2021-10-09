package com.company.products;

public class Guard {

    public static class Against {

        public static void InvalidPriceFormat(String price) {
            try {
                if(price.contains(","))
                    Double.parseDouble(price.replace(",", "."));
                Double.parseDouble(price);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(price + " is not a valid price format.");
            }
        }

        public static void PriceLessThanZero(String price) {
            if (Double.parseDouble(price) < 0.0)
                throw new IllegalArgumentException("product's price cannot be set to a value less than zero.");
        }

        public static void InsufficientStock(int quantity, int currentQuantity) {
            if (currentQuantity < quantity)
                throw new IllegalArgumentException("Amount in stock is less than the requested quantity.");
        }

        public static void StockIsZero(int currentQuantity) {
            if (currentQuantity == 0)
                throw new IllegalArgumentException("Product is currently out of stock");
        }

    }

}
