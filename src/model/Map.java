package model;

import java.util.ArrayList;

public class Map {


    //TODO Workshop

    private int width;
    private int height;
    private Cell[][] cells;
    private Warehouse warehouse = new Warehouse();
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
        ArrayList<Point> catCollectablePoints = new ArrayList<Point>();

        //TODO:set points for cats and dogs
//        for(int i = 0; i < width; i++) {
//            for (int j = 0; j < height; j++) {
//
//            }
//        }


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
