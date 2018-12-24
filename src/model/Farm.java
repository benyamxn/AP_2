package model;

import java.util.ArrayList;
import java.util.Random;
public class Farm {

    private static int TURN_TIME = 1;
    private Map map;
    private Truck truck;
    private  Helicopter helicopter;
    private  Well well;

    public void placeProduct(ArrayList<Product> products){

        for (Product product : products) {
            Point point = getRandomPoint();
            map.getCell(point).addProduct(product);
        }
    }

    public  void placeAnimal(WildType wildType ){

        Point point = getRandomPoint();
        Animal newWild = new Wild(point,wildType);
        map.getCell(point).addAnimal(newWild);
    }

    public  void placeAnimal(DomesticatedType DomesticatedType ){

        Point point = getRandomPoint();
        Animal newDomesticated = new Domesticated(point,DomesticatedType);
        map.getCell(point).addAnimal(newDomesticated);
    }

    public  void placeAnimal(Dog dog){
        Point point = getRandomPoint();
        Animal newDog = new Dog(point);
        map.getCell(point).addAnimal(newDog);
    }

    public  void placeAnimal(Cat cat){
        Point point = getRandomPoint();
        Animal newCat = new Cat(point);
        map.getCell(point).addAnimal(newCat);
    }

    public Point getRandomPoint(){
        Random random  = new Random();
        return new Point(random.nextInt(map.getWidth()),random.nextInt(map.getHeight()));
    }

    public void updateFarm(){
        if(truck.isOnTravel())
                 truck.decreaseEstimatedTimeOfArrival(TURN_TIME);
        if(helicopter.isOnTravel())
                 helicopter.decreaseEstimatedTimeOfArrival(TURN_TIME);
        map.updateMap();

    }


}
