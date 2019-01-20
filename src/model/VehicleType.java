package model;

public enum VehicleType {
    HELICOPTER, TRUCK;


    @Override
    public String toString() {
        switch (this){

            case HELICOPTER:
                return "Helicopter";
            case TRUCK:
                return "Truck";
        }
        return "";
    }
}
