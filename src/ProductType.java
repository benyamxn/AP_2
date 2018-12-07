public enum ProductType {
    EGG(1, 20, 10),
    WOOL(5, 200, 100),
    PLUME(2, 200, 100),
    MILK(10, 2000, 1000),
    HORN(6, 2000, 1000),
    DRIED_EGG(4, 100, 50),
    CAKE(5, 200, 100),
    FLOURY_CAKE(6, 400, 200),
    SEWING(3, 300, 150),
    FABRIC(6, 400, 300),
    CANIVAL_DRESS(8, 1400, 1300),
    SourCream(8, 3000, 1500),
    Curd(6, 4000, 2000),
    Cheese(5, 5000, 2500),
    ColoredPlume(2, 300, 150),
    Adornment(4, 400, 300),
    BrightHorn(5, 3000, 1500),
    Intermediate(4, 4000, 2000),
    Souvenir(3, 5000, 2500),
    Flour(1.5, 20, 10),
    CheeseFerment(2, 25, 15),
    Varnish(2.5, 25, 15),
    MegaPie(20, 20000, 10000),
    SpruceGrizzly(25, 2500, 7000),
    SpruceLion(25, 2500, 7000),
    SpruceBrownBear(25, 2500, 7000),
    SpruceJaguar(25, 2500, 7000),
    SpruceWhiteBear(25, 2500, 7000),
    CagedGrizzly(20, 80, 80),
    CagedLion(20, 150, 150),
    CagedBrownBear(20, 100, 100),
    CagedJaguar(20, 200, 200),
    CagedWhiteBear(20, 100, 100);

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
