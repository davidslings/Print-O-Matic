package pojo;

import java.util.HashMap;
import java.util.Map;

public class ProductCatalog {

    private final HashMap<Integer, Item> products;

    public ProductCatalog() {
        this.products = new HashMap<>();
    }

    public ProductCatalog(ProductCatalog source) {
        this.products = source.products;
    }


    public Map<Integer, Item> getProductCatalog() {
        return products;
    }

    public void addProduct(Item item) {
        products.put(item.getProductID(), item);
    }

    public Item getProduct(int productID) {

        return new Item(products.get(productID));
    }

}
