package model;

import java.util.ArrayList;

public abstract class Animal {
    public boolean hasATarget() {
        return hasATarget;
    }

    private Point location;
    private Point target;

    public void setHasATarget(boolean hasATarget) {
        this.hasATarget = hasATarget;
    }

    private boolean hasATarget;


    public Point getTarget() {
        return target;
    }

    public void setTarget(Point target) {
        this.target = target;
        hasATarget = true;
    }

    Animal(Point location){
        this.location = location;
        hasATarget = false;
    }

    public abstract int getBuyPrice();
    public abstract int getSellPrice();

    public Point getLocation() {
        return location;
    }

    public void move(Point cornerPoint){

        if (hasATarget) {
            moveToPoint(target);
            return;
        }

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

    public void setTarget(ArrayList<Point> possibleTargets){
        target = possibleTargets.get(0);
        int distance = Integer.MAX_VALUE;
        for (Point point : possibleTargets) {
            int newDistance = getLocation().getOptimalDistance(point);
            if (distance > newDistance){
                distance = newDistance;
                target = point;
            }
        }
        hasATarget = true;
    }

    public void moveWithDirection(Direction direction){
        Point moveVector = direction.getMoveVector();
        location.add(moveVector);
    }

    public void moveToPoint(Point point){
        Direction direction = selectDirection(point);
        moveWithDirection(direction);
    }

    private Direction selectDirection(Point point){
        Direction direction = Direction.STATIONARY;
        if (point.getWidth() > location.getWidth()){
            if (point.getHeight() > location.getHeight()) {
                direction = Direction.UP_RIGHT;
            }
            else if (point.getHeight() < location.getHeight()){
                direction = Direction.DOWN_RIGHT;
            }
            else
                direction = Direction.RIGHT;
        }
        else if (point.getWidth() < location.getWidth()) {
            if (point.getHeight() > location.getHeight()) {
                direction = Direction.UP_LEFT;
            }
            else if (point.getWidth() > location.getWidth()) {
                direction = Direction.DOWN_LEFT;
            }
            else
                direction = Direction.LEFT;
        }
        else {
            if (point.getHeight() > location.getHeight())
                direction = Direction.UP;
            else if (point.getHeight() < location.getHeight())
                direction = Direction.DOWN;
        }
        return direction;
    }

}
