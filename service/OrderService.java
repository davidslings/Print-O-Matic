package service;

import csvLoader.LoadCSV;
import pojo.Item;
import pojo.Order;
import pojo.OrderQuant;
import pojo.ProductCatalog;

import java.util.Scanner;

import static Constants.MenuChoices.*;

public class OrderService {
    private Scanner scanner;
    private final ProductCatalog PRODUCT_CATALOG = new ProductCatalog(LoadCSV.readItemsFromCSV());
    public OrderService() {
        this.scanner = new Scanner(System.in);
    }

    // Method to add an order item to an order
    public void addOrderItem(Integer productID, Integer quantity, Order order) {
        //First it needs to read the product catalog with all the items.

        ProductCatalog productCatalog = new ProductCatalog(LoadCSV.readItemsFromCSV());
        // The if-statement occurs when an item is selected for the first time, i.e. when the item is not in the basket yet.
        if (!order.getOrderItems().contains(order.getOrderItem(productID))) {
            OrderQuant orderQuant = new OrderQuant(quantity, new Item(productCatalog.getProduct(productID)));
            order.addOrderItem(orderQuant);
        } else { // else it will set the new amount of the item that is already in the basket.
            int oldAmount = order.getOrderItem(productID).getQuantity();
            order.getOrderItem(productID).setQuantity(oldAmount + quantity);
        }
    }

    // Method to remove an order item from an order
    public void removeOrderItem(Integer productID, Integer quantity, Order order) throws ArrayIndexOutOfBoundsException {
        OrderQuant orderQuant = order.getOrderItem(productID);
        orderQuant.setQuantity(orderQuant.getQuantity() - quantity);
        if (orderQuant.getQuantity() <= 0) { // will remove the item from the basket if the amount reaches 0 or less.
            order.removeOrderItem(orderQuant);
        } else { // this replaces the old orderQuant object with a new one that has an updated amount value in the array list.
            order.setOrderItem(orderQuant);
        }
    }


    // Method to choose items from a menu and build an order
    public void chooseFromMenu(Order order) {
    // This function is located in OrderService so that it can be called in MenuService as well as in CheckoutService.
        int itemChoice;
        int quantity;
        String input = "";
        System.out.println("Type\n- 'add' to add an item\n- 'remove' to remove an item or reduce the quantity\n- 'exit' to exit the shop.\n- 'checkout' to review your order before confirming it.\n- 'catalog' to show the product catalog\n- 'basket' to show your shopping basket");
        while (!input.equals("exit")) { //only when typing exit or checkout will the loop end.
            System.out.println("add | remove | exit | checkout | catalog | basket");
            input = scanner.nextLine();
            if (input.toUpperCase().equals(ADD.toString())) { //chooses an enum constant

                System.out.println("Please choose a product ID number to pick an item (1-12)");
                itemChoice = itemValidator();
                System.out.println("Please choose the amount (1-100)");
                quantity = amountValidator();
                addOrderItem(itemChoice, quantity, order);
                printBasket(order);
            } else if (input.toUpperCase().equals(REMOVE.toString())) {
                if (order.getOrderItems().isEmpty()) {
                    System.out.println("You have not chosen any items.");
                    continue;
                }
                printBasket(order);
                System.out.println("Please pick an item");

                itemChoice = itemValidator();
                while (order.getOrderItem(itemChoice) == null) {
                    System.out.println("Your shopping basket does not contain this item. Please pick another item.");
                    itemChoice = itemValidator();
                }
                System.out.println("Please pick the amount you want to remove");
                quantity = amountValidator();
                try {
                    removeOrderItem(itemChoice, quantity, order);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Invalid input, please choose one of the options");
                }
                printBasket(order);
            } else if (input.toUpperCase().equals(CHECKOUT.toString())) {
                if (order.getOrderItems().isEmpty()) {
                    System.out.println("Your basket is empty.");
                    continue;
                }
                return; // will return always to the superior layer where it will be sent to checkoutService.
            } else if (input.toUpperCase().equals(CATALOG.toString())) {
                printCatalog();
            } else if (input.toUpperCase().equals(BASKET.toString())) {
                printBasket(order);
            }
        }
        System.exit(0); //will ensure exiting when breaking the while loop (only possible when entering 'exit')
    }


    public int itemValidator() { //function that loops when the user does not select one of the 12 product IDs
        while (true) {
            String intInput = scanner.nextLine();

            if (!isInteger(intInput)) {
                System.out.println("Invalid input. Please enter a valid number.");
            } else {
                int number = Integer.parseInt(intInput);

                if (number < 1 || number > PRODUCT_CATALOG.getProductCatalog().size()) {
                    System.out.println("Number is out of range (1-12). Please try again.");
                } else {
                    return number; // Return the valid number
                }
            }
        }
    }

    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        }
            catch (Exception e) {
            return false;
        }
    }
    private int amountValidator() { // function that loops when the user does not select a number between 1 and 100
        while (true) {
            String intInput = scanner.nextLine();

            if (!isInteger(intInput)) {
                System.out.println("Invalid input. Please enter a valid number.");
            } else {
                int number = Integer.parseInt(intInput);

                if (number < 1 || number > 100) {
                    System.out.println("Number is out of range (1-100). Please try again.");
                } else {
                    return number; // Return the valid number
                }
            }
        }
    }



    public void printBasket(Order order) { // string formatting to display the basket, used in various places in the app.
        if (order.getOrderItems().isEmpty()) {
            System.out.println("Your basket is empty.");
            System.out.println("Please add something to your basket before attempting to view it");
            return;
        }
        System.out.println("ID  |  Product name               | Amount    | Price  ");
        order.getOrderItems().forEach(o -> {
            String formattedId = String.format("%-6s", "(" + o.getItem().getProductID() + ")");
            String formattedName = String.format("%-29s", o.getItem().getProductName());
            String formattedQuantity = String.format("%-12s", o.getQuantity());
            double priceItem = o.getItem().getPrice()*o.getQuantity();
            String formattedPrice = String.format("%-11s", priceItem);
            System.out.println(formattedId + " " + formattedName + formattedQuantity + formattedPrice);
        });
    }

    // Method to print the product catalog
    public void printCatalog() {

        System.out.println("ID  |  Product name               | Price (€) | Production time (hours)");
        System.out.println("-------------------------------------------------------------");

        PRODUCT_CATALOG.getProductCatalog().forEach((id, product) -> {
            // Use String formatting to align the columns
            String formattedId = String.format("%-4s", "(" + product.getProductID() + ")");
            String formattedName = String.format("%-28s", product.getProductName());
            String formattedPrice = String.format("%-10s", product.getPrice());
            String formattedProductionTime = String.format("%-21s", product.getProductionTime());

            // Print the formatted data as a row in the table
            System.out.println(formattedId + "| " + formattedName + "| " + formattedPrice + "| " + formattedProductionTime);
        });

        System.out.println("----------------------------------------------------------\n");
    }
}
