package model;

import GUI.WorkshopGUI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Arrays;

public class Workshop implements Upgradable {
    private static final int INITIAL_PRODUCTION_TYPE = 7;
    private ProductType[] input;
    private ProductType output;
    private int numberOfInputs = 1;
    private int numberOfOutputs = 1;
    private int productionTime = INITIAL_PRODUCTION_TYPE;
    private int timeLeftToProduction;
    private int level = 1;
    private int price;
    private boolean onProduction = false;
    private WorkshopType type;
    private String name;
    private Point productionPoint;
    private transient WorkshopGUI workshopGUI;




    public static Workshop readFromJson(String path) throws IOException {
        Reader reader = new FileReader(path);
        Gson gson = new GsonBuilder().create();
        Workshop workshop = gson.fromJson(reader, Workshop.class);
        reader.close();
        return workshop;
    }

    public void setWorkshopGUI(WorkshopGUI workshopGUI) {
        this.workshopGUI = workshopGUI;
    }

    public Workshop(WorkshopType type, Point point) {
        this.type = type;
        input = type.getInput();
        output = type.getOutput();
        name = type.name();
        price = type.getPrice();
        productionPoint = point;
    }

    public Product[] produce() {
        Product[] products = new Product[numberOfOutputs];
        for (int i = 0; i < products.length; i++) {
            products[i] = new Product(output);
        }
        return products;
    }

    public void startProduction() {
        workshopGUI.produce();
        onProduction = true;
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

    public Point getProductionPoint() {
        return productionPoint;
    }

    public void endProduction() {
        onProduction = false;
        timeLeftToProduction = 0;
    }

    public ProductType[] getNeededProducts() {
        ProductType[] productTypes = new ProductType[numberOfInputs * input.length];
        for (int i = 0; i < productTypes.length; i++) {
            productTypes[i] = input[i / numberOfInputs];
        }
        return productTypes;
    }
    @Override
    public int getUpgradePrice() {
       return price * 2 - 50;
    }

    @Override
    public boolean canUpgrade() {
        return level < 5;
    }

    @Override
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
        workshopGUI.upgrade();
    }

    public void saveToJson(String path) throws IOException {
        Writer writer = new FileWriter(path);
        Gson gson = new GsonBuilder().create();
        gson.toJson(this, writer);
        writer.close();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Workshop)) return false;

        Workshop workshop = (Workshop) o;

        if (numberOfInputs != workshop.numberOfInputs) return false;
        if (numberOfOutputs != workshop.numberOfOutputs) return false;
        if (productionTime != workshop.productionTime) return false;
        if (timeLeftToProduction != workshop.timeLeftToProduction) return false;
        if (level != workshop.level) return false;
        if (price != workshop.price) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(input, workshop.input)) return false;
        if (output != workshop.output) return false;
        if (type != workshop.type) return false;
        if (onProduction != workshop.onProduction) return false;
        return name.equals(workshop.name);
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isOnProduction() {
        return onProduction;
    }

    public WorkshopType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        String status = "Workshop (" + this.toString() + "):\n" + "Requirements:\n";
        for (ProductType productType : input) {
            status += productType.toString() + "\n";
        }
        status += "Output:\n" + output + "\n";
        return status;
    }

    public void setOnProduction(boolean onProduction) {
        this.onProduction = onProduction;
    }

    public int getLevel() {
        return level;
    }
}
