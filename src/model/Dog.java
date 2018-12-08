package model;

import java.util.ArrayList;
import java.util.Collection;

public class Dog extends Animal {

    Dog(Point location) {
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
}
