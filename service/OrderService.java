package service;

import csvLoader.LoadCSV;
import pojo.*;

import java.util.ArrayList;
import java.util.List;
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
        OrderQuant orderQuant1 = new OrderQuant(quantity, new Item(productCatalog.getProduct(item)));
        order.addOrderItem(orderQuant1);
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
    public void ChooseFromMenu(Order order) {

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
            } else if (input.toUpperCase().equals(CHECKOUT.toString())) {
                return;
            }
        }
        System.exit(0);
    }


    public int itemValidator() {
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a number");
            scanner.next();
        }
        int number = scanner.nextInt();
        while (!(number > 0 && number <= 12)) {
            System.out.println("Please enter a number between 1 and 12");
            number = scanner.nextInt();
        }
        return number;
    }

    public int amountValidator() {
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a number");
            scanner.next();
        }
        int number = scanner.nextInt();
        while (!(number > 0 && number <= 100)) {
            System.out.println("Please enter a number between 1 and 100");
            number = scanner.nextInt();
        }
        return number;
    }

}
