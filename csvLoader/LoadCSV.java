package csvLoader;

import pojo.Item;
import pojo.OpeningHours;
import pojo.ProductCatalog;
import pojo.WorkWeek;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;

public class LoadCSV {


    public static ProductCatalog readItemsFromCSV() {
        ProductCatalog items = new ProductCatalog();
        String fileName = "csvData/PhotoShop_PriceList.csv";
        Path pathToFile = Paths.get(fileName);
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(";");
                Item item = createItem(attributes);
                items.addProduct(item);
                line = br.readLine();
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return items;
    }


    public static Item createItem(String[] attributes) {
        int productID = Integer.parseInt(attributes[0]);
        String productName = attributes[1];
        double price = Double.parseDouble(attributes[2]);
        LocalTime productionTime = LocalTime.parse(attributes[3]);
        return new Item(productID, productName, price, productionTime);
    }

    public static WorkWeek readOpeningHours() {
        WorkWeek workWeek = new WorkWeek();
        String fileName = "csvData/PhotoShop_OpeningHours.csv";
        Path pathToFile = Paths.get(fileName);
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(";");
                if (!attributes[0].startsWith("D")) {
                    OpeningHours openingHours = createOpeningHours(attributes);
                    workWeek.getOpeningTimes().add(openingHours);
                }
                line = br.readLine();
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return workWeek;
    }

    public static OpeningHours createOpeningHours(String[] attributes) {
        String weekday = (attributes[1]);
        LocalTime openingTime = LocalTime.parse(attributes[2]);
        LocalTime closingTime = LocalTime.parse(attributes[3]);
        return new OpeningHours(weekday, openingTime, closingTime);
    }

}

