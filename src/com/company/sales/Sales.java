package com.company.sales;

import com.company.Command;
import com.company.Menu;
import com.company.categories.Categories;
import com.company.products.Product;
import com.company.products.Products;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Sales implements Command {
    private final Scanner scanner = new Scanner(System.in);
    private final List<Receipt> receipts = new ArrayList<>();

    private List<Receipt> receipts() {
        return List.copyOf(receipts);
    }

    public void addReceipt(Receipt receipt) {
        receipts.add(receipt);
    }


    public void printProductList(List<Product> list) {

    }

    public void saveReceipt(Receipt receipt) {
        //add to receipts

        String fileName = "receipt - " + receipt.receiptNumber();

        //save file


    }

    private BigDecimal convertPrice(Double price) {
        int scale = 2;
        String value = String.valueOf(price);
        return (new BigDecimal(value)).setScale(scale, RoundingMode.HALF_EVEN);
    }

    @Override
    public void execute(String option, Object object) {

        Products products = (Products) object;
        List<SalesItem> purchases = new ArrayList<>();
        Receipt receipt = new Receipt();
        List<Product> filteredProducts;
        List<Double> subTotal = new ArrayList<>();;
        BigDecimal totalPrice;
        BigDecimal discount = convertPrice(0.0);

        System.out.println("\nWelcome to the storefront!");

        String shoppingStatus = "";
        while (true) {

            System.out.println("Press y to continue shopping or x to confirm purchase");
            shoppingStatus = scanner.nextLine();

            if (shoppingStatus.equalsIgnoreCase("x")) {
                break;
            }
            filteredProducts = products.productsForPurchase();
            Menu.printProductsCustomerView(filteredProducts);

            while (true) {
                System.out.println("Enter a number to choose a product or 0 to choose new products view ");
                int choice = Menu.handleSelection(scanner, filteredProducts.size());
                if (choice == 0)
                    break;
                Product product = products.getProduct(choice - 1);
                System.out.print("Selected: ");
                product.toCustomerViewString();

                System.out.println("Enter how many " + product.name() + " you want to buy: ");
                int quantity = scanner.nextInt();
                scanner.nextLine();
                if(quantity < 1)
                    System.out.println("Please choose quantity of at least 1.");


                purchases.add(new SalesItem(product, quantity));
                subTotal.add(product.priceAsDouble() * quantity);

            }

            totalPrice = subTotal.stream()
                    .reduce(Double::sum)
                    .map(this::convertPrice)
                    .orElse(convertPrice(0.0));

            if (Double.parseDouble(totalPrice.toPlainString()) > 500) {
                BigDecimal newTotalPrice = Discounter.largePurchaseDiscounter().applyDiscount(totalPrice);
                discount = convertPrice(totalPrice.doubleValue() - newTotalPrice.doubleValue());
                totalPrice = newTotalPrice;
            }

            receipt = new Receipt(purchases, String.valueOf(totalPrice), String.valueOf(discount));

        }

        receipt.printReceipt();

        //todo: save receipt


    }
}
