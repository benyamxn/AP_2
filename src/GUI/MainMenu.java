package GUI;

import controller.Controller;
import controller.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.Farm;
import model.Game;
import model.Mission;
import model.exception.MoneyNotEnoughException;
import model.exception.UsedIdException;
import multiplayer.Player;
import multiplayer.client.Client;
import multiplayer.multiplayerGUI.ChatRoomGUI;
import multiplayer.multiplayerModel.ChatRoom;
import multiplayer.multiplayerGUI.ServerPageGUI;
import multiplayer.server.Server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.util.Stack;
import java.util.zip.CheckedOutputStream;

public class MainMenu {
    private double width = MainStage.getInstance().getWidth();
    private double height = MainStage.getInstance().getHeight();
    private AnchorPane pane = new AnchorPane();
    public void render() {
        Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 42);

        pane.setId("mainMenuPane");
        VBox menuBox = new VBox();
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setSpacing(10);
        menuBox.setId("menuBox");
        double anchor = (400 > height / 3)? height / 2 - 200: height / 3;
        pane.setBottomAnchor(menuBox, anchor);
        pane.setTopAnchor(menuBox, anchor);
        pane.setRightAnchor(menuBox, 100.0);
        pane.setLeftAnchor(menuBox, width - 300);
        pane.getChildren().add(menuBox);
        Label titleLabel = new Label("Friendly  Farm");
        titleLabel.setId("titleLabel");
        pane.setTopAnchor(titleLabel, 80.0);
        pane.setLeftAnchor(titleLabel, 100.0);
        pane.getChildren().add(titleLabel);
        createButtons(menuBox);
        MainStage.getInstance().getScene().getStylesheets().add(getClass().
                getResource("CSS/mainMenu.css").toExternalForm());
        MainStage.getInstance().pushStack(pane);
    }

    private void createButtons(VBox vBox) {
        Button newGameButton = new Button("New Game");
        Button multiPlayerButton = new Button("Multi Player");
        Button loadGameButton = new Button("Load Game");
        Button settingsButton = new Button("Settings");
        Button aboutButton = new Button("About");
        Button exitButton = new Button("Exit");
        exitButton.setOnMouseClicked(event -> {
            MainStage.getInstance().getSoundUI().playTrack("click");
            System.exit(0);
        });
        newGameButton.setOnMouseClicked(event -> {
            try {
                MainStage.getInstance().getSoundUI().playTrack("click");
                Controller c = new Controller();
                System.out.println(Mission.getMissions().get(0).getProductsGoal());
                c.getGame().setMission(Mission.getMissions().get(0));
                new FarmGUI(c).render();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        multiPlayerButton.setOnMouseClicked(event -> createMultiPlayerMenu(vBox));
        loadGameButton.setOnMouseClicked(event -> createLoadGameButton());
        exitButton.setOnMouseClicked(event -> System.exit(0));

        settingsButton.setOnMouseClicked(event -> {
            MainStage.getInstance().getSoundUI().playTrack("click");
            createSettingsMenu(vBox);
        });

        aboutButton.setOnMouseClicked(event -> {
            MainStage.getInstance().getSoundUI().playTrack("click");
            createAboutsMenu(vBox);
        });

        VBox.setMargin(newGameButton, new Insets(10, 20, 10, 20));
        VBox.setMargin(multiPlayerButton,new Insets(10,20,10,20));
        VBox.setMargin(loadGameButton, new Insets(10, 20, 10, 20));
        VBox.setMargin(settingsButton, new Insets(10, 20, 10, 20));
        VBox.setMargin(aboutButton, new Insets(10, 20, 10, 20));
        VBox.setMargin(exitButton, new Insets(10, 20, 10, 20));
        vBox.getChildren().addAll(newGameButton,multiPlayerButton,loadGameButton, settingsButton, aboutButton, exitButton);
        for (Node child : vBox.getChildren()) {
            Hoverable.setMouseHandler(child);
        }

    }


    public static void createLoadGameButton() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Game File");
        File file = fileChooser.showOpenDialog(MainStage.getInstance().getScene().getWindow());
        if (file == null) {
            return;
        }
        FarmGUI.anchorPane = new AnchorPane();
        Controller controller = new Controller();
        try {
            controller.loadGame(file.getPath());
            new FarmGUI(controller).render();
        } catch (IOException e) {
            new Alert(Alert.AlertType.WARNING, "No such file").show();
            return;
        }
    }
    private void createSettingsMenu(VBox menuBox){
        menuBox.getChildren().clear();

        Slider musicSoundSlider = new Slider(0, 100, MainStage.getInstance().getSoundUI().getMusicSound()  * 100);
        musicSoundSlider.valueProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                MainStage.getInstance().getSoundUI().setMusicSound(newValue.doubleValue());
            }
        });

        Slider soundEffect = new Slider(0,100,MainStage.getInstance().getSoundUI().getVolume() * 100);
        soundEffect.valueProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                MainStage.getInstance().getSoundUI().setVolume(newValue.doubleValue());
            }
        });
        Text text = new Text("Music Volume:");
        text.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 20));
        Text text1 = new Text("Sound Effects:");
        text1.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 20));
        text.setFill(Color.GOLD);
        text1.setFill(Color.GOLD);
        Button backButton = new Button("Back");

        VBox.setMargin(text, new Insets(10, 20, 10, 20));
        VBox.setMargin(musicSoundSlider, new Insets(5, 20, 10, 20));
        VBox.setMargin(text1, new Insets(30, 20, 10, 20));
        VBox.setMargin(soundEffect, new Insets(5, 20, 10, 20));
        VBox.setMargin(backButton, new Insets(100, 20, 10, 20));

        menuBox.getChildren().addAll(text,musicSoundSlider,text1,soundEffect,backButton);
        Hoverable.setMouseHandler(backButton);
        backButton.setOnMouseClicked(event -> {
            MainStage.getInstance().getSoundUI().playTrack("click");
            menuBox.getChildren().clear();
            createButtons(menuBox);
        });
    }


    private void createAboutsMenu(VBox menuBox) {
        menuBox.getChildren().clear();
        Text start1 = new Text("\nA game, totally new!");
        start1.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 20));

        Text start2 = new Text("Made By:");
        start2.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 20));

        Text ben = new Text("Benyamin Ghaseminia");
        ben.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 20));
        Text aboo = new Text("Amirmohammad Abouei");
        aboo.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 20));
        Text aryo = new Text("Aryo Lotfi");
        aryo.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 20));

        Button backButton = new Button("Back");
        start1.setFill(Color.GOLD);
        start2.setFill(Color.GOLD);
        ben.setFill(Color.BEIGE);
        aboo.setFill(Color.BEIGE);
        aryo.setFill(Color.BEIGE);
        VBox.setMargin(start1, new Insets(10, 20, 10, 20));
        VBox.setMargin(start2, new Insets(10, 20, 10, 20));
        VBox.setMargin(aboo, new Insets(10, 20, 10, 20));
        VBox.setMargin(aryo, new Insets(10, 20, 10, 20));
        VBox.setMargin(ben, new Insets(10, 20, 10, 20));
        VBox.setMargin(backButton, new Insets(80, 20, 10, 20));

        menuBox.getChildren().addAll(start1, start2, aboo, aryo, ben, backButton);
        Hoverable.setMouseHandler(backButton);
        backButton.setOnMouseClicked(event -> {
            MainStage.getInstance().getSoundUI().playTrack("click");
            menuBox.getChildren().clear();
            createButtons(menuBox);
        });
    }

    private void createMultiPlayerMenu(VBox vBox){

        vBox.getChildren().clear();
        Button serverButton = new Button("Host");
        Button clientButton = new Button("client");
        Button backButton = new Button("Back");
        VBox.setMargin(serverButton, new Insets(10, 20, 10, 20));
        VBox.setMargin(clientButton, new Insets(10, 20, 10, 20));
        VBox.setMargin(backButton, new Insets(200, 20, 10, 20));
        vBox.getChildren().addAll(serverButton,clientButton,backButton);

        serverButton.setOnMouseClicked(event -> createServerMenu(vBox));
        clientButton.setOnMouseClicked(event -> createClientMenu(vBox));
        backButton.setOnMouseClicked(event -> {
            MainStage.getInstance().getSoundUI().playTrack("click");
            vBox.getChildren().clear();
            createButtons(vBox);
        });
        for (Node child : vBox.getChildren()) {
            Hoverable.setMouseHandler(child);
        }
    }


    private void createServerMenu(VBox vBox){
        pane.getChildren().remove(vBox);
        double anchor = (400 > height / 3)? height / 2 - 200: height / 3;
        VBox menu = new VBox();
        pane.getChildren().add(menu);
        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(10);
        menu.setId("menuBox");
        pane.setBottomAnchor(menu, anchor);
        pane.setTopAnchor(menu, anchor);
        pane.setRightAnchor(menu, width / 9);
        pane.setLeftAnchor(menu, width /2);

        Text error = new Text();
        error.setFill(Color.GOLD);

        Text textIP = new Text("IP ADDRESS : ");
        textIP.setFill(Color.GOLD);
        TextField ip = new TextField();
        ip.setAlignment(Pos.CENTER);
        ip.setText("127.0.0.1");
        ip.setEditable(false);

        Text textPort = new Text("PORT : ");
        textPort.setFill(Color.GOLD);
        TextField port = new TextField();
        port.setAlignment(Pos.CENTER);
        port.setText("8050");

        HBox buttons = new HBox();
        Button back  = new Button("Back");
        Button connect = new Button("Connect");
        buttons.setAlignment(Pos.CENTER);
        HBox.setMargin(back,new Insets(0,20,10,20));
        HBox.setMargin(connect,new Insets(0,10,10,30));
        buttons.getChildren().addAll(back,connect);

        VBox.setMargin(error,new Insets(20,40,5,50));
        VBox.setMargin(textIP,new Insets(10,40,10,50));
        VBox.setMargin(ip,new Insets(10,100,10,100));
        VBox.setMargin(textPort,new Insets(10,40,10,50));
        VBox.setMargin(port,new Insets(10,100,80,100));
        VBox.setMargin(buttons,new Insets(80,40,50,50));

        menu.getChildren().addAll(error,textIP,ip,textPort,port,buttons);
        setTextFont(menu,20);
        addToHoverable(buttons);
        back.setOnMouseClicked(event -> {
            pane.getChildren().add(vBox);
            pane.getChildren().remove(menu);
            createMultiPlayerMenu(vBox);
        });

        connect.setOnMouseClicked(event -> {
          try {
              int portNumber = Integer.parseInt(port.getText());
              if(portNumber < 0 || portNumber > 9999){
                  throw new  NumberFormatException();
              }
              Server server = new Server(portNumber , InetAddress.getByName(ip.getText()));
              new ServerPageGUI(server);
          } catch (Exception NumberFormatException) {
              error.setText("Invalid Port");
          }
        });

    }

    private void addToHoverable(Parent parent){
        for (Node node : parent.getChildrenUnmodifiable()) {
            Hoverable.setMouseHandler(node);
        }
    }
    private void createClientMenu(VBox vBox){

        pane.getChildren().remove(vBox);
        double anchor = (400 > height / 3)? height / 2 - 200: height / 3;
        VBox menu = new VBox();
        pane.getChildren().add(menu);
        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(10);
        menu.setId("menuBox");
        pane.setBottomAnchor(menu, anchor);
        pane.setTopAnchor(menu, anchor);
        pane.setRightAnchor(menu, width / 9);
        pane.setLeftAnchor(menu, width /2);

        Text error = new Text();
        error.setFill(Color.GOLD);

        Text textIP = new Text("Client IP ADDRESS : ");
        textIP.setFill(Color.GOLD);
        TextField ip = new TextField();
        ip.setAlignment(Pos.CENTER);
        ip.setText("192.168.1.68");
        ip.setEditable(false);



        Text textPort = new Text("PORT : ");
        textPort.setFill(Color.GOLD);
        TextField port = new TextField();
        port.setAlignment(Pos.CENTER);
        port.setText("8060");


        Text textSeverIP = new Text("Sever IP ADDRESS : ");
        textSeverIP.setFill(Color.GOLD);
        TextField severIP = new TextField();
        severIP.setAlignment(Pos.CENTER);
        severIP.setText("127.0.0.1");

        Text textSeverPort = new Text("Sever PORT : ");
        textSeverPort.setFill(Color.GOLD);
        TextField serverPort = new TextField();
        serverPort.setAlignment(Pos.CENTER);
        serverPort.setText("8050");


        Text id = new Text("ID : ");

        TextField idField = new TextField();
        Text name = new Text("NAME :");
        TextField nameField = new TextField();
        HBox user = new HBox();
        HBox.setMargin(id,new Insets(5,5,10,10));
        HBox.setMargin(idField,new Insets(0,10,5,10));
        HBox.setMargin(name,new Insets(5,5,5,10));
        HBox.setMargin(nameField,new Insets(0,10,10,10));
        user.getChildren().addAll(id,idField,name,nameField);
        setTextFont(user,15);
        user.setAlignment(Pos.CENTER);
        HBox buttons = new HBox();
        Button back  = new Button("Back");
        Button connect = new Button("Connect");
        buttons.setAlignment(Pos.CENTER);
        HBox.setMargin(back,new Insets(0,20,10,20));
        HBox.setMargin(connect,new Insets(0,10,10,30));
        buttons.getChildren().addAll(back,connect);

        VBox.setMargin(error,new Insets(10,40,10,50));
        VBox.setMargin(textIP,new Insets(5,40,0,50));
        VBox.setMargin(ip,new Insets(0,100,5,100));
        VBox.setMargin(textPort,new Insets(0,40,5,50));
        VBox.setMargin(port,new Insets(0,100,5,100));
        VBox.setMargin(textSeverIP,new Insets(0,40,5,50));
        VBox.setMargin(severIP,new Insets(0,100,5,100));
        VBox.setMargin(textSeverPort,new Insets(0,40,5,50));
        VBox.setMargin(serverPort,new Insets(0,100,20,100));
        VBox.setMargin(user,new Insets(5,100,10,100));
        VBox.setMargin(buttons,new Insets(10,40,5,50));

        menu.getChildren().addAll(error,textIP,ip,textPort,port);
        menu.getChildren().addAll(textSeverIP,severIP,textSeverPort,serverPort,user,buttons);
        setTextFont(menu,15);

        addToHoverable(buttons);
        back.setOnMouseClicked(event -> {
            pane.getChildren().add(vBox);
            pane.getChildren().remove(menu);
            createMultiPlayerMenu(vBox);
        });

        connect.setOnMouseClicked(event -> {

            try {
                if (idField.getText().equals("")) {
                    error.setText("Enter Id");
                    return;
                }
                if (nameField.getText().equals("")) {
                    error.setText("Enter Name");
                    return;
                }
                error.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 20));
                int portNumber = Integer.parseInt(port.getText());
                int serverPortNumber = Integer.parseInt(serverPort.getText());
                InetAddress ipClient = InetAddress.getByName(ip.getText());
                InetAddress ipSever = InetAddress.getByName(severIP.getText());
                if (portNumber < 0 || serverPortNumber < 0) {
                    throw new NumberFormatException();
                }
                Client client = new Client(new Player(nameField.getText(), idField.getText(), 20000), ipClient, portNumber, serverPortNumber, ipSever);
            } catch (UnknownHostException e) {
                error.setText("Invalid  ip");
            } catch (IOException e) {
                error.setText("Can Not connect to Sever");
            } catch (UsedIdException e) {
                error.setText("this id already used");
            }
        });

    }


    public void setTextFont(Pane menu,int size ){

        Font font = Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), size);
        for (Node child : menu.getChildren()) {
            if(child instanceof Text){
                ((Text )child).setFont(font);
                ((Text) child).setFill(Color.GOLD);
            }
        }

    }

}
