package model;

import GUI.AnimalGUI;
import GUI.MainStage;

import java.util.ArrayList;
import java.util.List;

public abstract class Animal {
    private int health, maxHealth;
    private Point location;
    private Point target;
    private boolean hasATarget;
    private transient AnimalGUI animalGUI;

    Animal(Point location) {
        this.location = location;
        hasATarget = false;
        animalGUI  = new AnimalGUI(this);
    }

    public abstract int getBuyPrice();

    public abstract int getSellPrice();

    public Point getLocation() {
        return location;
    }

    public void move(Point cornerPoint) {

        if (hasATarget) {
            moveToPoint(target);
            return;
        }

        ArrayList<Direction> possibleDirections = getPossibleDirections(cornerPoint);

        moveRandom(possibleDirections);
    }

    public void moveRandom(ArrayList<Direction> directions){
        Direction dir = Direction.randomDir(directions.toArray(new Direction[0]));
        moveWithDirection(dir);
    }

    private ArrayList<Direction> getPossibleDirections(Point cornerPoint){
        ArrayList<Direction> directions = Direction.getAllDirections();
        if (getLocation().getWidth() == 0) {
            directions.remove(Direction.LEFT);
            directions.remove(Direction.DOWN_LEFT);
            directions.remove(Direction.UP_LEFT);
        }
        if (getLocation().getWidth() == cornerPoint.getWidth() - 1) {
            directions.remove(Direction.RIGHT);
            directions.remove(Direction.DOWN_RIGHT);
            directions.remove(Direction.UP_RIGHT);
        }
        if (getLocation().getHeight() == 0) {
            directions.remove(Direction.DOWN_LEFT);
            directions.remove(Direction.DOWN);
            directions.remove(Direction.DOWN_RIGHT);
        }
        if (getLocation().getHeight() == cornerPoint.getHeight() - 1) {
            directions.remove(Direction.UP);
            directions.remove(Direction.UP_RIGHT);
            directions.remove(Direction.UP_LEFT);
        }
        return directions;
    }

    public void setTarget(List<Point> possibleTargets, Point cornerPoint) {
        if(possibleTargets.isEmpty()) {
            setRandomTarget(cornerPoint);
            hasATarget = false;         // so that the next time it moves normally.
            return;
        }
        target = possibleTargets.get(0);
        int distance = Integer.MAX_VALUE;
        for (Point point : possibleTargets) {
            int newDistance = getLocation().getOptimalDistance(point);
            if (distance > newDistance) {
                distance = newDistance;
                target = point;
            }
        }
        hasATarget = true;
    }

    public void setRandomTarget(Point cornerPoint){
        ArrayList<Direction> directions = getPossibleDirections(cornerPoint);
        Direction dir = Direction.randomDir(directions.toArray(new Direction[0]));
        Point newTarget = location;
        newTarget.add(dir.getMoveVector());
        setTarget(newTarget);
    }

    public void moveWithDirection(Direction direction) {
        Point moveVector = direction.getMoveVector();
        location.add(moveVector);
    }

    public void moveToPoint(Point point) {
        Direction direction = selectDirection(point);
        moveWithDirection(direction);
    }


    private Direction selectDirection(Point point) {
        return Direction.getOrientationByTarget(location, point);
    }

    public int getHealth() {
        return health;
    }

    public void addToHealth(int amount) {
        health += amount;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public Point getTarget() {
        return target;
    }

    public void setTarget(Point target) {
        this.target = target;
        hasATarget = true;
    }

    public void setHasATarget(boolean hasATarget) {
        this.hasATarget = hasATarget;
    }

    public void reachedTarget(){
        hasATarget = false;
    }

    public boolean hasATarget() {
        return hasATarget;
    }

    public String getStatus() {
        String answer = this.toString() + ":\n";
        answer += "Health: " + getHealth();
        answer += "Location: " + location.toString();

        return answer;
    }
}
