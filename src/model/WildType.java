package model;

public enum WildType {

    BROWN_BEAR(125),
    LION(200),
    GRIZZLY(150),
    JAGUAR(250),
    POLAR_BEAR(125);

    private int sellPrice;

    WildType(int sellPrice){
        this.sellPrice = sellPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public ProductType returnCagedType(){
        switch (this){
            case BROWN_BEAR:
                return ProductType.CAGED_BROWN_BEAR;
            case LION:
                return ProductType.CAGED_LION;
            case GRIZZLY:
                return ProductType.CAGED_GRIZZLY;
            case JAGUAR:
                return ProductType.CAGED_JAGUAR;
            case POLAR_BEAR:
                return ProductType.CAGED_POLAR_BEAR;
                default:
                    return null;
        }
    }
}
