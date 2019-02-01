package multiplayer.multiplayerGUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import multiplayer.Player;

import javax.print.DocFlavor;

public class ProfileGUI {

    private Player player;
    private VBox vBox;
    public ProfileGUI(Player player) {
        this.player = player;
    }


    public void init(VBox vBox){
        this.vBox = vBox;

        Text start1 = new Text("Profile Page");
        start1.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 20));
        start1.setTextAlignment(TextAlignment.CENTER);
        vBox.setAlignment(Pos.CENTER_LEFT);
        Text name = new Text ("Name : " + player.getName());
        Text  id = new Text ("Id : " + player.getId());
        Text  money = new Text ("Money : " + String.valueOf(player.getMoney()));
        Text  level = new Text ("Level : " + String.valueOf(player.getLevel()));
        Text  number = new Text ("Number Of Exchanges " + String.valueOf(player.getNumberOfExchanges()));

        name.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 15));
        id.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 15));
        money.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 15));
        level.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 15));
        number.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 15));

        VBox.setMargin(start1 , new Insets(10,10,10,40));
        VBox.setMargin(name , new Insets(10,10,10,10));
        VBox.setMargin(id , new Insets(10,10,10,10));
        VBox.setMargin(money , new Insets(10,10,10,10));
        VBox.setMargin(level , new Insets(10,10,10,10));
        VBox.setMargin(number , new Insets(10,10,10,10));
        vBox.getChildren().addAll(start1,name,id,money,level,number);

        for (Node child : vBox.getChildren()) {
            if(child instanceof Text){
                ((Text) child).setFill(Color.BEIGE);
            }
        }

    }



}
