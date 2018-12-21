package model;


public class Domesticated extends Animal {

    private DomesticatedType type;
    private int eatenAmountOfFood;

    public Domesticated(Point location, DomesticatedType type, int maxHealth, int eatenAmountOfFood) {
        super(location);
        this.type = type;
        setMaxHealth(maxHealth);
        setHealth(maxHealth);
        this.eatenAmountOfFood = eatenAmountOfFood;
    }

    public Domesticated(Point location) {
        super(location);
    }

    public Product produce(){
        return new Product(type.getProductType());
    }

    public void eat(){
        addToHealth((int) Math.ceil((double) getMaxHealth() / 3));
    }

    @Override
    public void move(Point cornerPoint) {
        super.move(cornerPoint);
    }

    @Override
    public int getBuyPrice() {
        return type.getBuyPrice();
    }

    @Override
    public int getSellPrice() {
        return type.getSellPrice();
    }
}
