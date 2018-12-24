package model;

public class Workshop {
    private static final int INITIAL_PRODUCTION_TYPE = 8;
    private ProductType[] input;
    private ProductType output;
    private int numberOfInputs = 1;
    private int numberOfOutputs = 1;
    private int productionTime = INITIAL_PRODUCTION_TYPE;
    private int timeLeftToProduction;
    private int level = 1;
    private int price;
    private WorkshopType type;
    private String name;

    public Workshop(WorkshopType type) {
        this.type = type;
        input = type.getInput();
        output = type.getOutput();
        name = type.name();
        price = type.getPrice();
    }

    public Product[] produce() {
        Product[] products = new Product[numberOfOutputs];
        for (int i = 0; i < products.length; i++) {
            products[i] = new Product(output);
        }
        return products;
    }

    public void startProduction() {
        timeLeftToProduction = productionTime;
    }

    public void decrementTimeLeft() {
        timeLeftToProduction--;
        if (timeLeftToProduction < 0)
            timeLeftToProduction = 0;
    }

    public boolean isProductionEnded() {
        return timeLeftToProduction == 0;
    }

    public ProductType[] getNeededProducts() {
        ProductType[] productTypes = new ProductType[numberOfInputs * input.length];
        for (int i = 0; i < productTypes.length; i++) {
            productTypes[i] = input[i / numberOfInputs];
        }
        return productTypes;
    }

    public int getUpgradePrice() {
       return price * 2 - 50;
    }

    public boolean canUpgrade() {
        return level < 5;
    }

    public void upgrade() {
        level++;
        numberOfInputs++;
        numberOfOutputs++;
        if (level == 4) {
            numberOfInputs++;
            numberOfOutputs++;
        }
        if (level == 5) {
            productionTime -= 2;
        }
        price = getUpgradePrice();
    }




}
