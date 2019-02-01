package multiplayer.multiplayerModel;

import model.ProductType;
import multiplayer.multiplayerGUI.ShopGUI;
import multiplayer.multiplayerModel.messages.HelicopterProductsMessage;
import multiplayer.multiplayerModel.messages.Message;
import multiplayer.multiplayerModel.messages.TruckItemsMessage;

import java.util.EnumMap;
import java.util.Map;

public class Shop {

    private EnumMap<ProductType,Integer> products = new EnumMap<>(ProductType.class);
    private ShopGUI shopGUI;
    public Shop() {
    }

    public Shop(EnumMap<ProductType, Integer> products) {
        this.products = products;
    }


    public synchronized boolean hasProducts(EnumMap<ProductType,Integer> input){
        for (Map.Entry<ProductType, Integer> productTypeIntegerEntry : input.entrySet()) {
            if( products.get(productTypeIntegerEntry.getKey()) == null || products.get(productTypeIntegerEntry.getKey()) < input.get(productTypeIntegerEntry.getKey())){
                return false;
            }
        }
        return true;
    }

    public synchronized void remove(EnumMap<ProductType,Integer> input){
        for (Map.Entry<ProductType, Integer> product : input.entrySet()) {
            int number = products.get(product.getKey()) -  input.get(product.getKey());
            if(number > 0 ){
                products.put(product.getKey(),number);
            } else {
                products.put(product.getKey(),0);
            }
        }
    }

    public synchronized void add(EnumMap<ProductType,Integer> input) {
        for (Map.Entry<ProductType, Integer> product : input.entrySet()) {
            int number = products.get(product.getKey()) == null ? 0 : products.get(product.getValue()) - input.get(product.getKey());
            products.put(product.getKey(), number);
        }
    }

    public EnumMap<ProductType, Integer> getProducts() {
        return products;
    }

    public Message createProductsMessage() {
        TruckItemsMessage message = new TruckItemsMessage();
        for (ProductType productType : products.keySet()) {
            message.addToProducts(productType, productType.getSaleCost());
        }
        return message;
    }

    public Message createHelicopterProductsMessage() {
        HelicopterProductsMessage message = new HelicopterProductsMessage();
        for (Map.Entry<ProductType, Integer> entry : products.entrySet()) {
            message.addToList(new HelicopterProduct(entry.getKey(), entry.getKey().getBuyCost(), entry.getValue()));
        }
        return message;
    }

    public ShopGUI getShopGUI() {
        return shopGUI;
    }

    public void setShopGUI(ShopGUI shopGUI) {
        this.shopGUI = shopGUI;
    }
}
