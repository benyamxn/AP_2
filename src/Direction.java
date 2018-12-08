import org.junit.Assert;

import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.Random;

public enum Direction {
    LEFT, RIGHT, UP, DOWN, UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT, STATIONARY;

    public Point getMoveVector(){
        Point moveVector;
        switch (this){
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

    public static Direction randomDir(Direction[] directions){
        Random rand = new Random();
        return directions[rand.nextInt(directions.length)];
    }

    public static ArrayList<Direction> getAllDirections(){
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
}
