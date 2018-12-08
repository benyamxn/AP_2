import java.util.ArrayList;

public abstract class Animal {
    private Point location;

    public abstract void move();

    public void moveToPoint(Point point){
        Direction direction = selectDirection(point);
        Point moveVector = direction.getMoveVector();
//        location.add(moveVector);
    }

    private Direction selectDirection(Point point){
        ArrayList<Direction> possibleDirectins = new ArrayList<>();
        possibleDirectins.add(Direction.STATIONARY);

        if (point.getWidth() > location.getWidth()){
            possibleDirectins.add(Direction.RIGHT);
            if (point.getHeight() > location.getHeight()) {
                possibleDirectins.add(Direction.UP);
                possibleDirectins.add(Direction.UP_RIGHT);
            }
            else {
                possibleDirectins.add(Direction.DOWN);
                possibleDirectins.add(Direction.DOWN_RIGHT);
            }
        }
        else if (point.getWidth() < location.getWidth()) {
            possibleDirectins.add(Direction.LEFT);
            if (point.getHeight() > location.getHeight()) {
                possibleDirectins.add(Direction.UP);
                possibleDirectins.add(Direction.UP_LEFT);
            }
            else {
                possibleDirectins.add(Direction.DOWN);
                possibleDirectins.add(Direction.DOWN_LEFT);
            }
        }
        else {
            if (point.getHeight() > location.getHeight())
                possibleDirectins.add(Direction.UP);
            else if (point.getHeight() < location.getHeight())
                possibleDirectins.add(Direction.DOWN);
        }
        return Direction.randomDir((Direction[]) possibleDirectins.toArray());
    }
}
