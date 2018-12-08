import java.util.ArrayList;

public class Warehouse {
    private static final int INITIAL_CAPACITY = 100;
    private static final int BASE_UPGRADE_COST = 100;
    private ArrayList<ProductType> contents = new ArrayList<>();
    private int level = 1;
    private int capacity = INITIAL_CAPACITY;

    public void upgrade() {
        level++;
        capacity += getTotalCapacity(level) - getTotalCapacity(level - 1);
    }

    public int getUpgradeCost() {
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

    public int getCapacity() {
        return capacity;
    }

}
