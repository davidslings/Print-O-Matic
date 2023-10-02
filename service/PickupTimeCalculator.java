package service;
import pojo.*;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class PickupTimeCalculator {

    // Method to calculate the pickup time based on order production time and queue time
    public static LocalDateTime CalculatePickupTime(Order order) {
        // Calculate the total production time including queue time
        int productionTime = CalculateProductionTime(order, jsonService.fileReader());

        // Read the opening hours of the shop
        WorkWeek workWeek = csvLoader.LoadCSV.readOpeningHours();

        // Get the current hour
        int hourCount = LocalTime.now().getHour();

        // Get the current day of the week
        DayOfWeek today = LocalDateTime.now().getDayOfWeek();

        // Initialize the number of days to complete production
        int days = -1;

        // Get the closing time of the current day
        int closingTime = workWeek.getCurrentDayClosingTime(today);

        // Get the opening time of the current day
        int openingTime = workWeek.getCurrentDayOpeningTime(today);

        // Loop to calculate the pickup time
        outerLoop: while (productionTime != 0) {
            days++;
            while (hourCount >= openingTime && hourCount < closingTime) {
                hourCount++;
                productionTime--;
                if (productionTime == 0) {
                    break outerLoop;
                };
            }
            today = today.plus(1);
            hourCount = workWeek.getCurrentDayOpeningTime(today);
        }

        // Calculate the exact pickup date and time
        LocalDateTime day = LocalDateTime.now().plusDays(days).plusHours(hourCount);
        while (day.getHour() >= workWeek.getCurrentDayClosingTime(day.getDayOfWeek()) || day.getHour() < workWeek.getCurrentDayOpeningTime(day.getDayOfWeek())) {
            day = day.plusHours(1);
        }

        // Format the pickup time and return it
        return day;
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
//        return day.format(formatter);
    }

    // Method to calculate the total production time for an order
    public static int CalculateProductionTime(Order order, int queueTime) {
        // Initialize the total production time with queue time
        int totalProductionTime = queueTime;
        for (int i = 0; i < order.getOrderItems().size(); i++) {
            // Calculate the production time for each item and quantity
            int productionTime = order.getOrderItems().get(i).getItem().getProductionTime().getHour();
            int productionTimePerItem = productionTime * order.getOrderItems().get(i).getQuantity();
            totalProductionTime += productionTimePerItem;
        }
        // Set the production time in the order object
        order.setProductionTime(totalProductionTime);
        return totalProductionTime;
    }
}
