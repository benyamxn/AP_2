package model;


import GUI.FarmGUI;

import java.util.List;
import java.util.Random;

public class Domesticated extends Animal {

    private static final int HUNGRINESS = 10;
    private static final int APPETITE = 3;
    private DomesticatedType type;
    private int turnsLeftToProduce;

    public Domesticated(Point location, DomesticatedType type) {
        super(location);
        this.type = type;
        setMaxHealth(type.getMaxHealth());
        setHealth(type.getMaxHealth());
        turnsLeftToProduce = type.getTurnsToProduce();
        animalGUI.initImages();
    }

    public Domesticated(Point location) {
        super(location);
    }

    public Product produce(){
        if (turnsLeftToProduce == 0) {
            Random rand = new Random();
            if (rand.nextInt(100) <= 20) {
                turnsLeftToProduce = type.getTurnsToProduce();
                FarmGUI.getSoundPlayer().playTrack(this.toString() + " produce");
                return new Product(type.getProductType());
            }
        }
        return null;
    }

    public boolean canEat(){
        return getHealth() < getMaxHealth();
    }

    public void eat(){
        isEating = true;
        animalGUI.eat();
        addToHealth((int) Math.ceil((double) getMaxHealth() / 3));
        if (getHealth() >= getMaxHealth())
            setHealth(getMaxHealth());
    }

    public void decrementTurnsLeftToProduce(){
        turnsLeftToProduce--;
        if (turnsLeftToProduce < 0)
            turnsLeftToProduce = 0;
    }

    public boolean isHungry(){
        return 2 * getMaxHealth() > APPETITE * getHealth();
    }

    @Override
    public void move(Point cornerPoint) {
        reduceHealth();
        // Assuming that map calls this method when the location of the animal does not contain any grass
        if (isHungry() && hasATarget()) {
            moveToPoint(getTarget());
            animalGUI.move();
        } else {
            super.move(cornerPoint);
            // TODO: why???
        }
    }

    private void reduceHealth() {
        setHealth(getHealth() - getMaxHealth() / HUNGRINESS);
    }

    public DomesticatedType getType() {
        return type;
    }

    @Override
    public void setTarget(List<Point> plants, Point cornerPoint) {
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
