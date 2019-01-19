package GUI;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Callback;
import model.Game;
import model.ProductType;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.EnumMap;

public class TruckMenuTable {
    private Game game;
    private double width;
    private double height;
    private final TableView<ItemRow> table = new TableView<>();
    private final ObservableList<ItemRow> observableList = FXCollections.observableArrayList();
    private Label moneyLabel = new Label("");
    private Label totalCapacityLabel = new Label("");
    private Label remainedCapacityLabel = new Label("");
    private VBox statusBox;
    public TruckMenuTable(Game game, double width, double height) {
        this.game = game;
        this.width = width;
        this.height = height;

        setTableAppearance();

        fillTableObservableList();
        table.setItems(observableList);

        createProductColumn();
        createCountColumn();
        createDepotSizeColumn();
        createPriceColumn();
        createButtonsColumn();
        createTruckCount();
        createStatusBox();
        table.setEditable(false);
        MainStage.getInstance().getScene().getStylesheets().add(getClass().
                getResource("CSS/truckTable.css").toExternalForm());
    }

    private void createProductColumn() {
        TableColumn<ItemRow, HBox> columnProduct = new TableColumn<>("Product");
        columnProduct.setCellValueFactory(new PropertyValueFactory<>("descriptionBox"));
        columnProduct.setResizable(false);
        columnProduct.setSortable(false);
        columnProduct.setReorderable(false);
        columnProduct.setPrefWidth(0.31 * width);
        table.getColumns().add(columnProduct);
    }

    private void createCountColumn() {
        TableColumn<ItemRow, Label> columnCount = new TableColumn<>("Count");
        columnCount.setCellValueFactory(new PropertyValueFactory<>("count"));
        columnCount.setResizable(false);
        columnCount.setReorderable(false);
        columnCount.setPrefWidth(0.1 * width);
        table.getColumns().add(columnCount);
    }

