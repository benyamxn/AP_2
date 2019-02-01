package multiplayer.multiplayerModel;

import model.ProductType;

import java.io.Serializable;

public class HelicopterProduct implements Serializable {
    private ProductType productType;
    private int buyPrice;
    private int quantity;

    public HelicopterProduct(ProductType productType, int buyPrice, int quantity) {
        this.productType = productType;
        this.buyPrice = buyPrice;
        this.quantity = quantity;
    }

    public ProductType getProductType() {
        return productType;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public int getQuantity() {
        return quantity;
    }
}
