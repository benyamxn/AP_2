package GUI;

import model.Animal;
import model.Cell;
import model.Farm;

public class DurationManager {

    private static double rate = 1;
    public static  double pauseRate = 0;
    public static  double resumeRate = -1;
    private FarmGUI farmGUI;

    public DurationManager(FarmGUI farmGUI) {
        this.farmGUI = farmGUI;
    }


    public void pause(){
        setRate(pauseRate);
    }

    public void resume(){setRate(resumeRate);}

    public void setRate(double rate){
        if(rate != DurationManager.pauseRate && rate != DurationManager.resumeRate ){
            farmGUI.getGameUpdater().setRate(rate);
        }
        DurationManager.rate = rate;
        setCellsGUI(rate);
        setFarmCity(rate);
        setWorkshops(rate);
        setAnimal(rate);
        setWell(rate);
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
