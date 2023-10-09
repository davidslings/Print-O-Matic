package service;

import pojo.Order;
import pojo.User;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class CheckoutService {
    private final Scanner scanner;
    private final OrderService orderService;
    private final UserService userService;

    // Constructor for CheckoutService
    public CheckoutService() {
        // Initialize the scanner and Service classes
        this.scanner = new Scanner(System.in);
        this.orderService = new OrderService();
        this.userService = new UserService();
    }

    // Main method for handling the checkout process
    public void checkoutService(Order order) {
        // Set the order time to the current date and time
        order.setOrderTime(getDateTime());

        // Calculate and set the pickup time for the order
        LocalDateTime pickupTime = PickupTimeCalculator.CalculatePickupTime(order);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        order.setPickupTime(pickupTime.format(formatter));

        // Print the order details
        printOrder(order.getUser(), order);

        // Prompt the user to edit their user information
        System.out.println("Do you want to edit the user information? y/n");
        String input = scanner.nextLine();
        if (input.equals("y")) {
            // Update user information if desired
            order.setUser(userService.createUser(order.getUser()));
            // Print the updated order details
            printOrder(order.getUser(), order);
        }

        // Prompt the user to edit their order
        System.out.println("Do you want to edit your order? y/n");
        String input2 = scanner.nextLine();
        if (input2.equals("y")) {
            orderService.ChooseFromMenu(order);
            pickupTime = PickupTimeCalculator.CalculatePickupTime(order);
            order.setPickupTime(pickupTime.format(formatter));
        }

        // Finalize the order and export it as JSON
        printOrder(order.getUser(), order);
        System.out.println("You are now ready to order. Press any button to continue. Your order will be exported as JSON.");
        scanner.nextLine();
        JsonService.fileWriter(order);
        System.exit(0);
    }


    // Method to print order details
    private void printOrder(User user, Order order) {
        System.out.println("User ID: " + user.getUserID() + "\nName: " + user.getFirstName() + " " + user.getLastName() + "\nStreet and house number: " + user.getAddressLine1() + "\nPostcode and city: " + user.getAddressLine2() + "\nEmail: " + user.getEmail() + "\nPhone number: " + user.getPhoneNumber());
        System.out.println("----------------------------------------------------------------");
        System.out.println("Order information: ");
        System.out.println("Order time: " + order.getOrderTime());

        printBasket(order);

        System.out.println("Earliest pickup time (depending on availability of the printer): " + order.getPickupTime());
        System.out.println("------------------------------------------------------------------");

        double totalCost = 0;
        for (int i = 0; i < order.getOrderItems().size(); i++) {
            totalCost += order.getOrderItems().get(i).getItem().getPrice() * order.getOrderItems().get(i).getQuantity();
        }
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        System.out.println("Total cost: " + (formatter.format(totalCost)) + "\n");
    }

    // Method to get the current date and time as a formatted string
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm");
        Date date = new Date();
        return dateFormat.format(date);
    }



    public void printBasket(Order order) {
        System.out.println("ID  |  Product name               | Amount      ");
        order.getOrderItems().forEach(o -> {
            String formattedId = String.format("%-6s", "(" + o.getItem().getProductID() + ")");
            String formattedName = String.format("%-30s", o.getItem().getProductName());
            String formattedQuantity = String.format("%-10s", o.getQuantity());
            System.out.println(formattedId + " " + formattedName + formattedQuantity);
        });
    }


}
