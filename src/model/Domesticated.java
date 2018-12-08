package model;

import java.util.ArrayList;

public class Domesticated extends Animal {

    private DomesticatedType type;
    private int health, maxHealth;
    private int eatenAmountOfFood;

    public Domesticated(Point location, DomesticatedType type, int maxHealth, int eatenAmountOfFood) {
        super(location);
        this.type = type;
        this.health = maxHealth;
        this.maxHealth = maxHealth;
        this.eatenAmountOfFood = eatenAmountOfFood;
    }

    public Domesticated(Point location) {
        super(location);
    }

    public Product produce(){
        return new Product(type.getProductType());
    }

    public void eat(){
        health += Math.ceil((double) maxHealth / 3);
    }

    @Override
    public void move(Point cornerPoint) {
        ArrayList<Direction> directions = Direction.getAllDirections();
        if (getLocation().getWidth() == 0) {
            directions.remove(Direction.LEFT);
            directions.remove(Direction.DOWN_LEFT);
            directions.remove(Direction.UP_LEFT);
        }
        if (getLocation().getWidth() == cornerPoint.getWidth()){
            directions.remove(Direction.RIGHT);
            directions.remove(Direction.DOWN_RIGHT);
            directions.remove(Direction.UP_RIGHT);
        }
        if (getLocation().getHeight() == 0){
            directions.remove(Direction.DOWN_LEFT);
            directions.remove(Direction.DOWN);
            directions.remove(Direction.DOWN_RIGHT);
        }
        if (getLocation().getHeight() == cornerPoint.getHeight()){
            directions.remove(Direction.UP);
            directions.remove(Direction.UP_RIGHT);
            directions.remove(Direction.UP_LEFT);
        }
        Direction dir = Direction.randomDir((Direction []) directions.toArray());
        moveWithDirection(dir);
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
