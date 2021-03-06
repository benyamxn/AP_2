package model;

import java.util.ArrayList;
import java.util.Random;

public enum Direction {
    LEFT, RIGHT, UP, DOWN, UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT, STATIONARY;

    public Point getMoveVector() {
        Point moveVector;
        switch (this) {
            case LEFT:
                moveVector = new Point(-1, 0);
                break;
            case RIGHT:
                moveVector = new Point(1, 0);
                break;
            case UP:
                moveVector = new Point(0, 1);
                break;
            case DOWN:
                moveVector = new Point(0, -1);
                break;
            case UP_RIGHT:
                moveVector = new Point(1, 1);
                break;
            case UP_LEFT:
                moveVector = new Point(-1, 1);
                break;
            case DOWN_RIGHT:
                moveVector = new Point(1, -1);
                break;
            case DOWN_LEFT:
                moveVector = new Point(-1, -1);
                break;
            default:
                moveVector = new Point(0, 0);
                break;
        }
        return moveVector;
    }

    public static Direction getOrientationByTarget (Point location, Point target){
        Direction direction = Direction.STATIONARY;
        if (target.getWidth() > location.getWidth()) {
            if (target.getHeight() > location.getHeight()) {
                direction = Direction.UP_RIGHT;
            } else if (target.getHeight() < location.getHeight()) {
                direction = Direction.DOWN_RIGHT;
            } else
                direction = Direction.RIGHT;
        } else if (target.getWidth() < location.getWidth()) {
            if (target.getHeight() > location.getHeight()) {
                direction = Direction.UP_LEFT;
            } else if (target.getWidth() > location.getWidth()) {
                direction = Direction.DOWN_LEFT;
            } else
                direction = Direction.LEFT;
        } else {
            if (target.getHeight() > location.getHeight())
                direction = Direction.UP;
            else if (target.getHeight() < location.getHeight())
                direction = Direction.DOWN;
        }
        return direction;
    }

    public static Direction randomDir(Direction[] directions) {
        Random rand = new Random();
        return directions[rand.nextInt(directions.length)];
    }


    public static ArrayList<Direction> getAllDirections() {
        ArrayList<Direction> answer = new ArrayList<>();
        answer.add(LEFT);
        answer.add(RIGHT);
        answer.add(UP);
        answer.add(DOWN);
        answer.add(UP_RIGHT);
        answer.add(UP_LEFT);
        answer.add(DOWN_LEFT);
        answer.add(DOWN_RIGHT);
        answer.add(STATIONARY);
        return answer;
    }

    @Override
    public String toString() {
        String str = "";
        switch (this){
            case LEFT:
                str = "left";
                break;
            case RIGHT:
                str = "right";
                break;
            case UP:
                str = "up";
                break;
            case DOWN:
                str = "down";
                break;
            case UP_RIGHT:
                str = "up right";
                break;
            case UP_LEFT:
                str = "up left";
                break;
            case DOWN_RIGHT:
                str = "down right";
                break;
            case DOWN_LEFT:
                str = "down left";
                break;
            case STATIONARY:
                str = "stationary";
                break;
        }
        return str;
    }
}
