package model;

import java.util.ArrayList;
import java.util.Collection;

public class Dog extends Animal {

    private Point target;


    Dog(Point location) {
        super(location);
    }

    public void setTarget(ArrayList<Point> pointsWithWildAnimal){
        target = pointsWithWildAnimal.get(0);
        int distance = Integer.MAX_VALUE;
        for (Point point : pointsWithWildAnimal) {
            int newDistance = getLocation().getOptimalDistance(point);
            if (distance > newDistance){
                distance = newDistance;
                target = point;
            }
        }
    }

    @Override
    public void move(Point cornerPoint) {
        if (target != null)
            moveToPoint(target);
        else
            super.move(cornerPoint);
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
