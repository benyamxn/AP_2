package multiplayer.multiplayerModel.messages;

import model.ProductType;
import multiplayer.multiplayerModel.*;

import java.util.EnumMap;

public class ProductExchangeList extends Message{
    private EnumMap<ProductType, Integer> products;

    public ProductExchangeList(CompactProfile sender, CompactProfile receiver, EnumMap<ProductType, Integer> products) {
        super(sender, receiver);
        this.products = products;
    }
    
    public EnumMap<ProductType, Integer> getProducts() {
        return products;
    }
}
