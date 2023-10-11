package service;

import csvLoader.LoadCSV;
import pojo.Order;
import pojo.ProductCatalog;
import pojo.User;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MenuService {
    private final UserService userService;
    private final OrderService orderService;
    private final CheckoutService checkoutService;
    private final Scanner scanner;


    // Constructor for MenuService
    public MenuService() {
        // Initialize various services and components
        this.userService = new UserService();
        this.orderService = new OrderService();
        this.checkoutService = new CheckoutService();
        this.scanner = new Scanner(System.in);
    }

    // Method to display an introduction message
    public void displayIntroduction() throws FileNotFoundException {
        System.out.println("Welcome to Dunder Mifflin, the automatic PrintStore designed to accommodate you in all your needs.");
        System.out.println("Have you been here before? y/n");

        if (scanner.nextLine().equals("y")) { // Checks for existing user
            System.out.println("Please insert your email address");
            String email = scanner.nextLine();

            // This while loop keeps asking for an email address, until a matching one is found or the user enters 'exit'
            while (JsonService.readOldOrder(email) == null) {
                System.out.println("That email address is not recognized.");
                System.out.println("Try again or type 'exit' to go back to the main menu and create a new user account.");
                email = scanner.nextLine();
                System.out.println("Loading files...");

                // Upon entering 'exit', the user will create a new user account and follow the normal user flow
                if (email.equals("exit")) {
                    Order order = new Order(userService.createUser(new User()));
                    userFlow(order);
                    System.exit(0); // will need to exit here manually, otherwise will do the user flow twice.
                }
            }
            // Here, the old orders associated with the user account are added to an array list to be printed for the user to see.
            List<Order> oldOrders = JsonService.readOldOrder(email);
            //this will get the first item in the list, but because all items (should) have the same user, it does not matter.
            System.out.println("Welcome back, " + oldOrders.get(0).getUser().getFirstName());
            System.out.println("Would you like to review your old orders? y/n");

            // prints order(s) if the user answers with 'y'
            if (scanner.nextLine().equals("y")) {
                for (Order order : oldOrders) {
                    checkoutService.printOrder(order);
                }
            }
            // If the user already has an account, the user will go through the normal user flow afterward, but with the stored user information
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("Press enter to continue with your order.");
            scanner.nextLine();
            userFlow(new Order(oldOrders.get(0).getUser()));

        } else { // if the user said 'no' in the beginning, user will be directed here.
            Order order = new Order(userService.createUser(new User()));
            userFlow(order);
        }
    }
    // Method to flow user through application
    public void userFlow(Order order) {
        printCatalog();
        orderService.chooseFromMenu(order);
        checkoutService.checkoutService(order);
    }

    // Method to print the product catalog
    public void printCatalog() {
        ProductCatalog productCatalog = new ProductCatalog(LoadCSV.readItemsFromCSV());
        System.out.println("ID  |  Product name               | Price (€) | Production time (hours)");
        System.out.println("-------------------------------------------------------------");

        productCatalog.getProductCatalog().forEach((id, product) -> {
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
