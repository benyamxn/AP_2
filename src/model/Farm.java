package model;

import java.util.ArrayList;
import java.util.Random;
public class Farm {

    private static int TURN_TIME = 1;
    private static int CAPACITY = 30;
    private Map map   = new Map();
    private Truck truck = new Truck();
    private Workshop[] workshops= new Workshop[7];
    private Warehouse warehouse = new Warehouse();
    private  Helicopter helicopter = new Helicopter();
    private  Well well = new Well(CAPACITY);

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
        for (Workshop workshop : workshops) {
            if(workshop.isOnProduction()){
                if(workshop.isProductionEnded()){
                    map.getCell(workshop.getProductionPoint()).addProduct(workshop.produce());
                }
            }
        }
        warehouse.addProduct(map.updateMap());
    }

    public void startWorkshop(Workshop workshop){
        if(! workshop.isProductionEnded()){
            if(warehouse.hasProducts(workshop.getNeededProducts())){
                workshop.startProduction();
                warehouse.removeProducts(workshop.getNeededProducts());
            }
        }
    }

    public Well getWell() {
        return well;
    }

    public Truck getTruck() {
        return truck;
    }

    public Helicopter getHelicopter() {
        return helicopter;
    }
}
