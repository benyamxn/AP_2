import java.util.ArrayList;

public enum WorkshopType {
    EGG_POWERDER_PLANT(ProductType.EGG, ProductType.DRIED_EGG),
    COOKIE_BAKERY(ProductType.DRIED_EGG, ProductType.COOKIE),
    SPINNERY(ProductType.WOOL, ProductType.SEWING),
    GRINDING_FACTORY(ProductType.HORN, ProductType.BRIGHT_HORN),
    WEAVING_FACTORY(ProductType.SEWING, ProductType.FABRIC),
    MANUFACTORING_PLANT(ProductType.BRIGHT_HORN, ProductType.INTERMEIDATE);


    private ProductType[] input;
    private ProductType output;

    WorkshopType(ProductType input, ProductType output) {
        this.input = new ProductType[1];
        this.input[0] = input;
        this.output = output;
    }

    WorkshopType(ProductType output, ProductType... inputs) {
        this.input = inputs;
        this.output = output;
    }
}
