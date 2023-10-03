package pojo;

public class OrderQuant {
    private Integer quantity;
    private Item item;

    public OrderQuant(Integer quantity, Item item) {
        this.quantity = quantity;
        this.item = item;
    }

    public OrderQuant(OrderQuant source) {
        this.quantity = source.quantity;
        this.item = source.item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }



}
