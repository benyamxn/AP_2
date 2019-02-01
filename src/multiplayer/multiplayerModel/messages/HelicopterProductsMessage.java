package multiplayer.multiplayerModel.messages;

import multiplayer.multiplayerModel.HelicopterProduct;

import java.util.LinkedList;

public class HelicopterProductsMessage extends Message {
    LinkedList<HelicopterProduct> products = new LinkedList<>();

    public LinkedList<HelicopterProduct> getProducts() {
        return products;
    }

    public void addToList(HelicopterProduct helicopterProduct) {
        products.add(helicopterProduct);
    }
}
