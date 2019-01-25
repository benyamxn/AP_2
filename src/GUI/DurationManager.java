package GUI;

import model.Animal;
import model.Cell;

public class DurationManager {

    private static double rate = 1;
    private FarmGUI farmGUI;

    public DurationManager(FarmGUI farmGUI) {
        this.farmGUI = farmGUI;
    }


    public void pause(){
        setRate(0);
    }

    public void setRate(double rate){
        DurationManager.rate = rate;
        setCellsGUI(rate);
        setFarmCity(rate);
        setWorkshops(rate);
        setAnimal(rate);
        farmGUI.getGameUpdater().setRate(rate);
    }

    public static double getRate() {
        return rate;
    }

    public void setAnimal(double rate){
        Cell[][] cells = farmGUI.getFarm().getMap().getCells();
        for (int i = 0; i < FarmGUI.WIDTH; i++) {
            for (int j = 0; j < FarmGUI.HEIGHT; j++) {
                for (Animal animal : cells[i][j].getAnimals()) {
                    animal.getAnimalGUI().setRate(rate);
                }
            }
        }

    }
    public void setWell(double rate) {
        farmGUI.getFarm().getWell().getWellGUI().setRate(rate);
    }
    public void setFarmCity(double rate){
        farmGUI.getFarmCityView().setRate(rate);
    }
    public void setCellsGUI(double rate){
        CellGUI[][] cells = farmGUI.getCellGUIs();
        for (int i = 0; i < FarmGUI.WIDTH; i++) {
            for(int j = 0; j < FarmGUI.HEIGHT; j++){
                cells[i][j].setRate(rate);
            }
        }
    }
    public void setWorkshops(double rate){
        for (WorkshopGUI workshopGUI : farmGUI.getWorkshopGUIS()) {
            workshopGUI.setRate(rate);
        }
    }
}
