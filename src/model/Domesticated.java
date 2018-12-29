package model;


import java.util.ArrayList;

public class Domesticated extends Animal {

    private DomesticatedType type;
    private int eatenAmountOfFood;
    private int turnsLeftToProduce;

    public Domesticated(Point location, DomesticatedType type) {
        super(location);
        this.type = type;
        setMaxHealth(type.getMaxHealth());
        setHealth(type.getMaxHealth());
        turnsLeftToProduce = type.getTurnsToProduce();
        eatenAmountOfFood = 0;
    }

    public Domesticated(Point location) {
        super(location);
    }

    public Product produce(){
        if (turnsLeftToProduce == 0) {
            turnsLeftToProduce = type.getTurnsToProduce();
            return new Product(type.getProductType());
        }
        else
            return null;
    }

    public boolean canEat(){
        return getHealth() < getMaxHealth();
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
        if (!canEat()) {
            super.move(cornerPoint);
        }
//        else if()
    }

    @Override
    public void setTarget(ArrayList<Point> plants, Point cornerPoint) {
        if (getHealth() < getMaxHealth() / 3)
            super.setTarget(plants, cornerPoint);
    }

    @Override
    public String toString() {
        return type.toString();
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
