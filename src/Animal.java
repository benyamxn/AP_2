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

    public abstract void move(Point cornerPoint);
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
