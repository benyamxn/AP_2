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
import model.exception.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

public class HelicopterMenuTable {
    protected Game game;
    protected double width;
    protected double height;
    protected final TableView<ItemRow> table = new TableView<>();
    protected final ObservableList<ItemRow> observableList = FXCollections.observableArrayList();
    protected Label moneyLabel = new Label("0");
    protected Label totalCapacityLabel;
    protected Label remainedCapacityLabel;
    protected VBox statusBox;

    public HelicopterMenuTable(Game game, double width, double height) {
        table.setId("smallText");
        this.game = game;
        this.width = width;
        this.height = height;
        totalCapacityLabel = new Label(Double.toString(game.getFarm().getTruck().getCapacity()));
        remainedCapacityLabel = new Label(Double.toString(game.getFarm().getTruck().getCapacity()));

        setTableAppearance();

        fillTableObservableList();
        table.setItems(observableList);

        createProductColumn();
        createDepotSizeColumn();
        createPriceColumn();
        createButtonsColumn();
        createHelicopterCount();
        createStatusBox();
        table.setEditable(false);
        MainStage.getInstance().getScene().getStylesheets().add(getClass().
                getResource("CSS/truckTable.css").toExternalForm());
        fillTableObservableList();
    }

    protected void createProductColumn() {
        TableColumn<ItemRow, HBox> columnProduct = new TableColumn<>("Product");
        columnProduct.setCellValueFactory(new PropertyValueFactory<>("descriptionBox"));
        columnProduct.setResizable(false);
        columnProduct.setSortable(false);
        columnProduct.setReorderable(false);
        columnProduct.setPrefWidth(0.31 * width);
        table.getColumns().add(columnProduct);
    }

