public enum DomesticatedType {

    GUINEA_FOWL(100, ProductType.EGG),
    OSTRICH(1000, ProductType.FEATHER),
    BUFFALO(10000, ProductType.HORN),

    GOOSE(100, ProductType.EGG),
    YAK(1000, ProductType.WOOL),
    BURNEKA(10000, ProductType.MILK),

    PENGUIN(100, ProductType.EGG),
    KING_PENGUIN(1000, ProductType.MILK),
    Walrus(10000, ProductType.TUSK),

    TURKEY(100, ProductType.EGG),
    SHEEP(1000, ProductType.WOOL),
    COW(10000, ProductType.MILK),

    CHICKEN(100, ProductType.EGG),
    LAMA(1000, ProductType.WOOL),
    GOAT(10000, ProductType.HORN);


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
