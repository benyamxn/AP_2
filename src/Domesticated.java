public class Domesticated extends Animal {

    Domesticated(Point location) {
        super(location);
    }

    @Override
    public void move() {

    }

    @Override
    public int getBuyPrice() {
        return 0;
    }

    @Override
    public int getSellPrice() {
        return 0;
    }
}
