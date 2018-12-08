package model;

public enum DomesticatedType {

    GUINEA_FOWL(100, ProductType.EGG),
    OSTRICH(1000, ProductType.FEATHER),
    BUFFALO(10000, ProductType.HORN),

    GOOSE(100, ProductType.EGG),
    YAK(1000, ProductType.WOOL),
    BURENKA(10000, ProductType.MILK),

    PENGUIN(100, ProductType.EGG),
    KING_PENGUIN(1000, ProductType.MILK),
    Walrus(10000, ProductType.TUSK),

    TURKEY(100, ProductType.EGG),
    SHEEP(1000, ProductType.WOOL),
    COW(10000, ProductType.MILK),

    CHICKEN(100, ProductType.EGG),
    LAMA(1000, ProductType.WOOL),
    GOAT(10000, ProductType.HORN);

    @Override
    public String toString() {
        switch (this) {
            case GUINEA_FOWL:
                return "Guinea Fool";
            case OSTRICH:
                return "Ostrich";
            case BUFFALO:
                return "Buffalo";
            case GOOSE:
                return "Goose";
            case YAK:
                return "Yak";
            case BURENKA:
                return "Burenka";
            case PENGUIN:
                return "Penguin";
            case KING_PENGUIN:
                return "King Penguin";
            case Walrus:
                return "Walrus";
            case TURKEY:
                return "Turkey";
            case SHEEP:
                return "Sheep";
            case COW:
                return "Cow";
            case CHICKEN:
                return "Chicken";
            case LAMA:
                return "Lama";
            case GOAT:
                return "Goat";
            default:
                return "";
        }
    }

    public int getBuyPrice() {
        return basePrice;
    }

    public int getSellPrice(){
        return basePrice / 2;
    }

    public ProductType getProductType() {
        return productType;
    }

    private int basePrice;
    private ProductType productType;

    DomesticatedType(int basePrice, ProductType productType) {
        this.productType = productType;
        this.basePrice = basePrice;
    }

}
