package model;

public enum DomesticatedType {

    GUINEA_FOWL(100, ProductType.EGG, 6, 75),
    OSTRICH(1000, ProductType.FEATHER, 8, 125),
    BUFFALO(10000, ProductType.HORN, 10, 200),

    GOOSE(100, ProductType.EGG, 6, 75),
    YAK(1000, ProductType.WOOL, 8, 125),
    BURENKA(10000, ProductType.MILK, 10, 200),

    PENGUIN(100, ProductType.EGG, 6, 75),
    KING_PENGUIN(1000, ProductType.MILK, 8, 125),
    Walrus(10000, ProductType.TUSK, 10, 200),

    TURKEY(100, ProductType.EGG, 6, 75),
    SHEEP(1000, ProductType.WOOL, 8, 125),
    COW(10000, ProductType.MILK, 10, 200),

    CHICKEN(100, ProductType.EGG, 6, 75),
    LAMA(1000, ProductType.WOOL, 8, 125),
    GOAT(10000, ProductType.HORN, 10, 200);

    @Override
    public String toString() {
        switch (this) {
            case GUINEA_FOWL:
                return "Guinea Fowl";
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

    public static DomesticatedType getTypeByString(String typeString) {
        for (DomesticatedType type : DomesticatedType.values()) {
            if (type.toString().toLowerCase().equals(typeString.toLowerCase()))
                return type;
        }
        return null;
    }

    public int getBuyPrice() {
        return basePrice;
    }

    public int getSellPrice() {
        return basePrice / 2;
    }

    public ProductType getProductType() {
        return productType;
    }

    private int basePrice;
    private ProductType productType;
    private int turnsToProduce;
    private int maxHealth;

    DomesticatedType(int basePrice, ProductType productType, int turnsToProduce, int maxHealth) {
        this.productType = productType;
        this.basePrice = basePrice;
        this.turnsToProduce = turnsToProduce;
        this.maxHealth = maxHealth;
    }

    public int getTurnsToProduce() {
        return turnsToProduce;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
}
