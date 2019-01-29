package multiplayer.messages;

import model.ProductType;

import java.util.EnumMap;
import java.util.EnumSet;

public class ProductExchangeList extends Message{
    private EnumMap<ProductType, Integer> products;

    public ProductExchangeList(String sender, String receiver, EnumMap<ProductType, Integer> products) {
        super(sender, receiver);
        this.products = products;
    }

    public EnumMap<ProductType, Integer> getProducts() {
        return products;
    }
}
