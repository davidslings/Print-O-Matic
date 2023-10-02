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

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalTime getProductionTime() {
        return this.productionTime;
    }

    public void setProductionTime(LocalTime productionTime) {
        this.productionTime = productionTime;
    }

    
    
}
