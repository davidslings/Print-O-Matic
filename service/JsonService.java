package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pojo.Order;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;

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
    }

    // Method to read and process JSON files in a directory
    public static int fileReader() {
        // Create a GsonBuilder with a custom type adapter for LocalTime
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter());
        Gson gson = gsonBuilder.create();

        // Specify the directory where JSON files are located
        Path fileDirectory = Paths.get("files");
        int productionTime = 0;

        try {
            // Use Files.newDirectoryStream to get a list of JSON files in the directory
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(fileDirectory, "*.json");
            // Iterate through each JSON file
            for (Path filePath : directoryStream) {
                try {
                    // Read the JSON file into a string
                    String jsonContent = new String(Files.readAllBytes(filePath));

                    // Deserialize the JSON content into an Order object
                    Order oldOrder = gson.fromJson(jsonContent, Order.class);

                    // Update production time by adding the production time of the old order
                    productionTime += oldOrder.getProductionTime();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productionTime;
    }


}
