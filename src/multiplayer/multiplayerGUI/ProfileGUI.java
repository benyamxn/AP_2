package multiplayer.multiplayerGUI;

import GUI.FarmGUI;
import GUI.Hoverable;
import GUI.MainStage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import multiplayer.Player;

import javax.print.DocFlavor;

public class ProfileGUI {

    private Player player;
    private Button okButton;
    private VBox vBox;
    public ProfileGUI(Player player) {
        this.player = player;
    }


    public void init(VBox vBox){
        this.vBox = vBox;
        vBox.setId("infoBox");
        vBox.setAlignment(Pos.CENTER);

        Text start1 = new Text("Profile Page");
        start1.setId("profileTitle");

        Text playerName = new Text ("Name\n" + player.getName());
        playerName.setId("profileInfo");

        Text  id = new Text ("Id\n" + player.getId());
        id.setId("idText");

        Text  money = new Text ("Money\n" + String.valueOf(player.getMoney()));
        money.setId("text");

        Text  level = new Text ("Level\n" + String.valueOf(player.getLevel()));
        level.setId("text");

        Text  number = new Text ("Number Of Exchanges\n" + String.valueOf(player.getNumberOfExchanges()));
        number.setId("text");

        okButton = new Button("Ok");
        Hoverable.setMouseHandler(okButton);
        okButton.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 20));
        VBox.setMargin(okButton, new Insets(20, 0, 0, 0));

        VBox.setMargin(start1 , new Insets(10,0,15,0));
        VBox.setMargin(playerName , new Insets(10,10,10,10));
        VBox.setMargin(id , new Insets(10,10,10,10));
        VBox.setMargin(money , new Insets(10,10,10,10));
        VBox.setMargin(level , new Insets(10,10,10,10));
        VBox.setMargin(number , new Insets(10,10,10,10));
        vBox.getChildren().addAll(start1,playerName,id,money,level,number,okButton);

        double width = MainStage.getInstance().getWidth() ;
        double height = MainStage.getInstance().getHeight() ;
        double anchor = (400 > height / 3)? height / 2 - 200: height / 3;
        AnchorPane.setBottomAnchor(vBox, anchor - 200);
        AnchorPane.setTopAnchor(vBox, anchor - 200);
        AnchorPane.setRightAnchor(vBox, width / 2 - width / 10);
        AnchorPane.setLeftAnchor(vBox, width / 2  - width / 10);

    }

    public Button getOkButton() {
        return okButton;
    }

    public void relocate(double x, double y) {
        vBox.relocate(x, y);
    }

    public void addToRoot(Pane pane) {
        pane.getChildren().add(vBox);
    }

    public void removeFromRoot(Pane pane) {
        pane.getChildren().remove(vBox);
        vBox.getChildren().clear();
    }
}
