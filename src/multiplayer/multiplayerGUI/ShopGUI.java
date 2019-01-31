package multiplayer.multiplayerGUI;

import com.sun.jdi.IntegerValue;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.Product;
import model.ProductType;
import multiplayer.Player;
import multiplayer.multiplayerModel.Shop;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.StringJoiner;

public class ShopGUI {


    private double width;
    private double height;
    private Shop shop;
    private TableView<ProductType> table = new TableView<>();
    private final ObservableList<ProductType> observableList = FXCollections.observableArrayList();

    public ShopGUI(Shop shop,double width,double height) {
        this.shop = shop;
        this.width = width;
        this.height = height;
        fillTableObservableList(shop.getProducts());
        table.setItems(observableList);
        table.setEditable(true);
        createName();
        createSale();
        createBuy();
        createStock();
    }

    private void createName(){

        TableColumn<ProductType, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(param ->  new SimpleStringProperty(param.getValue().toString()));
        nameColumn.setResizable(false);
        nameColumn.setReorderable(false);
        nameColumn.setPrefWidth(0.3 * width);
        table.getColumns().add(nameColumn);
    }
    private void createStock(){
        TableColumn<ProductType, Integer> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(param ->  new SimpleIntegerProperty(shop.getProducts().get(param.getValue())).asObject());
        stockColumn.setResizable(false);
        stockColumn.setReorderable(false);
        stockColumn.setPrefWidth(0.2 * width);
        table.getColumns().add(stockColumn);
    }

    private void createSale(){


        TableColumn<ProductType, String> sellColumn = new TableColumn<>("SalePrice");

        sellColumn.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getSaleCost())));
        sellColumn.setCellFactory(TextFieldTableCell.<ProductType>forTableColumn());
        sellColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductType, String>>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent<ProductType, String> event) {
                try{
                    event.getTableView().getItems().get(
                            event.getTablePosition().getRow()).setSaleCost(Integer.parseInt(event.getNewValue()));
                } catch (Exception e){

                }
            }
        });
        sellColumn.setResizable(false);
        sellColumn.setReorderable(false);
        sellColumn.setPrefWidth(0.2 * width);
        table.getColumns().add(sellColumn);

    }

    private void createBuy(){
        TableColumn<ProductType, String> buyColumn = new TableColumn<>("BuyPrice");
        buyColumn.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getBuyCost())));
        buyColumn.setCellFactory(TextFieldTableCell.<ProductType>forTableColumn());
        buyColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductType, String>>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent<ProductType, String> event) {
                try{
                    event.getTableView().getItems().get(
                            event.getTablePosition().getRow()).setBuyCost(Integer.parseInt(event.getNewValue()));
                } catch (Exception e){

                }
            }
        });
        buyColumn.setResizable(false);
        buyColumn.setReorderable(false);
        buyColumn.setPrefWidth(0.2 * width);
        table.getColumns().add(buyColumn);
    }

    public void fillTableObservableList(EnumMap<ProductType,Integer> enumMap) {
        for (Map.Entry<ProductType, Integer> product : enumMap.entrySet()) {
            observableList.add(product.getKey());
        }
    }

    public void relocate(double x, double y) {
        table.relocate(x, y);
    }

    public void addToPane(Pane pane){
        pane.getChildren().add(table);
    }

    public void updateProduct(){
        table.refresh();
    }
}
