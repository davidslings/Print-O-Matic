package service;

import csvLoader.LoadCSV;
import pojo.Order;
import pojo.ProductCatalog;
import pojo.User;

import java.util.ArrayList;


public class MenuService {
    private final UserService userService;
    private final OrderService orderService;
    private final CheckoutService checkoutService;


    // Constructor for MenuService
    public MenuService() {
        // Initialize various services and components
        this.userService = new UserService();
        this.orderService = new OrderService();
        this.checkoutService = new CheckoutService();
    }

    // Method to display an introduction message
    public void displayIntroduction() {
        System.out.println("Welcome to Dunder Mifflin, the automatic PrintStore designed to accommodate you in all your needs.\nFirst, please answer the following questions.\n");
        User currentUser = new User();

        Order order = new Order(new ArrayList<>(), null, null, 0, userService.createUser(currentUser));
        printCatalog();
        orderService.ChooseFromMenu(order);
        checkoutService.checkoutService(order);
    }


    // Method to print the product catalog
    public void printCatalog() {
        ProductCatalog productCatalog = new ProductCatalog(LoadCSV.readItemsFromCSV());
        System.out.println("ID  |  Product name               | Price (€) | Production time (hours)");
        System.out.println("----------------------------------------------------------");

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
