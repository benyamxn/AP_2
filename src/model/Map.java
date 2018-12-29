package model;

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
        cells = new Cell[width][height];
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

    public ProductType[]  updateMap(double  warehouseCapacity){

        LinkedList<Point> catCollectablePoints = new LinkedList<>();
        LinkedList<Point> wildAnimalPoints = new LinkedList<>();
        LinkedList<Point> grassPoints  = new LinkedList<>();
        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(cells[i][j].hasACatCollectable()){
                    catCollectablePoints.add(cells[i][j].getCoordinate());
                }
                if(cells[i][j].hasAWildAnimal()){
                    wildAnimalPoints.add(cells[i][j].getCoordinate());
                }
                if(cells[i][j].hasGrass()){
                    grassPoints.add(cells[i][j].getCoordinate());
                }
            }
        }

        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (Cat cat : cells[i][j].getCats()) {
                    cat.setTarget(catCollectablePoints, getCornerPoint());
                }
                for (Dog dog : cells[i][j].getDogs()) {
                    dog.setTarget(wildAnimalPoints, getCornerPoint());
                }
                for (Domesticated domesticatedAnimal : cells[i][j].getDomesticatedAnimals()) {
                    domesticatedAnimal.setTarget(grassPoints,getCornerPoint());
                }
            }
        }

        LinkedList<Animal> mapAnimals  = new LinkedList<>();
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                for (Animal animal : cells[i][j].getAnimals()) {
                    if(cells[i][j].hasGrass() && animal instanceof Domesticated)
                        continue;
                    animal.move(getCornerPoint());
                    mapAnimals.add(animal);
                }
                cells[i][j].setAnimals(new LinkedList<>());
            }
        }

        for (Animal animal : mapAnimals) {
            if (animal != null) {
                cells[animal.getLocation().getWidth()][animal.getLocation().getHeight()].addAnimal(animal);
            }

        }
        List<ProductType> newProduct = new LinkedList<>();
        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j].updateWildAnimals();
                cells[i][j].updateDomesticatedAnimals();
                cells[i][j].updateAddProducts();
                if (warehouseCapacity >= cells[i][j].calculateDepotSize())
                    newProduct.addAll(Arrays.asList(cells[i][j].updateRemoveProducts()));
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


    public void plant(List<Cell> cells){
        for (Cell cell : cells) {
            cell.growGrass();
        }
    }
    public LinkedList<Cell > getCellsForPlant(Point point){
        LinkedList<Cell> cellLinkedList = new LinkedList<>();
        for(int i = point.getWidth() - 1 ; i < point.getWidth() + 2; i++){
            for(int j = point.getHeight() - 1; j < point.getHeight() + 2; j++){
                if(i >= 0 && j >= 0 && i < width && j < height ){
                    cellLinkedList.add(cells[i][j]);
                }
            }
        }
        return cellLinkedList;
    }

    private int[] getNumberOfEachAnimal(){
        int[] ans = new int[4];
        for (Cell[] cell : cells) {
            for (Cell cell1 : cell) {
                for (int i = 0; i < 4; i++)
                    ans[i] += cell1.getNumberOfEachAnimal()[i];
            }
        }
        return ans;
    }

    public Point getCornerPoint(){
        return new Point(width, height);
    }

    public String getStatus() {
        String status = "Map:\n" + "\t*: Contains nothing\t^: Contains Only Products\t$" +
                ": Contains Only Animals\t&: Contains both animals and products\tNumber: Level of existing Grass (if any)\n\n";
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){

                status += cells[x][y].toString();
                if (x == width - 1)
                    status += "\n";
            }
        }
        int[] numberOfAnimals = getNumberOfEachAnimal();
        status += "\n# of Domesticated Animals: " + numberOfAnimals[0] + "\t# of Wild Animals: " + numberOfAnimals[3] +
                "\t# of Dogs: " + numberOfAnimals[1] + "\t# of Cats: " + numberOfAnimals[2] + "\n";
        return status;
    }
}
