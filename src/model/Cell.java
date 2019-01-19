package model;

import GUI.CellGUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class Cell {
    private Point coordinate;
    private LinkedList<Animal> animals = new LinkedList<>();
    private LinkedList<Product> products = new LinkedList<>();
    private int grassLevel = 0;
    private CellGUI cellGUI;

    public void setCellGUI(CellGUI cellGUI) {
        this.cellGUI = cellGUI;
    }

    private static final int GRASS_GROWING_RATE = 4;


    public Cell() {
    }

    public Cell(Point coordinate) {
        this.coordinate = coordinate;
    }

    public Point getCoordinate() {
        return coordinate;
    }

    public void growGrass() {
        grassLevel += GRASS_GROWING_RATE;

        if(grassLevel > GRASS_GROWING_RATE * 4){
            grassLevel = GRASS_GROWING_RATE * 4;
        }
    }

    public void eatGrass() {
        grassLevel--;
        if (grassLevel < 0)
            grassLevel = 0;
    }
    public double calculateDepotSize() {
        double size = 0;
        for (Product product : products) {
            size += product.getType().getDepotSize();
        }
        return size;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void addProduct(Product[] products) {
        this.products.addAll(Arrays.asList(products));
    }

    public ProductType[] updateRemoveProducts() {
        for (Animal animal : animals) {
            if (animal instanceof Cat) {
                return removeProducts();
            }
        }
        Iterator<Product> iterator = products.iterator();
        while(iterator.hasNext()) {
            Product temp = iterator.next();
            temp.decreaseTimeLeftToExpire(1);
            if(temp.isExpired())
                iterator.remove();
        }
        return new ArrayList<ProductType>().toArray(new ProductType[0]);
    }

    public ProductType[] removeProducts() {
        ProductType[] temp = new ProductType[products.size()];
        int counter = 0;
        for (Product product : products) {
            temp[counter] = product.getType();
            counter++;
        }
        products = new LinkedList<>();
        return temp;
    }

    public void updateAddProducts() {
        for (Animal animal : animals) {
            if (animal instanceof Domesticated) {
                Domesticated domesticated = (Domesticated) animal;
                Product temp = domesticated.produce();
                if (temp != null) {
                    products.add(temp);
                }
            }
        }
    }

    public void updateDomesticatedAnimals() {
        if (!hasAWildAnimal()) {
            Iterator<Animal> iterator = animals.iterator();
            while (iterator.hasNext()) {
                Animal animal = iterator.next();
                if (animal instanceof Domesticated) {
                    ((Domesticated) animal).decrementTurnsLeftToProduce();
                    if (grassLevel > 0 && ((Domesticated) animal).canEat()) {
                        eatGrass();
                        ((Domesticated) animal).eat();
                    } else if (((Domesticated) animal).getHealth() < 0) {
                        deleteFromGUI(animal);
                        iterator.remove();
                    }
                }
            }
        } else {
            if(getDomesticatedAnimals().length > 0)
                    cellGUI.battle();
            deleteFromGUI(getDomesticatedAnimals());
            animals.removeAll(Arrays.asList(getDomesticatedAnimals()));
        }
    }

    public void updateWildAnimals() {
        boolean hasDog = false;
        boolean hasWild = hasAWildAnimal();
        Iterator<Animal> iterator = animals.iterator();
        Animal temp;
        while (iterator.hasNext()) {
            if ((temp = iterator.next()) instanceof Dog) {
                hasDog = true;
                if (hasWild) {
                    deleteFromGUI(temp);
                    iterator.remove();
                }
            }
        }
        if(hasWild && hasDog){
            cellGUI.battle();
        }
        if (hasDog) {
            for (Animal animal : animals) {
                if(animal instanceof Wild ){
                    deleteFromGUI(animal);
                }
            }
            animals.removeIf(animal -> (animal instanceof Wild));
        }
    }

    public void deleteFromGUI(Animal animal){
        deleteFromGUI(new Animal[]{animal});
    }

    public void deleteFromGUI(Animal[] animals){

        for (Animal animal : animals) {
            animal.getAnimalGUI().dead();
        }
    }

    public void cage() {
        Iterator<Animal> iterator = animals.iterator();
        while (iterator.hasNext()) {
            Animal animal = iterator.next();
            if (animal instanceof Wild) {
                Wild wild = (Wild) animal;
                wild.incrementCagedLevel();
                if (wild.isCaged()) {
                    iterator.remove();
                    products.add(new Product(wild.returnCagedProduct()));
                }
            }
        }

    }

    public boolean hasAWildAnimal() {
        for (Animal animal : animals) {
            if (animal instanceof Wild)
                return true;
        }
        return false;
    }

    public boolean hasACatCollectable() {
        for (Product product : products) {
            if(!product.getType().name().startsWith("CAGED"))
                return true;
        }
        return false;
    }

    public Cat[] getCats() {
        LinkedList<Cat> temp = new LinkedList<>();
        Cat[] cats = new Cat[0];
        for (Animal animal : animals) {
            if (animal instanceof Cat)
                temp.add(((Cat) animal));
        }
        return temp.toArray(cats);
    }

    public Dog[] getDogs() {
        LinkedList<Dog> temp = new LinkedList<>();
        Dog[] dogs = new Dog[0];
        for (Animal animal : animals) {
            if (animal instanceof Dog)
                temp.add(((Dog) animal));
        }
        return temp.toArray(dogs);
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }
    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }
    public Animal[] getAnimals() {
        Animal[] animals = new Animal[this.animals.size()];
        return this.animals.toArray(animals);
    }

    public Domesticated[] getDomesticatedAnimals() {
        LinkedList<Domesticated> domesticatedAnimals = new LinkedList<>();
        for (Animal animal : animals) {
            if (animal instanceof Domesticated)
                domesticatedAnimals.add(((Domesticated) animal));
        }
        return domesticatedAnimals.toArray(new Domesticated[0]);
    }

    public boolean hasGrass() {
        return grassLevel > 0;
    }

    @Override
    public String toString() {
        String str;
        if (!animals.isEmpty()){
            if (!products.isEmpty())
                str = "&";
            else
                str = "$";
        }
        else if (!products.isEmpty()) {
            str = "^";
        }
        else
            str = "*";
        if (str.equals("*") && grassLevel > 0)
            str = String.valueOf(grassLevel);
        return str;
    }

    public int[] getNumberOfEachAnimal() {
        int counter[] = {0, 0, 0, 0};
        for (Animal animal : animals) {
            if (animal instanceof Domesticated)
                counter[0]++;
            if (animal instanceof Dog)
                counter[1]++;
            if (animal instanceof Cat)
                counter[2]++;
        }
        counter[3] = animals.size() - counter[0] - counter[1] - counter[2];
        return counter;
    }

    public void setAnimals(LinkedList<Animal> animals) {
        this.animals = animals;
    }

    public int getGrassLevel() {
        return grassLevel;
    }

    public boolean hasProduct(){
        return !products.isEmpty();
    }

    public boolean canGrow(){
        return grassLevel < 4 * GRASS_GROWING_RATE;
    }
}
