package model;

public class Wild extends Animal {

    private int cagedLevel = 0;
    private WildType type;

    Wild(Point location) {
        super(location);
    }

    Wild(Point location, WildType type){
        super(location);
        this.type = type;
    }

    @Override
    public void move(Point cornerPoint) {
        if (!isCaged())
            super.move(cornerPoint);
    }

    @Override
    public int getBuyPrice() {
        return 0;
    }

    @Override
    public int getSellPrice() {
        return type.getSellPrice();
    }

    public int getCagedLevel() {
        return cagedLevel;
    }

    public void incrementCagedLevel(){
        cagedLevel++;
    }

    public boolean isCaged(){
        return cagedLevel == 5;
    }

    public ProductType returnCagedProduct(){
        return type.returnCagedType();
    }

    public void setCagedLevel(int cagedLevel) {
        this.cagedLevel = cagedLevel;
    }
}