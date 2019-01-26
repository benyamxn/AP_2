package model;

public class Wild extends Animal {

    private int cagedLevel = 0;
    private WildType type;

    Wild(Point location) {
        super(location);
    }

    public Wild(Point location, WildType type){
        super(location);
        this.type = type;
        animalGUI.initImages();
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
        if (!isCaged()) {
            cagedLevel++;
            animalGUI.cage();
        }
    }

    public boolean isCaged(){
        return cagedLevel == 7;
    }

    public ProductType returnCagedProduct(){
        return type.returnCagedType();
    }

    public void setCagedLevel(int cagedLevel) {
        if (!isCaged()) {
            animalGUI.cage();
            this.cagedLevel = cagedLevel;
        }
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
