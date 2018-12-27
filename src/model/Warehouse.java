package model;

import java.util.*;
import java.util.Map;

public class Warehouse implements Upgradable {
    private static final int INITIAL_CAPACITY = 100;
    private static final int BASE_UPGRADE_COST = 100;
    private LinkedList<ProductType> contents = new LinkedList<>();
    private int level = 1;
    private double capacity = INITIAL_CAPACITY;

    @Override
    public void upgrade() {
        level++;
        capacity += getTotalCapacity(level) - getTotalCapacity(level - 1);
    }

    @Override
    public boolean canUpgrade() {
        return canUpgrade();
    }

    @Override
    public int getUpgradePrice() {
        return BASE_UPGRADE_COST * (int) Math.pow(2, level) + 150 - 50 * (level - 1);
    }

    private int getTotalCapacity(int level) {
        return ((INITIAL_CAPACITY / 4) * level * level) + 3 * INITIAL_CAPACITY / 4 * level;
    }

    public void addProduct(Product product) {
        ProductType productType = product.getType();
        contents.add(productType);
        capacity -= productType.getDepotSize();
    }

    public void addProduct(ProductType[] productTypes) {
        for (ProductType productType : productTypes) {
            addProduct(productType);
        }
    }

    public void addProduct(LinkedList<ProductType> productTypes) {
        for (ProductType productType : productTypes) {
            addProduct(productType);
        }
    }

    public void addProduct(HashMap<ProductType, Integer> quantities) {
        addProduct(unwrapQuantities(quantities));
    }

    public void addProduct(ProductType productType) {
        contents.add(productType);
        capacity -= productType.getDepotSize();
    }

    public ProductType[] getProductList() {
        ProductType[] productList = new ProductType[5];
        productList = contents.toArray(productList);
        return productList;
    }

    public ProductType[] removeProducts(ProductType[] productTypes) {
        for (ProductType productType : productTypes) {
            contents.remove(productType);
            capacity -= productType.getDepotSize();
        }
        return productTypes;
    }

    boolean hasCapacity(ProductType productType) {
        return (capacity >= productType.getDepotSize());
    }

    boolean hasCapacity(Product product) {
        return (capacity >= product.getType().getDepotSize());
    }

    public double getCapacity() {
        return capacity;
    }

    public boolean hasProducts(ProductType[] productTypes) {
        return contents.containsAll(Arrays.asList(productTypes));
    }

    public boolean hasProducts(ArrayList<ProductType> productTypes) {
        return contents.containsAll(productTypes);
    }

    public boolean hasProducts(HashMap<ProductType, Integer> quantities) {
        return contents.containsAll(unwrapQuantities(quantities));
    }

    private static LinkedList<ProductType> unwrapQuantities(HashMap<ProductType, Integer> quantities) {
        LinkedList<ProductType> productTypes = new LinkedList<>();
        for (Map.Entry<ProductType, Integer> entry : quantities.entrySet()) {
            ProductType temp = entry.getKey();
            for (Integer i = 0; i < entry.getValue(); i++) {
                productTypes.add(temp);
            }
        }
        return productTypes;
    }

    public void removeProducts(HashMap<ProductType, Integer> quantities) {
        removeProducts(unwrapQuantities(quantities).toArray(new ProductType[0]));
    }
}
