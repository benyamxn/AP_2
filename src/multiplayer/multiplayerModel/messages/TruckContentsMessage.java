package multiplayer.multiplayerModel.messages;

import model.ProductType;

import java.util.EnumMap;

public class TruckContentsMessage extends Message {
    EnumMap<ProductType, Integer> contents = new EnumMap<ProductType, Integer>(ProductType.class);

    public void addToMap(ProductType productType, int quantity) {
        contents.put(productType, quantity);
    }

    public EnumMap<ProductType, Integer> getContents() {
        return contents;
    }
}
