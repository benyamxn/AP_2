public class Product {
    private static final int DEFAULT_EXPIRATION_TIME = 10;
    private ProductType type;
    private int timeLeftToExpire = DEFAULT_EXPIRATION_TIME;

    public boolean isExpired() {
        return (timeLeftToExpire <= 0);
    }

    public void decreaseTimeLeftToExpire(int time) {
        timeLeftToExpire -= time;
        if (timeLeftToExpire < 0)
            timeLeftToExpire = 0;
    }

    public Product(ProductType type) {
        this.type = type;
    }
}
