package multiplayer.multiplayerGUI;

import GUI.FarmGUI;
import GUI.HelicopterMenuTable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import model.Game;
import model.ProductType;
import model.exception.*;
import multiplayer.ClientSenderThread;
import multiplayer.client.ClientHandler;
import multiplayer.multiplayerModel.HelicopterProduct;
import multiplayer.multiplayerModel.messages.HelicopterMenuRequest;
import multiplayer.multiplayerModel.messages.TruckMenuRequest;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class HelicopterMenuTableMultiplayer extends HelicopterMenuTable {
    public HelicopterMenuTableMultiplayer(Game game, double width, double height) {
        super(game, width, height);
        ClientHandler.setHelicopterMenuTableMultiplayer(this);
    }

    @Override
    protected void fillTableObservableList() {
        ClientSenderThread.getInstance().addToQueue(new HelicopterMenuRequest());
    }

    public void updatePrices(List<HelicopterProduct> products) {
        observableList.clear();
        for (HelicopterProduct product : products) {
            product.getProductType().setBuyCost(product.getBuyPrice());
            if (product.getQuantity() > 0) {
                System.out.println(product.getQuantity());
                observableList.add(new ItemRow(product.getProductType(), product.getQuantity()));
            }
        }
        System.out.println(observableList.size());
        table.refresh();
    }

    @Override
    protected void createHelicopterCount() {
        TableColumn<ItemRow, Label> columnCount = new TableColumn<>("Count");
        columnCount.setCellValueFactory(new PropertyValueFactory<>("count"));
        columnCount.setResizable(false);
        columnCount.setReorderable(false);
        columnCount.setPrefWidth(0.5 * width);
        table.getColumns().add(columnCount);

        TableColumn<ItemRow, Label> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityColumn.setResizable(false);
        quantityColumn.setReorderable(false);
        quantityColumn.setPrefWidth(0.5 * width);
        table.getColumns().add(quantityColumn);
    }

//    @Override
//    protected void createButtonsColumn() {
//        TableColumn<ItemRow, Void> buttonColumn = new TableColumn("Button Column");
//
//        Callback<TableColumn<ItemRow, Void>, TableCell<ItemRow, Void>> cellFactory = new Callback<>() {
//            @Override
//            public TableCell<ItemRow, Void> call(final TableColumn<ItemRow, Void> param) {
//                final TableCell<ItemRow, Void> cell = new TableCell<>() {
//                    protected final Button addOneButton = new Button("1");
//                    {
//                        addOneButton.setOnMouseClicked(event -> {
//                            FarmGUI.getSoundPlayer().playTrack("click");
//                            ItemRow entry = getTableView().getItems().get(getIndex());
//                            try {
//                                game.addProductToVehicle(game.getFarm().getHelicopter(), entry.getProductType(), 1);
//                                entry.setCount(entry.getCount() + 1);
//                                increaseMoney(entry.getProductType().getBuyCost());
//                                decreaseRemainedCapacity(entry.getProductType().getDepotSize());
//                                getTableView().refresh();
//                            } catch (VehicleOnTripException e) {
//                                e.printStackTrace();
//                            } catch (NotEnoughCapacityException e) {
//                                e.printStackTrace();
//                            } catch (NotEnoughItemsException e) {
//                                e.printStackTrace();
//                            } catch (MoneyNotEnoughException e) {
//                                e.printStackTrace();
//                            } catch (ItemNotForSaleException e) {
//                                e.printStackTrace();
//                            }
//                        });
//                        addOneButton.getStyleClass().add("addBtn");
//                        addOneButton.setPrefWidth(width * 0.05);
//                    }
//
//                    protected final Button addTenButton = new Button("10");
//                    {
//                        addTenButton.setOnMouseClicked(event -> {
//                            FarmGUI.getSoundPlayer().playTrack("click");
//                            ItemRow entry = getTableView().getItems().get(getIndex());
//                            try {
//                                game.addProductToVehicle(game.getFarm().getHelicopter(), entry.getProductType(), 10);
//                                entry.setCount(entry.getCount() + 10);
//                                increaseMoney(entry.getProductType().getBuyCost() * 10);
//                                decreaseRemainedCapacity(entry.getProductType().getDepotSize() * 10);
//                                getTableView().refresh();
//                            } catch (VehicleOnTripException e) {
//                                e.printStackTrace();
//                            } catch (NotEnoughCapacityException e) {
//                                e.printStackTrace();
//                            } catch (NotEnoughItemsException e) {
//                                e.printStackTrace();
//                            } catch (MoneyNotEnoughException e) {
//                                e.printStackTrace();
//                            } catch (ItemNotForSaleException e) {
//                                e.printStackTrace();
//                            }
//                        });
//                        addTenButton.getStyleClass().add("addBtn");
//                        addTenButton.setPrefWidth(width * 0.05);
//                    }
//
//                    protected final Button addAllButton = new Button("all");
//                    {
//                        addAllButton.setOnMouseClicked(event -> {
//                            FarmGUI.getSoundPlayer().playTrack("click");
//                            ItemRow entry = getTableView().getItems().get(getIndex());
//                            int count = (int) (game.getFarm().getHelicopter().getCapacity() / entry.getProductType().getDepotSize());
//                            if (count > 0) {
//                                try {
//                                    game.addProductToVehicle(game.getFarm().getHelicopter(), entry.getProductType(), count);
//                                    entry.setCount(entry.getCount() + count);
//                                    increaseMoney(entry.getProductType().getBuyCost() * count);
//                                    decreaseRemainedCapacity(entry.getProductType().getDepotSize() * count);
//                                    getTableView().refresh();
//                                } catch (VehicleOnTripException e) {
//                                    e.printStackTrace();
//                                } catch (NotEnoughCapacityException e) {
//                                    e.printStackTrace();
//                                } catch (NotEnoughItemsException e) {
//                                    e.printStackTrace();
//                                } catch (MoneyNotEnoughException e) {
//                                    e.printStackTrace();
//                                } catch (ItemNotForSaleException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//                        addAllButton.getStyleClass().add("addBtn");
//                        addAllButton.setPrefWidth(width * 0.05);
//                    }
//
//                    protected final Button removeOneButton = new Button("1");
//                    {
//                        removeOneButton.setOnMouseClicked(event -> {
//                            FarmGUI.getSoundPlayer().playTrack("click");
//                            ItemRow entry = getTableView().getItems().get(getIndex());
//                            if (entry.getCount() > 0) {
//                                entry.setCount(entry.getCount() - 1);
//                                game.removeProductFromVehicle(game.getFarm().getHelicopter(), entry.productType, 1);
//                                increaseMoney(-1 * entry.getProductType().getBuyCost());
//                                decreaseRemainedCapacity(-1 * entry.getProductType().getDepotSize());
//                                getTableView().refresh();
//                            }
//                        });
//                        removeOneButton.getStyleClass().add("removeBtn");
//                        removeOneButton.setPrefWidth(width * 0.05);
//                    }
//
//                    protected final Button removeTenButton = new Button("10");
//                    {
//                        removeTenButton.setOnMouseClicked(event -> {
//                            FarmGUI.getSoundPlayer().playTrack("click");
//                            ItemRow entry = getTableView().getItems().get(getIndex());
//                            if (entry.getCount() >= 10) {
//                                entry.setCount(entry.getCount() - 10);
//                                game.removeProductFromVehicle(game.getFarm().getHelicopter(), entry.productType, 10);
//                                increaseMoney(-1 * entry.getProductType().getBuyCost());
//                                decreaseRemainedCapacity(-10 * entry.getProductType().getDepotSize());
//                                getTableView().refresh();
//                            }
//                        });
//                        removeTenButton.getStyleClass().add("removeBtn");
//                        removeTenButton.setPrefWidth(width * 0.05);
//                    }
//
//                    protected final Button removeAllButton = new Button("all");
//                    {
//                        removeAllButton.setOnMouseClicked(event -> {
//                            FarmGUI.getSoundPlayer().playTrack("click");
//                            ItemRow entry = getTableView().getItems().get(getIndex());
//                            int count = entry.getCount();
//                            if (count > 0) {
//                                entry.setCount(0);
//                                game.removeProductFromVehicle(game.getFarm().getTruck(), entry.productType, count);
//                                increaseMoney(-1 * entry.getProductType().getBuyCost() * count);
//                                decreaseRemainedCapacity(-1 * entry.getProductType().getDepotSize() * count);
//                                getTableView().refresh();
//                            }
//                        });
//                        removeAllButton.getStyleClass().add("removeBtn");
//                        removeAllButton.setPrefWidth(width * 0.05);
//                    }
//
//
//                    protected final HBox hBox = new HBox();
//                    {
//                        hBox.setSpacing(10);
//                        hBox.getChildren().addAll(addOneButton, addTenButton, addAllButton, removeOneButton, removeTenButton, removeAllButton);
//                        hBox.setAlignment(Pos.CENTER);
//                    }
//
//                    @Override
//                    public void updateItem(Void item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (empty) {
//                            setGraphic(null);
//                        } else {
//                            setGraphic(hBox);
//                        }
//                    }
//                };
//                return cell;
//            }
//        };
//
//        buttonColumn.setCellFactory(cellFactory);
//        buttonColumn.setResizable(false);
//        buttonColumn.setSortable(false);
//        buttonColumn.setReorderable(false);
//        buttonColumn.setPrefWidth(0.4 * width);
//        table.getColumns().add(buttonColumn);
//
//    }
}
