package model;

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
    CARNIVAL_DRESS(8, 1400, 1300),
    SOUR_CREAM(8, 3000, 1500),
    CURD(6, 4000, 2000),
    CHEESE(5, 5000, 2500),
    COLORED_PLUME(2, 300, 150),
    ADORNMENT(4, 400, 300),
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
    SPRUCE_POLAR_BEAR(25, 2500, 7000),
    CAGED_GRIZZLY(20, 80, 80),
    CAGED_LION(20, 150, 150),
    CAGED_BROWN_BEAR(20, 100, 100),
    CAGED_JAGUAR(20, 200, 200),
    CAGED_POLAR_BEAR(20, 100, 100);

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

    @Override
    public String toString() {
        switch (this){
            case EGG:
                return "Egg";
            case WOOL:
                return "Wool";
            case PLUME:
                return "Plume";
            case MILK:
                return "Milk";
            case HORN:
                return "Horn";
            case DRIED_EGG:
                return "Dried Egg";
            case TUSK:
                return "Tusk";
            case FEATHER:
                return "Feather";
            case CAKE:
                return "Cake";
            case COOKIE:
                return "Cookie";
            case FLOURY_CAKE:
                return "Floury Cake";
            case SEWING:
                return "Sewing";
            case FABRIC:
                return "Fabric";
            case CARNIVAL_DRESS:
                return "Carnival Dress";
            case SOUR_CREAM:
                return "Sour Cream";
            case CURD:
                return "Curd";
            case CHEESE:
                return "Cheese";
            case COLORED_PLUME:
                return "Colored Plume";
            case ADORNMENT:
                return "Adornment";
            case BRIGHT_HORN:
                return "Bright Horn";
            case INTERMEDIATE:
                return "intermediate";
            case SOUVENIR:
                return "Souvenir";
            case FLOUR:
                return "Flour";
            case CHEESE_FERMENT:
                return "Cheese Ferment";
            case VARNISH:
                return "Varnish";
            case MEGA_PIE:
                return "Mega Pie";
            case SPRUCE_GRIZZLY:
                return "Spruce Grizzly";
            case SPRUCE_LION:
                return "Spruce Lion";
            case SPRUCE_BROWN_BEAR:
                return "Spruce Brown Bear";
            case SPRUCE_JAGUAR:
                return "Spruce Jaguar";
            case SPRUCE_POLAR_BEAR:
                return "Spruce Polar Bear";
            case CAGED_GRIZZLY:
                return "Caged Grizzly";
            case CAGED_LION:
                return "Caged Lion";
            case CAGED_BROWN_BEAR:
                return "Caged Brown Bear";
            case CAGED_JAGUAR:
                return "Caged Jaguar";
            case CAGED_POLAR_BEAR:
                return "Caged Polar Bear";
                default:
                    return "";
        }
    }

    public static ProductType getTypeByString(String typeString) {
        for (ProductType type : ProductType.values()) {
            if (type.toString().toLowerCase().equals(typeString.toLowerCase()))
                return type;
        }
        return null;
    }

    public void setBuyCost(int buyCost) {
        this.buyCost = buyCost;
    }

    public void setSaleCost(int saleCost) {
        this.saleCost = saleCost;
    }
}
