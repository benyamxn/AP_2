package model;

public class Dog extends Animal {

    public Dog(Point location) {
        super(location);
    }

    @Override
    public int getBuyPrice() {
        return 2600;
    }

    @Override
    public int getSellPrice() {
        return 1300;
    }

    @Override
    public String toString() {
        return "Dog";
    }
}
