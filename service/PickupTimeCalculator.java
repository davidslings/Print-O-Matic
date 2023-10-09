package service;

import pojo.Order;
import pojo.WorkWeek;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class PickupTimeCalculator {

    // Method to calculate the pickup time based on order production time and queue time
    public static LocalDateTime CalculatePickupTime(Order order) {
        // Calculate the total production time including queue time
        int productionTime = CalculateProductionTime(order);

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
        outerLoop:
        while (productionTime != 0) {
            days++;
            while (hourCount >= openingTime && hourCount < closingTime) {
                hourCount++;
                productionTime--;
                if (productionTime == 0) {
                    break outerLoop;
                }
            }
            today = today.plus(1);
            closingTime = workWeek.getCurrentDayClosingTime(today);
            openingTime = workWeek.getCurrentDayOpeningTime(today);
            hourCount = workWeek.getCurrentDayOpeningTime(today);
        }

        // Calculate the exact pickup date and time
        LocalDateTime oldDate = JsonService.pickupTimeReader();
        LocalDateTime day = JsonService.pickupTimeReader() == null ? LocalDateTime.now() : oldDate;

        day = day.plusDays(days).plusHours(hourCount);
            while (day.getHour() >= workWeek.getCurrentDayClosingTime(day.getDayOfWeek()) || day.getHour() < workWeek.getCurrentDayOpeningTime(day.getDayOfWeek())) {
                day = day.plusHours(1);
            }
            return day;
    }

    // Method to calculate the total production time for an order
    public static int CalculateProductionTime(Order order) {
        // Initialize the total production time with queue time
        int totalProductionTime = 0;
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
