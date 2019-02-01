package multiplayer.multiplayerModel.messages;

import model.ProductType;

import java.util.EnumMap;

public class TruckItemsMessage extends Message {
    private EnumMap<ProductType, Integer> products = new EnumMap<>(ProductType.class);

    public EnumMap<ProductType, Integer> getProducts() {
        return products;
    }

    public void addToProducts(ProductType productType, int price) {
        products.put(productType, price);
    }
}
