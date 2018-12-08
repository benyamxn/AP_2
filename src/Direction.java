import java.nio.channels.Pipe;
import java.util.Random;

public enum Direction {
    LEFT, RIGHT, UP, DOWN, UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT, STATIONARY;

    private Point moveVector;

    Direction(){
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
            case STATIONARY:
                moveVector = new Point(0, 0);
                break;
        }
    }

    static Direction randomDir(Direction[] directions){
        Random rand = new Random();
        return directions[rand.nextInt(directions.length)];
    }

    public Point getMoveVector() {
        return moveVector;
    }
}
