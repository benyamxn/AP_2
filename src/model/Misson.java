package model;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
public class Misson implements  Comparable<Misson> {

    private int moneyGoal;
    private int timeGoal;
    private  Map<ProductType,Integer>  productsGoal = new HashMap<>();

    Misson(int moneyGoal,int timeGoal,Map productsGoal ){
        this.moneyGoal = moneyGoal;
        this.timeGoal = timeGoal;
        this.productsGoal = productsGoal;
    }

    public int getMoneyGoal() {
        return moneyGoal;
    }

    public int getTimeGoal() {
        return timeGoal;
    }

    public Map<ProductType, Integer> getProductsGoal() {
        return productsGoal;
    }

    public Map<ProductType,Integer> productsGoal(){
        return productsGoal;
    }

    @Override
    public int compareTo(Misson second){
        if(this.getTimeGoal() <= second.getTimeGoal() ){
            if(this.getMoneyGoal() >= second.getMoneyGoal()){
                for (Map.Entry<ProductType, Integer> entry : productsGoal.entrySet()){
                    if(entry.getValue() < second.productsGoal.get(entry.getKey())){
                        return 0;
                    }
                }
                return 1;
            }
        }
        return 0;
    }

}
