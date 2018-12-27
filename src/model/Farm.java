package model;

import model.exception.NameNotFoundException;
import model.exception.NotEnoughCapacityException;

import java.util.ArrayList;
import java.util.Random;
public class Farm {

    private static int TURN_TIME = 1;
    private static final int WELL_DEFAULT_CAPACITY = 30;
    private Map map   = new Map();
    private Workshop[] workshops= new Workshop[7];
    private transient Truck truck = new Truck();
    private transient Warehouse warehouse = new Warehouse();
    private transient Helicopter helicopter = new Helicopter();
    private transient Well well = new Well(WELL_DEFAULT_CAPACITY);

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
        warehouse.addProduct(map.updateMap(warehouse.getCapacity()));
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

    public void pickup(Point point) throws NotEnoughCapacityException {
        Cell cell = map.getCell(point);
        if (warehouse.getCapacity() < cell.calculateDepotSize())
            throw new NotEnoughCapacityException();
        warehouse.addProduct(map.getCell(point).removeProducts());
    }

    public Workshop getWorkshopByName(String name) throws NameNotFoundException {

        for (Workshop workshop : workshops) {
            if(workshop.getName().equals(name)){
                return workshop;
            }
        }
        throw new NameNotFoundException(name);
    }

    public Map getMap() {
        return map;
    }

    public Cell getCell(Point point){
        return map.getCell(point);
    }

    public void setCustomWorkshop(int number, Workshop workshop) {
        workshops[number] = workshop;
    }

    public Workshop getCustomWorkshop(int number) {
       return workshops[number];
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Vehicle getVehicleByName(VehicleType vehicleType){
        if(vehicleType.equals(VehicleType.TRUCK)){
            return truck;
        }
        return helicopter;

    }
}
