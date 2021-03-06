package multiplayer.multiplayerGUI;

import GUI.MainStage;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Product;
import multiplayer.ClientSenderThread;
import multiplayer.Player;
import multiplayer.ServerSenderThread;
import multiplayer.multiplayerModel.messages.ProfileRequestMessage;
import multiplayer.server.Server;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardTable {
    private double width;
    private double height;
    private final TableView<PlayerRow> table = new TableView<>();
    private final ObservableList<PlayerRow> observableList = FXCollections.observableArrayList();
    private boolean isServer = false;
    private Server server;
    private TableColumn<PlayerRow, String> idColumn = new TableColumn<>("ID");
    private TableColumn<PlayerRow, Integer> moneyColumn = new TableColumn<>("Money");
    TableColumn<PlayerRow, Integer> columnLevel = new TableColumn<>("Level");

    public LeaderboardTable(ArrayList<Player> players, double width, double height, boolean isServer) {
        this.isServer = isServer;
        this.width = width;
        this.height = height;
        setTableAppearance();
        table.setId("smallText");
        table.setRowFactory(tv -> {
            TableRow<PlayerRow> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    MainStage.getInstance().getSoundUI().playTrack("click");
                    if (!isServer) {
                        PlayerRow rowData = row.getItem();
                        ClientSenderThread.getInstance().addToQueue(new ProfileRequestMessage(rowData.getId()));
                    } else {
                        ProfileGUI profileGUI = new ProfileGUI(server.getUserById(row.getItem().getId()).getPlayer());
                        profileGUI.init(new VBox(),isServer);
                        profileGUI.addToRoot(server.getServerPageGUI().getPane());
                        profileGUI.relocate(100, 100);
                        profileGUI.getOkButton().setOnMouseClicked(e -> {
                            MainStage.getInstance().getSoundUI().playTrack("click");
                            profileGUI.removeFromRoot(server.getServerPageGUI().getPane());
                        });
                    }
                }
            });
            return row ;
        });
        fillTableObservableList(players);
        table.setItems(observableList);
        table.setEditable(false);
        createIdColumn();
        createLevelColumn();
        createMoneyColumn();
//        MainStage.getInstance().getScene().getStylesheets().add(getClass().
//                getResource("CSS/truckTable.css").toExternalForm());
        // TODO: create appropriate CSS file.
    }

    private void createIdColumn() {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setResizable(false);
        idColumn.setReorderable(false);
        idColumn.setPrefWidth(0.6 * width);
        table.getColumns().add(idColumn);
    }

    private void createMoneyColumn() {

        moneyColumn.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getMoney()).asObject());
        moneyColumn.setResizable(false );
        moneyColumn.setReorderable(false);
        moneyColumn.setPrefWidth(0.2 * width);
        table.getColumns().add(moneyColumn);
    }

    private void createLevelColumn() {

        columnLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
        columnLevel.setResizable(false);
        columnLevel.setReorderable(false);
        columnLevel.setPrefWidth(0.2 * width);
        table.getColumns().add(columnLevel);
    }

    public void setTableAppearance() {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefWidth(width);
        table.setPrefHeight(height);
        idColumn.setPrefWidth(0.6 * width);
        moneyColumn.setPrefWidth(0.2 * width);
        columnLevel.setPrefWidth(0.2 * width);
        table.refresh();
    }

    public void fillTableObservableList(List<Player> players) {
        observableList.clear();
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

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof PlayerRow) {
                return this.id.equals(((PlayerRow) obj).id);
            }
            return false;
        }
    }

    public void relocate(double x, double y) {
        table.relocate(x, y);
    }

    public void addToRoot(Pane pane) {
        pane.getChildren().add(table);
    }

    public void update() {
        table.refresh();
    }

    public ObservableList<PlayerRow> getObservableList() {
        return observableList;
    }

    public void addPlayer(Player player) {
        observableList.add(new PlayerRow(player));
        update();
    }

    public void removePlayer(Player player) {
        observableList.remove(new PlayerRow(player));
    }

    public void updatePlayer(Player player) {
        for (PlayerRow playerRow : observableList) {
            if (playerRow.getId().equals(player.getId())) {
                playerRow.setLevel(player.getLevel());
                playerRow.setMoney(player.getMoney());
                update();
                return;
            }
        }
    }

    public void updatePlayer(String id, int money, int level) {
        for (PlayerRow playerRow : observableList) {
            if (playerRow.getId().equals(id)) {
                if (level != -1) {
                    playerRow.setLevel(level);
                }
                if (money != -1) {
                    playerRow.setMoney(money);
                }
                update();
                return;
            }
        }
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public TableView<PlayerRow> getTable() {
        return table;
    }
}
