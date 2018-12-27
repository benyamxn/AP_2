package model;

import java.util.ArrayList;
import java.util.Random;

public class Cat extends Animal implements Upgradable {

    private static boolean smart;
    private static final int  BUY_PRICE = 2500;
    private static final int  SELL_PRICE = 1250;
    private static final int  UPGRADE_PRICE = 1000;
    public Cat(Point location) {
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
    public void upgrade() {
        smart = true;
    }

    @Override
    public int getUpgradePrice() {
        return UPGRADE_PRICE;
    }

    @Override
    public boolean canUpgrade() {
        return !smart;
    }

    @Override
    public int getBuyPrice() { return BUY_PRICE; }

    @Override
    public int getSellPrice() {
        return SELL_PRICE;
    }

    @Override
    public String toString() {
        return "Cat";
    }
}
