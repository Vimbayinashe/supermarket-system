package com.company.products;

import java.util.List;

public class DefaultProducts {
    List<String[]> products;

    public DefaultProducts() {

        products = List.of(
                new String[]{"7810255825944", "apple", "ICA Basic", "fruit and vegetables", "2", "200"},
                new String[]{"7318693555824", "Red Grapes", "ICA", "fruit and vegetables", "20", "120"},
                new String[]{"5588944871150", "Honeycrunch apple", "ICA Premium", "fruit and vegetables", "3.5", "30"},
                new String[]{"5410188030044", "Apple juice", "Tropicana", "drinks", "15", "75"},
                new String[]{"7310611278006", "Vitamin C tablets", "Active Care", "health", "19.50", "100"},
                new String[]{"7310614394055", "Spring Rain Shower Cream", "Family Fresh", "toiletries", "24", "250"},
                new String[]{"7340083489303", "Coconut hand soap", "Mevolution", "toiletries", "17.90", "60"},
                new String[]{"7340083455070", "Roasted onion flakes", "Eldorado", "food cupboard", "8.99", "50"},
                new String[]{"7350056793023", "Greek yoghurt", "Larsa", "dairy products", "32", "25"},
                new String[]{"7318690157298", "Carrot soup", "ICA", "ready meals", "29.90", "25"},
                new String[]{"3392590203204", "Pizza dough", "Pop Bakery!", "ready meals", "28.90", "25"},
                new String[]{"8720181127762", "Laundry detergent", "Via", "household", "42", "170"},
                new String[]{"7340083470943", "Dishwashing liquid", "Fixa", "household", "15.90", "120"},
                new String[]{"7622300247942", "Hot chocolate", "O'boy", "food cupboard", "27.5", "80"},
                new String[]{"3228881076649", "Strawberry tea", "Lipton", "food cupboard", "15.6", "75"},
                new String[]{"8715800002315", "Salt", "Jozo", "food cupboard", "11.90", "250"},
                new String[]{"7340083427312", "Milk 3.0%", "Garant", "dairy products", "10.90", "50"},
                new String[]{"7310960016502", "Natural yoghurt", "Arla", "dairy products", "14.95", "50"},
                new String[]{"7310290006693", "Ice-cream", "BigPack GB-Glace", "sweets and ice-cream", "19.90","50"}
        );
    }

    public List<String[]> products() {
        return products;
    }
}
