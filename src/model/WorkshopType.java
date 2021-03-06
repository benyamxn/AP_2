package model;

public enum WorkshopType {
    EGG_POWDER_PLANT(ProductType.EGG, ProductType.DRIED_EGG, 200),
    MEGA_PIE(ProductType.MEGA_PIE, 10000, ProductType.FLOUR, ProductType.SOUR_CREAM, ProductType.CHEESE),
    COOKIE_BAKERY(ProductType.COOKIE, 2500, ProductType.SOUR_CREAM, ProductType.DRIED_EGG),
    PLUME(ProductType.FEATHER, ProductType.PLUME, 2500),
    CARNIVAL_DRESS(ProductType.FABRIC, ProductType.CARNIVAL_DRESS, 15000),
    SPINNERY(ProductType.WOOL, ProductType.SEWING, 250),
    SOUR_CREAM(ProductType.MILK, ProductType.SOUR_CREAM, 1500),
    WEAVING_FACTORY(ProductType.FABRIC, 2000, ProductType.WOOL, ProductType.PLUME),
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
            case CARNIVAL_DRESS:
                return "CarnivalDress (Sewing Factory)";
            case PLUME:
                return "Plume (Plume)";
            case MEGA_PIE:
                return "Mega_Pie (MegaPie)";
            case EGG_POWDER_PLANT:
                return "DriedEggs (Egg Powder Plant)";
            case COOKIE_BAKERY:
                return "Cake (Cookie Bakery)";
            case SPINNERY:
                return "Spinnery (Spinnery)";
            case SOUR_CREAM:
                return "SourCream (SourCream)";
            case WEAVING_FACTORY:
                return "Weaving (Weaving Factory)";
            case MANUFACTURING_PLANT:
                return "Manufacturing Plant";
                default:
                    return "";
        }
    }
}
