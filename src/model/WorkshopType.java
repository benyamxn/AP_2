package model;

public enum WorkshopType {
    EGG_POWDER_PLANT(ProductType.EGG, ProductType.DRIED_EGG, 200),
    COOKIE_BAKERY(ProductType.DRIED_EGG, ProductType.COOKIE, 250),
    SPINNERY(ProductType.WOOL, ProductType.SEWING, 250),
    GRINDING_FACTORY(ProductType.HORN, ProductType.BRIGHT_HORN, 300),
    WEAVING_FACTORY(ProductType.SEWING, ProductType.FABRIC, 250),
    MANUFACTURING_PLANT(ProductType.BRIGHT_HORN, ProductType.INTERMEDIATE, 350),
    CUSTOM();

    private ProductType[] input;
    private ProductType output;
    private int price;

    WorkshopType(ProductType input, ProductType output, int price) {
        this.input = new ProductType[1];
        this.input[0] = input;
        this.output = output;
        this.price = price;
    }

    WorkshopType(ProductType output, int price, ProductType... inputs) {
        this.input = inputs;
        this.output = output;
        this.price = price;
    }

    WorkshopType() {
    }

    public ProductType[] getInput() {
        return input;
    }

    public ProductType getOutput() {
        return output;
    }

    public int getPrice() {
        return price;
    }


    @Override
    public String toString() {
        switch (this){

            case EGG_POWDER_PLANT:
                return "DriedEggs (Egg Powder Plant)";
            case COOKIE_BAKERY:
                return "Cake (Cookie Bakery)";
            case SPINNERY:
                return "Spinnery (Spinnery)";
            case GRINDING_FACTORY:
                return "Grinding Factory";
            case WEAVING_FACTORY:
                return "Weaving (Weaving Factory)";
            case MANUFACTURING_PLANT:
                return "Manufacturing Plant";
                default:
                    return "";
        }
    }
}
