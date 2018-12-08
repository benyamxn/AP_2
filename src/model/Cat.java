package model;

import java.util.ArrayList;
import java.util.Random;

public class Cat extends Animal {

    private boolean smart;

    Cat(Point location) {
        super(location);
        smart = false;
    }

    @Override
    public void setTarget(ArrayList<Point> possibleTargets) {
        if (smart) {
            super.setTarget(possibleTargets);
            return;
        }
        if (!hasATarget()) {
            Random randomTarget = new Random();
            int targetIndex = randomTarget.nextInt(possibleTargets.size());
            setTarget(possibleTargets.get(targetIndex));
        }
    }

    @Override
    public int getBuyPrice() {
        return 2500;
    }

    @Override
    public int getSellPrice() {
        return 1250;
    }
}
