package controller;

import model.*;
import model.exception.*;
import view.View;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

public class Main {
    private static Controller controller = new Controller();
    private static View view = new View();
    public static void main(String[] args) {

        while(true) {

            try {
                Request request = view.getRequest();
                handleRequest(request);
            } catch (MoneyNotEnoughException e) {
                view.println("Money Not Enough");
            } catch (MaxLevelException e) {
                view.println("The selected unit cannot upgrade more");
            } catch (NameNotFoundException e) {
                view.println("The inputted name is not valid");
            } catch (VehicleOnTripException e) {
                view.println("The selected vehicle is on trip");
            } catch (NotEnoughCapacityException e) {
                view.println("There isn't enough space in warehouse");
            } catch (NotEnoughItemsException e) {
                view.println("There isn't enough items in the warehouse");
            } catch (IOException e) {
                view.println("File not found");
            } catch (RequestNotFoundException e) {
                view.println("Request not found");
            }
        }
    }

    public static void  handleRequest(Request request) throws MaxLevelException, NameNotFoundException, MoneyNotEnoughException, IOException, VehicleOnTripException, NotEnoughCapacityException, NotEnoughItemsException {

        RequestType requestType = request.getRequestType();
        switch (requestType) {
            case BUY_ANIMAL:
                controller.buyAnimal(request.getParameter());
                break;
            case PICKUP:
                controller.pickup(request.getPoint());
                break;
            case CAGE:
                controller.cage(request.getPoint());
                break;
            case PLANT:
                controller.plant(request.getPoint());
                break;
            case WELL:
                controller.well();
                break;
            case START:
                controller.startWorkshop(request.getParameter());
                break;
            case UPGRADE_WORKSHOP:
                controller.upgrade(controller.getGame().getFarm().getWorkshopByName(request.getParameter()));
                break;
            case UPGRADE_CAT:
                controller.upgrade(new Cat(new Point(9, 9)));
                break;
            case UPGRADE_WELL:
                controller.upgrade(controller.getGame().getFarm().getWell());
                break;
            case UPGRADE_VEHICLE:
                controller.upgrade(controller.getGame().getFarm().getVehicleByName(request.getVehicleType()));
                break;
            case UPGRADE_WAREHOUSE:
                controller.upgrade(controller.getGame().getFarm().getWarehouse());
                break;
            case LOAD_CUSTOM:
                controller.loadCustom(request.getParameter());
                break;
            case RUN:
                controller.runMap(request.getParameter());
                break;
            case SAVE_GAME:
                controller.saveGame(request.getParameter());
                break;
            case LOAD_GAME:
                controller.loadGame(request.getParameter());
                break;
            case PRINT_INFO:
                view.println(view.getStatusHandler().getStatus(controller.getGame()));
                break;
            case PRINT_MAP:
                view.println(view.getStatusHandler().getStatus(controller.getGame().getFarm().getMap()));
                break;
            case PRINT_LEVELS:

                break;
            case PRINT_WAREHOUSE:
                view.println(view.getStatusHandler().getStatus(controller.getGame().getFarm().getWarehouse()));
                break;
            case PRINT_WELL:
                view.println(view.getStatusHandler().getStatus(controller.getGame().getFarm().getWell()));
                break;
            case PRINT_WORKSHOPS:
                view.println(view.getStatusHandler().getStatus(controller.getGame().getFarm().getWorkshopByName(request.getParameter())));
                break;
            case PRINT_VEHICLE:
                view.println(view.getStatusHandler().getStatus(controller.getGame().getFarm().getVehicleByName(request.getVehicleType())));
                break;
            case TURN:
                controller.turn(request.getCount());
                break;
            case PRINT_MARKET_PRODUCTS:
                view.println(Arrays.asList(controller.getGame().getMarketProducts()).toString());
                break;
            case ADD_ITEM_TO_VEHICLE:
                controller.loadProducts(request.getVehicleType(), ProductType.getTypeByString(request.getParameter()), request.getCount());
                break;
            case CLEAR_VEHICLE:
                controller.clear(request.getVehicleType());
                break;
            case VEHICLE_GO:
                controller.goVehicle(request.getVehicleType());
                break;
            default:
                break;
        }
    }
}
