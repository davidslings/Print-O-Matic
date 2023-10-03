package pojo;

import java.time.LocalTime;


public class Item {

    private int productID;
    private String productName;
    private double price;
    private LocalTime productionTime;


    public Item(int productID, String productName, double price, LocalTime productionTime) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.productionTime = productionTime;
    }

    public Item(Item source) {
        this.productID = source.productID;
        this.productName = source.productName;
        this.price = source.price;
        this.productionTime = source.productionTime;
    }

    public int getProductID() {
        return this.productID;
    }


    public String getProductName() {
        return this.productName;
    }

    public double getPrice() {
        return this.price;
    }

    public LocalTime getProductionTime() {
        return this.productionTime;
    }


}
