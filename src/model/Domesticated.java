package model;


public class Domesticated extends Animal {

    private DomesticatedType type;
    private int eatenAmountOfFood;
    private int turnsLeftToProduce;

    public Domesticated(Point location, DomesticatedType type) {
        super(location);
        this.type = type;
        setMaxHealth(type.getMaxHealth());
        setHealth(type.getMaxHealth());
        turnsLeftToProduce = type.getTurnToProduce();
        eatenAmountOfFood = 0;
    }

    public Domesticated(Point location) {
        super(location);
    }

    public Product produce(){
        if (turnsLeftToProduce == 0) {
            turnsLeftToProduce = type.getTurnToProduce();
            return new Product(type.getProductType());
        }
        else
            return null;
    }

    public void eat(){
        addToHealth((int) Math.ceil((double) getMaxHealth() / 3));
        if (getHealth() >= getMaxHealth())
            setHealth(getMaxHealth());
        eatenAmountOfFood++;
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