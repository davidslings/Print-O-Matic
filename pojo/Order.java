package pojo;

import java.util.List;

public class Order {

    private List<OrderQuant> orderItems;
    private String pickupTime;
    private String orderTime;
    private int productionTime;
    private User user;

    public Order(List<OrderQuant> orderItems, String pickupTime, String orderTime, int productionTime, User user) {
        this.orderItems = orderItems;
        this.pickupTime = pickupTime;
        this.orderTime = orderTime;
        this.productionTime = productionTime;
        this.user = user;
    }


    public List<OrderQuant> getOrderItems() {
        return orderItems;
    }

    public void addOrderItem(OrderQuant orderQuant) {
        orderItems.add(new OrderQuant(orderQuant));
    }

    public void removeOrderItem(OrderQuant orderQuant) {
        orderItems.remove(orderQuant);
    }

    public OrderQuant getOrderItem(Integer productID) {
        for (OrderQuant orderItem : orderItems) {
            int expectedProductID = orderItem.getItem().getProductID();
            if (expectedProductID == productID) {
                return orderItem;
            }
        }
        return null;
    }

    public User getUser() {
        return new User(user);
    }

    public void setUser(User user) {
        this.user = new User(user);
    }

    public void setOrderItem(OrderQuant orderQuant) {
        orderItems.set(orderItems.indexOf(orderQuant), new OrderQuant(orderQuant));
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }


    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public int getProductionTime() {
        return productionTime;
    }

    public void setProductionTime(int productionTime) {
        this.productionTime = productionTime;
    }


}


