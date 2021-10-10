package com.company.products;

public class Guard {

    public static class Against {

        public static void InvalidPriceFormat(String price) {
            try {
                if(price.contains(","))
                    Double.parseDouble(price.replace(",", "."));
                else
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

        public static void InvalidBarcode(String value) {
            try {
                Long.parseLong(value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid barcode - barcode should be a long.");
            }
        }

        public static void EmptyString(String name, String value) {
            if(value.isEmpty())
                throw new IllegalArgumentException(name + " is an empty string");
        }

        public static void InvalidInteger(String value) {
            try {
                Integer.parseInt(value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Quantity is not a valid integer");
            }
        }

        public static void QuantityLessThanZero(String quantity) {
            if (Integer.parseInt(quantity) < 0)
                throw new IllegalArgumentException("Quantity is less than zero.");
        }
    }

    public static class CheckFor {

        public static boolean ValidLong(String value) {
            try {
                Long.parseLong(value);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        public static boolean ValidInt(String value) {
            try {
                Integer.parseInt(value);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

}
