package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Map {

    private int width;
    private int height;
    private Cell[][] cells;
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

    public ProductType[]  updateMap(){
        LinkedList<Animal> mapAnimals  = new LinkedList<>();
        Point cornerPoint = new Point(width,height);
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                for (Animal animal : cells[i][j].getAnimals()) {
                        animal.move(cornerPoint);
                        mapAnimals.add(animal);
                }
                cells[i][j].setAnimals(null);
            }
        }

        for (Animal animal : mapAnimals) {
            cells[animal.getLocation().getWidth()][animal.getLocation().getHeight()].addAnimal(animal);
        }
        List<ProductType> newProduct = new LinkedList<>();
        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j].updateWildAnimals();
                cells[i][j].updateDomesticatedAnimals();
                cells[i][j].updateAddProducts();
                newProduct.addAll(Arrays.asList(cells[i][j].updateRemoveProducts()));
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

        return newProduct.toArray(new ProductType[newProduct.size()]);
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