    protected void createDepotSizeColumn() {
        TableColumn<ItemRow, Double> depotSizeColumn = new TableColumn<>("Depot Size");
        depotSizeColumn.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getProductType().getDepotSize()).asObject());
        depotSizeColumn.setResizable(false);
        depotSizeColumn.setReorderable(false);
        depotSizeColumn.setPrefWidth(0.1 * width);
        table.getColumns().add(depotSizeColumn);
    }

    protected void createPriceColumn() {
        TableColumn<ItemRow, Integer> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getProductType().getBuyCost()).asObject());
        priceColumn.setResizable(false);
        priceColumn.setReorderable(false);
        priceColumn.setPrefWidth(0.1 * width);
        table.getColumns().add(priceColumn);
    }

    protected void setTableAppearance() {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefWidth(width);
        table.setPrefHeight(height);
    }

    protected void fillTableObservableList() {
        for (ProductType marketProduct : game.getMarketProducts()) {
            observableList.add(new ItemRow(marketProduct));
        }
    }

    protected void createButtonsColumn() {
        TableColumn<ItemRow, Void> buttonColumn = new TableColumn("Button Column");

        Callback<TableColumn<ItemRow, Void>, TableCell<ItemRow, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<ItemRow, Void> call(final TableColumn<ItemRow, Void> param) {
                final TableCell<ItemRow, Void> cell = new TableCell<>() {
                    protected final Button addOneButton = new Button("1");
                    {
                        addOneButton.setOnMouseClicked(event -> {
                            FarmGUI.getSoundPlayer().playTrack("click");
                            ItemRow entry = getTableView().getItems().get(getIndex());
                            try {
                                game.addProductToVehicle(game.getFarm().getHelicopter(), entry.getProductType(), 1);
                                entry.setCount(entry.getCount() + 1);
                                increaseMoney(entry.getProductType().getBuyCost());
                                decreaseRemainedCapacity(entry.getProductType().getDepotSize());
                                getTableView().refresh();
                            } catch (VehicleOnTripException e) {
                                e.printStackTrace();
                            } catch (NotEnoughCapacityException e) {
                                e.printStackTrace();
                            } catch (NotEnoughItemsException e) {
                                e.printStackTrace();
                            } catch (MoneyNotEnoughException e) {
                                e.printStackTrace();
                            } catch (ItemNotForSaleException e) {
                                e.printStackTrace();
                            }
                        });
                        addOneButton.getStyleClass().add("addBtn");
                        addOneButton.setPrefWidth(width * 0.05);
                    }

                    protected final Button addTenButton = new Button("10");
                    {
                        addTenButton.setOnMouseClicked(event -> {
                            FarmGUI.getSoundPlayer().playTrack("click");
                            ItemRow entry = getTableView().getItems().get(getIndex());
                            try {
                                game.addProductToVehicle(game.getFarm().getHelicopter(), entry.getProductType(), 10);
                                entry.setCount(entry.getCount() + 10);
                                increaseMoney(entry.getProductType().getBuyCost() * 10);
                                decreaseRemainedCapacity(entry.getProductType().getDepotSize() * 10);
                                getTableView().refresh();
                            } catch (VehicleOnTripException e) {
                                e.printStackTrace();
                            } catch (NotEnoughCapacityException e) {
                                e.printStackTrace();
                            } catch (NotEnoughItemsException e) {
                                e.printStackTrace();
                            } catch (MoneyNotEnoughException e) {
                                e.printStackTrace();
                            } catch (ItemNotForSaleException e) {
                                e.printStackTrace();
                            }
                        });
                        addTenButton.getStyleClass().add("addBtn");
                        addTenButton.setPrefWidth(width * 0.05);
                    }

                    protected final Button addAllButton = new Button("all");
                    {
                        addAllButton.setOnMouseClicked(event -> {
                            FarmGUI.getSoundPlayer().playTrack("click");
                            ItemRow entry = getTableView().getItems().get(getIndex());
                            int count = (int) (game.getFarm().getHelicopter().getCapacity() / entry.getProductType().getDepotSize());
                            if (count > 0) {
                                try {
                                    game.addProductToVehicle(game.getFarm().getHelicopter(), entry.getProductType(), count);
                                    entry.setCount(entry.getCount() + count);
                                    increaseMoney(entry.getProductType().getBuyCost() * count);
                                    decreaseRemainedCapacity(entry.getProductType().getDepotSize() * count);
                                    getTableView().refresh();
                                } catch (VehicleOnTripException e) {
                                    e.printStackTrace();
                                } catch (NotEnoughCapacityException e) {
                                    e.printStackTrace();
                                } catch (NotEnoughItemsException e) {
                                    e.printStackTrace();
                                } catch (MoneyNotEnoughException e) {
                                    e.printStackTrace();
                                } catch (ItemNotForSaleException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        addAllButton.getStyleClass().add("addBtn");
                        addAllButton.setPrefWidth(width * 0.05);
                    }

                    protected final Button removeOneButton = new Button("1");
                    {
                        removeOneButton.setOnMouseClicked(event -> {
                            FarmGUI.getSoundPlayer().playTrack("click");
                            ItemRow entry = getTableView().getItems().get(getIndex());
                            if (entry.getCount() > 0) {
                                entry.setCount(entry.getCount() - 1);
                                game.removeProductFromVehicle(game.getFarm().getHelicopter(), entry.productType, 1);
                                increaseMoney(-1 * entry.getProductType().getBuyCost());
                                decreaseRemainedCapacity(-1 * entry.getProductType().getDepotSize());
                                getTableView().refresh();
                            }
                        });
                        removeOneButton.getStyleClass().add("removeBtn");
                        removeOneButton.setPrefWidth(width * 0.05);
                    }

                    protected final Button removeTenButton = new Button("10");
                    {
                        removeTenButton.setOnMouseClicked(event -> {
                            FarmGUI.getSoundPlayer().playTrack("click");
                            ItemRow entry = getTableView().getItems().get(getIndex());
                            if (entry.getCount() >= 10) {
                                entry.setCount(entry.getCount() - 10);
                                game.removeProductFromVehicle(game.getFarm().getHelicopter(), entry.productType, 10);
                                increaseMoney(-1 * entry.getProductType().getBuyCost());
                                decreaseRemainedCapacity(-10 * entry.getProductType().getDepotSize());
                                getTableView().refresh();
                            }
                        });
                        removeTenButton.getStyleClass().add("removeBtn");
                        removeTenButton.setPrefWidth(width * 0.05);
                    }

                    protected final Button removeAllButton = new Button("all");
                    {
                        removeAllButton.setOnMouseClicked(event -> {
                            FarmGUI.getSoundPlayer().playTrack("click");
                            ItemRow entry = getTableView().getItems().get(getIndex());
                            int count = entry.getCount();
                            if (count > 0) {
                                entry.setCount(0);
                                game.removeProductFromVehicle(game.getFarm().getTruck(), entry.productType, count);
                                increaseMoney(-1 * entry.getProductType().getBuyCost() * count);
                                decreaseRemainedCapacity(-1 * entry.getProductType().getDepotSize() * count);
                                getTableView().refresh();
                            }
                        });
                        removeAllButton.getStyleClass().add("removeBtn");
                        removeAllButton.setPrefWidth(width * 0.05);
                    }


                    protected final HBox hBox = new HBox();
                    {
                        hBox.setSpacing(10);
                        hBox.getChildren().addAll(addOneButton, addTenButton, addAllButton, removeOneButton, removeTenButton, removeAllButton);
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
        buttonColumn.setPrefWidth(0.4 * width);
        table.getColumns().add(buttonColumn);

    }

    protected void createHelicopterCount() {
        TableColumn<ItemRow, Label> columnCount = new TableColumn<>("Count");
        columnCount.setCellValueFactory(new PropertyValueFactory<>("count"));
        columnCount.setResizable(false);
        columnCount.setReorderable(false);
        columnCount.setPrefWidth(0.1 * width);
        table.getColumns().add(columnCount);
    }

    public class ItemRow {
        protected ProductType productType;
        protected Label productTypeName;
        protected Image image;
        protected ImageView imageView;
        protected HBox descriptionBox;
        protected int count = 0;
        protected int quantity;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public ItemRow(ProductType productType) {
            this.productType = productType;
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

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getQuantity() {
            return quantity;
        }

        public ItemRow(ProductType productType, int quantity) {
            this(productType);
            this.quantity = quantity;
        }
    }

    public void relocate(double x, double y) {
        table.relocate(x, y);
    }

    public void addToRoot(Pane pane) {
        pane.getChildren().add(table);
    }

    protected void createStatusBox() {
        statusBox = new VBox();
        statusBox.setSpacing(10);
        statusBox.getChildren().addAll(createMoneyLabel(), createTotalCapacityLabel(), createRemainedCapacityLabel());
        statusBox.setAlignment(Pos.CENTER);
    }

    protected HBox createMoneyLabel() {
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

    protected HBox createTotalCapacityLabel() {
        HBox box = new HBox();
        box.setSpacing(10);
        Label desc = new Label("Total Capacity:");
        totalCapacityLabel.getStyleClass().add("statusLabel");
        desc.getStyleClass().add("statusLabel");
        box.getChildren().addAll(desc, totalCapacityLabel);
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
    }

    protected HBox createRemainedCapacityLabel() {
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

    protected void increaseMoney(int value) {
        Integer prevValue = Integer.valueOf(moneyLabel.getText());
        prevValue = prevValue + value;
        moneyLabel.setText(prevValue.toString());
    }

    protected void decreaseRemainedCapacity(double value) {
        Double prevValue = Double.valueOf(remainedCapacityLabel.getText());
        prevValue = prevValue - value;
        remainedCapacityLabel.setText(prevValue.toString());
    }

    int getMoney() {
        return Integer.parseInt(moneyLabel.getText());
    }
}
