package GUI;

import model.ProductType;
import model.Warehouse;

public class WarehouseGUI {

    private Warehouse warehouse;
    private final boolean sale = false;
    private ProductButton[] productButtons;

    public WarehouseGUI(Warehouse warehouse) {
        this.warehouse = warehouse;
        for (ProductType productType : warehouse.getProductList()) {

        }
    }

    public void update(){

    }

}
