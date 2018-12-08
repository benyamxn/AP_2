package model;

import java.util.ArrayList;

public abstract class Animal {
    private Point location;

    Animal(Point location){
        this.location = location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Point getLocation() {
        return location;
    }

    public void move(Point cornerPoint){
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
    public abstract int getBuyPrice();
    public abstract int getSellPrice();

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
