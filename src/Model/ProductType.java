package Model;

public enum ProductType {
    EGG(1, 20, 10),
    WOOL(5, 200, 100),
    PLUME(2, 200, 100),
    MILK(10, 2000, 1000),
    HORN(6, 2000, 1000),
    DRIED_EGG(4, 100, 50),
    TUSK(15, 2000, 1000),
    FEATHER(8, 200, 100),
    CAKE(5, 200, 100),
    COOKIE(5, 200, 100),
    FLOURY_CAKE(6, 400, 200),
    SEWING(3, 300, 150),
    FABRIC(6, 400, 300),
    CANIVAL_DRESS(8, 1400, 1300),
    SOUR_CREAM(8, 3000, 1500),
    CURD(6, 4000, 2000),
    CHEESE(5, 5000, 2500),
    COLORED_PLUME(2, 300, 150),
    ARDORNMENT(4, 400, 300),
    BRIGHT_HORN(5, 3000, 1500),
    INTERMEDIATE(4, 4000, 2000),
    SOUVENIR(3, 5000, 2500),
    FLOUR(1.5, 20, 10),
    CHEESE_FERMENT(2, 25, 15),
    VARNISH(2.5, 25, 15),
    MEGA_PIE(20, 20000, 10000),
    SPRUCE_GRIZZLY(25, 2500, 7000),
    SPRUCE_LION(25, 2500, 7000),
    SPRUCE_BROWN_BEAR(25, 2500, 7000),
    SPRUCE_JAGUAR(25, 2500, 7000),
    SPRUCE_WHITE_BEAR(25, 2500, 7000),
    CAGED_GRIZZLY(20, 80, 80),
    CAGED_LION(20, 150, 150),
    CAGED_BROWN_BEAR(20, 100, 100),
    CAGED_JAGUAR(20, 200, 200),
    CAGED_WHITE_BEAR(20, 100, 100);

    private double depotSize;
    private int buyCost;
    private int saleCost;

    ProductType(double depotSize, int buyCost, int saleCost) {
        this.depotSize = depotSize;
        this.buyCost = buyCost;
        this.saleCost = saleCost;
    }

    public double getDepotSize() {
        return depotSize;
    }

    public int getBuyCost() {
        return buyCost;
    }

    public int getSaleCost() {
        return saleCost;
    }
}
