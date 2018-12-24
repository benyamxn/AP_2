package model;

import java.util.EnumSet;

public enum DomesticatedType {

    GUINEA_FOWL(100, ProductType.EGG, 6),
    OSTRICH(1000, ProductType.FEATHER, 8),
    BUFFALO(10000, ProductType.HORN, 10),

    GOOSE(100, ProductType.EGG, 6),
    YAK(1000, ProductType.WOOL, 8),
    BURENKA(10000, ProductType.MILK, 10),

    PENGUIN(100, ProductType.EGG, 6),
    KING_PENGUIN(1000, ProductType.MILK, 8),
    Walrus(10000, ProductType.TUSK, 10),

    TURKEY(100, ProductType.EGG, 6),
    SHEEP(1000, ProductType.WOOL, 8),
    COW(10000, ProductType.MILK, 10),

    CHICKEN(100, ProductType.EGG, 6),
    LAMA(1000, ProductType.WOOL, 8),
    GOAT(10000, ProductType.HORN, 10);

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

    public static DomesticatedType getTypeByString(String typeString) {
        for (DomesticatedType type : EnumSet.allOf(DomesticatedType.class)) {
            if (type.toString().equals(typeString))
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
    private int turnToProduce;

    DomesticatedType(int basePrice, ProductType productType,int turnToProduce) {
        this.productType = productType;
        this.basePrice = basePrice;
        this.turnToProduce = turnToProduce;
    }

    public int getTurnToProduce() {
        return turnToProduce;
    }
}