    private void createDepotSizeColumn() {
        TableColumn<ItemRow, Double> depotSizeColumn = new TableColumn<>("Depot Size");
        depotSizeColumn.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getProductType().getDepotSize()).asObject());
        depotSizeColumn.setResizable(false);
        depotSizeColumn.setReorderable(false);
        depotSizeColumn.setPrefWidth(0.1 * width);
        table.getColumns().add(depotSizeColumn);
    }

    private void createPriceColumn() {
        TableColumn<ItemRow, Integer> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getProductType().getSaleCost()).asObject());
        priceColumn.setResizable(false);
        priceColumn.setReorderable(false);
        priceColumn.setPrefWidth(0.1 * width);
        table.getColumns().add(priceColumn);
    }

    private void setTableAppearance() {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefWidth(width);
        table.setPrefHeight(height);
    }

    private void fillTableObservableList() {
//        EnumMap<ProductType, Integer> productMap = game.getFarm().getWarehouse().getProductMap();
        EnumMap<ProductType, Integer> productMap = new EnumMap<>(ProductType.class);
        productMap.put(ProductType.EGG, 3);
        observableList.addAll(new ItemRow(ProductType.EGG, 3), new ItemRow(ProductType.WOOL, 4));
    }

    private void createButtonsColumn() {
        TableColumn<ItemRow, Void> buttonColumn = new TableColumn("Button Column");

        Callback<TableColumn<ItemRow, Void>, TableCell<ItemRow, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<ItemRow, Void> call(final TableColumn<ItemRow, Void> param) {
                final TableCell<ItemRow, Void> cell = new TableCell<>() {
                    private final Button addOneButton = new Button("1");
                    {
                        addOneButton.setOnAction((ActionEvent event) -> {
                            ItemRow entry = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + entry);
                        });
                        addOneButton.getStyleClass().add("addBtn");
                        addOneButton.setPrefWidth(width * 0.07);
                    }

                    private final Button addAllButton = new Button("all");
                    {
                        addAllButton.setOnAction((ActionEvent event) -> {
                            ItemRow entry = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + entry);
                        });
                        addAllButton.getStyleClass().add("addBtn");
                        addAllButton.setPrefWidth(width * 0.07);
                    }

                    private final Button removeOneButton = new Button("1");
                    {
                        removeOneButton.setOnAction((ActionEvent event) -> {
                            ItemRow entry = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + entry);
                        });
                        removeOneButton.getStyleClass().add("removeBtn");
                        removeOneButton.setPrefWidth(width * 0.07);
                    }

                    private final Button removeAllButton = new Button("all");
                    {
                        removeAllButton.setOnAction((ActionEvent event) -> {
                            ItemRow entry = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + entry);
                        });
                        removeAllButton.getStyleClass().add("removeBtn");
                        removeAllButton.setPrefWidth(width * 0.07);
                    }


                    private final HBox hBox = new HBox();
                    {
                        hBox.setSpacing(10);
                        hBox.getChildren().addAll(addOneButton, addAllButton, removeOneButton, removeAllButton);
                        hBox.setAlignment(Pos.CENTER);
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(hBox);
                        }
                    }
                };
                return cell;
            }
        };

        buttonColumn.setCellFactory(cellFactory);
        buttonColumn.setResizable(false);
        buttonColumn.setSortable(false);
        buttonColumn.setReorderable(false);
        buttonColumn.setPrefWidth(0.3 * width);
        table.getColumns().add(buttonColumn);

    }

    private void createTruckCount() {
        TableColumn<ItemRow, Label> columnCount = new TableColumn<>("Truck\nCount");
        columnCount.setCellValueFactory(new PropertyValueFactory<>("truckCount"));
        columnCount.setResizable(false);
        columnCount.setReorderable(false);
        columnCount.setPrefWidth(0.1 * width);
        table.getColumns().add(columnCount);
    }

    public class ItemRow {
        private ProductType productType;
        private Label productTypeName;
        private int count;
        private Image image;
        private ImageView imageView;
        private HBox descriptionBox;
        private int truckCount = 0;

        public int getTruckCount() {
            return truckCount;
        }

        public void setTruckCount(int truckCount) {
            this.truckCount = truckCount;
        }

        public ItemRow(ProductType productType, int count) {
            this.productType = productType;
            this.count = count;
            productTypeName = new Label(productType.toString());
            try {
                image = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures",
                        "Products", productType.toString().replace(" ", "") + ".png").toString()));
                imageView = new ImageView(image);
                descriptionBox = new HBox();
                descriptionBox.setSpacing(10);
                descriptionBox.getChildren().addAll(imageView, productTypeName);
                descriptionBox.setAlignment(Pos.CENTER);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        public ProductType getProductType() {
            return productType;
        }

        public void setProductType(ProductType productType) {
            this.productType = productType;
        }

        public Label getProductTypeName() {
            return productTypeName;
        }

        public void setProductTypeName(Label productTypeName) {
            this.productTypeName = productTypeName;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public Image getImage() {
            return image;
        }

        public void setImage(Image image) {
            this.image = image;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        public HBox getDescriptionBox() {
            return descriptionBox;
        }

        public void setDescriptionBox(HBox descriptionBox) {
            this.descriptionBox = descriptionBox;
        }
    }

    public void relocate(double x, double y) {
        table.relocate(x, y);
    }

    public void addToRoot(Pane pane) {
        pane.getChildren().add(table);
    }

    private void createStatusBox() {
        statusBox = new VBox();
        statusBox.setSpacing(10);
        statusBox.getChildren().addAll(createMoneyLabel(), createTotalCapacityLabel(), createRemainedCapacityLabel());
        statusBox.setAlignment(Pos.CENTER);
    }

    private HBox createMoneyLabel() {
        HBox moneyBox = new HBox();
        moneyBox.setSpacing(10);
        Label desc = new Label("Total Money:");
        desc.getStyleClass().add("statusLabel");
        try {
            Image coin = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures",
                    "coin.png").toString()));
            ImageView imageView = new ImageView(coin);
            imageView.setFitWidth(40);
            imageView.setPreserveRatio(true);
            moneyBox.getChildren().addAll(desc, moneyLabel, imageView);
            moneyLabel.getStyleClass().add("statusLabel");
            moneyBox.setAlignment(Pos.CENTER_LEFT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            return moneyBox;
        }
    }

    private HBox createTotalCapacityLabel() {
        HBox box = new HBox();
        box.setSpacing(10);
        Label desc = new Label("Total Capacity:");
        totalCapacityLabel.getStyleClass().add("statusLabel");
        desc.getStyleClass().add("statusLabel");
        box.getChildren().addAll(desc, totalCapacityLabel);
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
    }

    private HBox createRemainedCapacityLabel() {
        HBox box = new HBox();
        box.setSpacing(10);
        Label desc = new Label("Remained Capacity:");
        desc.getStyleClass().add("statusLabel");
        box.getChildren().addAll(desc, remainedCapacityLabel);
        remainedCapacityLabel.getStyleClass().add("statusLabel");
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
    }

    public VBox getStatusBox() {
        return statusBox;
    }
}
