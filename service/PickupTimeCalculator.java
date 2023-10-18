package service;

import pojo.Order;
import pojo.WorkWeek;

import java.time.LocalDateTime;

public class PickupTimeCalculator {


    /**
     * This method calculates the pickup time by taking the previous pickup time
     * and adding the hours of production for the current order to it.
     *
     * @param order
     * @return LocalDateTime containing the actual pickup time
     */
    public static LocalDateTime CalculatePickupTime(Order order) {

        // Calculates the total production time of the current order
        int productionTime = CalculateProductionTime(order);

        // I created a Workweek class, which instantiates an arraylist to take the opening hours as object
        WorkWeek workWeek = csvLoader.LoadCSV.readOpeningHours();

        // The JsonService class will read the previous pickup time first and put it in a variable
        LocalDateTime oldDate = JsonService.pickupTimeReader();
        // If there was no previous pick up time, it will just use the current time to calculate the pickup time
        LocalDateTime day = JsonService.pickupTimeReader() == null ? LocalDateTime.now() : oldDate;

        /* To accurately calculate the pickup time, we need to know three more things:
        - the exact hour (int) of the previous pickup time
        - the opening time on the day of the previous pickup time
        - the closing time on the day of the previous pickup time
        */

        int hourCount = day.getHour();
        int openingTime = workWeek.getCurrentDayOpeningTime(day.getDayOfWeek());
        int closingTime = workWeek.getCurrentDayClosingTime(day.getDayOfWeek());

        // Watch it: Here's where the magic happens
        outerLoop:
        while (productionTime != 0) { //This outer loop has a condition that is never met, but clarifies its purpose (when production time is over, break out)
            while (hourCount >= openingTime && hourCount < closingTime) { // This loop will only run when the printshop is open on a given day
                day = day.withHour(hourCount);
                hourCount++;
                productionTime--;
                if (productionTime == 0) {
                    break outerLoop;
                    /* It will break out of the loop bc the production time is at zero.
                    We need to break out of both loops bc we already have the exact date and time
                    We would not want to add a day and set the hourcount to the day.hour etc..
                    */
                }

            }
            // adding a day when a day is finished
            day = day.plusDays(1);
            // The opening and closing times needs to be adapted to the new day
            openingTime = workWeek.getCurrentDayOpeningTime(day.getDayOfWeek());
            closingTime = workWeek.getCurrentDayClosingTime(day.getDayOfWeek());
            // also the hourcount needs to be reset to the opening time of the new day
            hourCount = workWeek.getCurrentDayOpeningTime(day.getDayOfWeek());
        }

        // The great thing about doing it this way, is that you minimize the amount of code and can directly return the day in LocalDateTime.
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
