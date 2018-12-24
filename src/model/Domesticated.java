package model;


public class Domesticated extends Animal {

    private DomesticatedType type;
    private int eatenAmountOfFood;
    private int turnsLeftToProduce;
    private int turnsToProduce;

    public Domesticated(Point location, DomesticatedType type, int maxHealth) {
        super(location);
        this.type = type;
        setMaxHealth(maxHealth);
        setHealth(maxHealth);
        turnsToProduce = type.getTurnToProduce();
        turnsLeftToProduce = turnsToProduce;
    }

    public Domesticated(Point location) {
        super(location);
    }

    public Product produce(){
        if (turnsLeftToProduce == 0) {
            turnsLeftToProduce = turnsToProduce;
            return new Product(type.getProductType());
        }
        else
            return null;
    }

    public void eat(){
        addToHealth((int) Math.ceil((double) getMaxHealth() / 3));
    }

    public void decrementTurnsLeftToProduce(){
        turnsLeftToProduce--;
        if (turnsLeftToProduce < 0)
            turnsLeftToProduce = 0;
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
