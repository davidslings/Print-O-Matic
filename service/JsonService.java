package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pojo.Order;

import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class JsonService {

    // Method to write an Order object to a JSON file
    public static void fileWriter(Order order) {
        // Create a GsonBuilder with a custom type adapter for LocalTime
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter());
        Gson gson = gsonBuilder.create();

        // Convert the Order object to JSON
        String orderToJson = gson.toJson(order);
        try {
            // Create a FileWriter and write the JSON content to a file with a name based on the order time
            FileWriter fileWriter = new FileWriter("files/" + order.getOrderTime() + ".json");
            fileWriter.write(orderToJson);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e);
        }

        Gson newGson = new Gson();
        String pickupTimeToJson = newGson.toJson(order.getPickupTime());

        try {
            // this writes the new pickup time to the pickup time JSON file.
            FileWriter fileWriter = new FileWriter("files/pickupTime.json");
            fileWriter.write(pickupTimeToJson);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static List<Order> readOldOrder(String email) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter());
        Gson gson = gsonBuilder.create();

        // Create a File object representing the directory
        File directory = new File("files");

        // Create an arraylist to store the old orders
        List<Order> orders = new ArrayList<>();

        // List all files in the directory
        File[] files = directory.listFiles();

        // Iterate through the files using a for loop
        for (File file : files) {
            // the if-statement ensures that the pickup time file is not read
            if (!file.getName().startsWith("p")) {

                try { // creates a try-catch statement to catch the FileNotFoundException
                    FileReader reader = new FileReader(file); // reads the file into a Filereader class object
                    Order order = gson.fromJson(reader, Order.class); // the filereader is then used to create an order out of the object

                    // all orders that match the given email address will be added to an array list
                    if (order.getUser().getEmail().equals(email)) {
                        orders.add(order);
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("File not found.");
                }
            }
        } // returning null signals to the function above that there is no user known with this email address
        if (orders.isEmpty()) {
            return null;
        } else {
            return orders;
        }
    }


    public static LocalDateTime pickupTimeReader() {
        String filePath = "files/pickupTime.json";

        try {
            // Open the file for reading using FileReader and wrap it in a BufferedReader for efficiency
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Read the JSON string from the file
            String jsonString = bufferedReader.readLine().substring(1, 17);
            String[] strArray = jsonString.split(" ");
            String finalString = strArray[0] + "T" + strArray[1] + ":00";

            // Define the format of the input string
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss");

            // Parse the string into a LocalDateTime object using the formatter
            LocalDateTime localDateTime = LocalDateTime.parse(finalString, formatter);

            // Close the BufferedReader and FileReader
            bufferedReader.close();
            fileReader.close();

            return localDateTime;
        } catch (IOException e) {
            System.out.println(e);
            // Handle the exception appropriately
        }

        return null;
    }


}
