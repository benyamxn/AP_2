package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Cell {
    private Point coordinate;
    private LinkedList<Animal> animals = new LinkedList<>();
    private LinkedList<Product> products = new LinkedList<>();
    private int grassLevel = 0;
    private static final int GRASS_GROWING_RATE = 1;

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
    }

    public void eatGrass() {
        grassLevel--;
        if (grassLevel < 0)
            grassLevel = 0;
    }

    public void addProduct(Product product) {
        products.add(product);
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
            // TODO: check this
        }
        return new ArrayList<ProductType>().toArray(new ProductType[0]);
        // TODO: check this
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
                    if (grassLevel > 0) {
                        eatGrass();
                        ((Domesticated) animal).eat();
                    } else if (((Domesticated) animal).getHealth() < 0) {
                        iterator.remove();
                    }
                }
            }
        } else {
            Iterator<Animal> iterator = animals.iterator();
            while (iterator.hasNext()) {
                iterator.remove();
            }
        } // TODO: decide about here
    }

    public void updateWildAnimals() {
        boolean hasDog = false;
        Iterator<Animal> iterator = animals.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof Dog) {
                hasDog = true;
                iterator.remove();
            }
        }
        if (hasDog) {
            animals.removeIf(animal -> (animal instanceof Wild));
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


}
