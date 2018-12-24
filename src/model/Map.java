package model;

import java.util.ArrayList;

public class Map {


    //TODO Workshop

    private int width;
    private int height;
    private Cell[][] cells;
    private Warehouse warehouse = new Warehouse();
    public Map(){

    }
    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        constructCells();
    }

    public void  setCells(Cell[][] cells){
        this.cells = cells;
    }

    public void constructCells(){
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = new Cell(new Point(i,j));
            }
        }
    }

    public void  updateMap(){
        Point cornerPoint = new Point(width,height);
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                for (Animal animal : cells[i][j].getAnimals()) {
                        animal.move(cornerPoint);
                }
            }
        }
        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j].updateWildAnimals();
                cells[i][j].updateDomesticatedAnimals();
                cells[i][j].updateAddProducts();
            }
        }
        ArrayList<Point> catCollectablePoints = new ArrayList<>();
        ArrayList<Point> wildAnimalPoints = new ArrayList<>();
        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(cells[i][j].hasACatCollectable()){
                    catCollectablePoints.add(cells[i][j].getCoordinate());
                }
                if(cells[i][j].hasAWildAnimal()){
                    wildAnimalPoints.add(cells[i][j].getCoordinate());
                }
            }
        }

        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (Cat cat : cells[i][j].getCats()) {
                    cat.setTarget(catCollectablePoints);
                }
                for (Dog dog : cells[i][j].getDogs()) {
                    dog.setTarget(wildAnimalPoints);
                }
            }
        }

    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public Cell getCell(Point point){
        return cells[point.getWidth()][point.getHeight()];
    }

    public void plant(Point point){
        cells[point.getWidth()][point.getHeight()].growGrass();
    }
}
