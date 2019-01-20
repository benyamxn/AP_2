package model;

import GUI.WarehouseGUI;

import java.util.*;
import java.util.Map;

public class Warehouse implements Upgradable {
    private static final int INITIAL_CAPACITY = 96;
    private static final int BASE_UPGRADE_COST = 100;
    private LinkedList<ProductType> contents = new LinkedList<>();
    private int level = 1;
    private double capacity = INITIAL_CAPACITY;
    private WarehouseGUI warehouseGUI;

    public Warehouse() {
        warehouseGUI = new WarehouseGUI(this);
    }

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

    public int getTotalCapacity(int level) {
        return (int) Math.pow(8 + 2 * (level - 1), 2);
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
        warehouseGUI.update();
    }

    public void addProduct(LinkedList<ProductType> productTypes) {
        for (ProductType productType : productTypes) {
            addProduct(productType);
        }
        warehouseGUI.update();
    }

    public void addProduct(HashMap<ProductType, Integer> quantities) {
        addProduct(unwrapQuantities(quantities));
        warehouseGUI.update();
    }

    public void addProduct(ProductType productType) {
        contents.add(productType);
        capacity -= productType.getDepotSize();
        warehouseGUI.update();
    }

    public ProductType[] getProductList() {
        ProductType[] productList = new ProductType[5];
        productList = contents.toArray(productList);
        return productList;
    }

    public EnumMap<ProductType, Integer> getProductMap() {
        EnumMap<ProductType, Integer> map = new EnumMap<>(ProductType.class);
        for (ProductType content : contents) {
            Integer count = map.get(content);
            if (count == null)
                count = 0;
            count = count + 1;
            map.put(content, count);
        }
        return map;
    }

    public ProductType[] removeProducts(ProductType[] productTypes) {
        for (ProductType productType : productTypes) {
            contents.remove(productType);
            capacity -= productType.getDepotSize();
        }
        warehouseGUI.update();
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

    public String getStatus() {
        return  "Warehouse:\n" + "\tLevel: " + level +
                "\tEmpty Space: " + capacity + "/ " + getTotalCapacity(level)
                + "\tNumber of contents: " + contents.size();
    }

    public int getNumberOfContents() {
        return contents.size();
    }

    public int getLevel() {
        return level;
    }

    public LinkedList<ProductType> getContents() {
        return contents;
    }
}
