package multiplayer.multiplayerModel.messages;

import model.ProductType;

import java.util.EnumMap;

public class HelicopterItems extends Message {
    private EnumMap<ProductType, Integer> products = new EnumMap<>(ProductType.class);

    public EnumMap<ProductType, Integer> getProducts() {
        return products;
    }

    public void addToProducts(ProductType productType, int quantity) {
        products.put(productType, quantity);
    }
}
