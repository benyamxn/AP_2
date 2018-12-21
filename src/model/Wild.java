package model;

public class Wild extends Animal {
    private int cagedLevel = 0;

    Wild(Point location) {
        super(location);
    }

    @Override
    public int getBuyPrice() {
        return 0;
    }

    @Override
    public int getSellPrice() {
        return 0;
    }

    public int getCagedLevel() {
        return cagedLevel;
    }

    public void setCagedLevel(int cagedLevel) {
        this.cagedLevel = cagedLevel;
    }
}
