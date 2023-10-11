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

    public OrderService() {
        this.scanner = new Scanner(System.in);
    }

    // Method to add an order item to an order
    public void addOrderItem(Integer item, Integer quantity, Order order) {
        ProductCatalog productCatalog = new ProductCatalog(LoadCSV.readItemsFromCSV());
        OrderQuant orderQuant = new OrderQuant(quantity, new Item(productCatalog.getProduct(item)));
        order.addOrderItem(orderQuant);
    }

    // Method to remove an order item from an order
    public void removeOrderItem(Integer productID, Integer quantity, Order order) throws ArrayIndexOutOfBoundsException {

        OrderQuant orderQuant = order.getOrderItem(productID);
        order.getOrderItem(productID).setQuantity(orderQuant.getQuantity() - quantity);
        if (orderQuant.getQuantity() <= 0) {
            order.removeOrderItem(orderQuant);
        } else {
            order.setOrderItem(orderQuant);
        }
    }


    // Method to choose items from a menu and build an order
    public void chooseFromMenu(Order order) {

        int itemChoice;
        int quantity;
        String input = "";
        System.out.println("Type add to add an item, remove to remove an item or reduce the quantity and exit to exit the shop.\nType checkout to review your order before confirming it.");
        while (!input.equals("exit")) {
            System.out.println("add | remove | exit | checkout");
            input = scanner.nextLine();
            if (input.toUpperCase().equals(ADD.toString())) {
                System.out.println("Please pick an item");
                itemChoice = itemValidator();
                System.out.println("Please pick the amount");
                quantity = amountValidator();
                scanner.nextLine();
                addOrderItem(itemChoice, quantity, order);
                printBasket(order);
            } else if (input.toUpperCase().equals(REMOVE.toString())) {
                if (order.getOrderItems().isEmpty()) {
                    System.out.println("You have not chosen any items.");
                    continue;
                }
                System.out.println("Please pick an item");

                itemChoice = itemValidator();
                while (order.getOrderItem(itemChoice) == null) {
                    System.out.println("Your shopping basket does not contain this item. Please pick another item.");
                    itemChoice = itemValidator();
                }
                System.out.println("Please pick the amount you want to remove");
                quantity = amountValidator();
                scanner.nextLine();
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
                return;
            }
        }
        System.exit(0);
    }


    public int itemValidator() {
        int number = hasNextInt();
        while (!(number > 0 && number <= 12)) {
            System.out.println("Please enter a number between 1 and 12");
            number = scanner.nextInt();
        }
        return number;
    }

    public int amountValidator() {
        int number = hasNextInt();
        while (!(number > 0 && number <= 100)) {
            System.out.println("Please enter a number between 1 and 100");
            number = scanner.nextInt();
        }
        return number;
    }
    public int hasNextInt() {
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a number");
            scanner.next();
        }
        return scanner.nextInt();
    }

    public void printBasket(Order order) {
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
}
