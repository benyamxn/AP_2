package multiplayer.multiplayerGUI;

import GUI.FarmGUI;
import GUI.Hoverable;
import GUI.MainStage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
        vBox.setAlignment(Pos.CENTER_LEFT);

        Text start1 = new Text("Profile Page");
        start1.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 20));
        start1.setTextAlignment(TextAlignment.CENTER);

        Text name = new Text ("Name : " + player.getName());
        name.setId("senderName");

        Text  id = new Text ("Id : " + player.getId());
        id.setId("idText");

        Text  money = new Text ("Money : " + String.valueOf(player.getMoney()));
        money.setId("messageText");

        Text  level = new Text ("Level : " + String.valueOf(player.getLevel()));
        level.setId("messageText");

        Text  number = new Text ("Number Of Exchanges " + String.valueOf(player.getNumberOfExchanges()));
        number.setId("messageText");

        okButton = new Button("Ok");
        Hoverable.setMouseHandler(okButton);
        okButton.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 20));
        VBox.setMargin(okButton, new Insets(20, 0, 0, 0));


//        name.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 15));
//        id.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 15));
//        money.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 15));
//        level.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 15));
//        number.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 15));

        VBox.setMargin(start1 , new Insets(10,10,10,40));
        VBox.setMargin(name , new Insets(10,10,10,10));
        VBox.setMargin(id , new Insets(10,10,10,10));
        VBox.setMargin(money , new Insets(10,10,10,10));
        VBox.setMargin(level , new Insets(10,10,10,10));
        VBox.setMargin(number , new Insets(10,10,10,10));
        vBox.getChildren().addAll(start1,name,id,money,level,number,okButton);

        for (Node child : vBox.getChildren()) {
            if(child instanceof Text){
                ((Text) child).setFill(Color.BEIGE);
            }
        }

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
