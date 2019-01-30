package multiplayer.multiplayerGUI;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import multiplayer.Player;
import java.util.ArrayList;

public class LeaderboardTable {
    private double width;
    private double height;
    private final TableView<PlayerRow> table = new TableView<>();
    private final ObservableList<PlayerRow> observableList = FXCollections.observableArrayList();

    public LeaderboardTable(ArrayList<Player> players, double width, double height) {
        this.width = width;
        this.height = height;
        setTableAppearance();
        fillTableObservableList(players);
        table.setItems(observableList);
        createIdColumn();
        createLevelColumn();
        createMoneyColumn();
        table.setEditable(false);
//        MainStage.getInstance().getScene().getStylesheets().add(getClass().
//                getResource("CSS/truckTable.css").toExternalForm());
        // TODO: create appropriate CSS file.
    }

    private void createIdColumn() {
        TableColumn<PlayerRow, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setResizable(false);
        idColumn.setReorderable(false);
        idColumn.setPrefWidth(0.6 * width);
        table.getColumns().add(idColumn);
    }

    private void createMoneyColumn() {
        TableColumn<PlayerRow, Integer> moneyColumn = new TableColumn<>("Money");
        moneyColumn.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getMoney()).asObject());
        moneyColumn.setResizable(false);
        moneyColumn.setReorderable(false);
        moneyColumn.setPrefWidth(0.2 * width);
        table.getColumns().add(moneyColumn);
    }

    private void createLevelColumn() {
        TableColumn<PlayerRow, Integer> columnLevel = new TableColumn<>("Level");
        columnLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
        columnLevel.setResizable(false);
        columnLevel.setReorderable(false);
        columnLevel.setPrefWidth(0.2 * width);
        table.getColumns().add(columnLevel);
    }

    private void setTableAppearance() {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefWidth(width);
        table.setPrefHeight(height);
    }

    private void fillTableObservableList(ArrayList<Player> players) {
        for (Player player : players) {
            observableList.add(new PlayerRow(player));
        }
    }

    public class PlayerRow {
        private String id;
        private int money;
        private int level;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public PlayerRow(Player player) {
            id = player.getId();
            money = player.getMoney();
            level = player.getLevel();
        }
    }

    public void relocate(double x, double y) {
        table.relocate(x, y);
    }

    public void addToRoot(Pane pane) {
        pane.getChildren().add(table);
    }
}
