package GUI;

import GUI.animation.AnimationConstants;
import GUI.animation.SpriteAnimation;
import javafx.animation.Animation;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.Product;
import model.ProductType;
import model.Workshop;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;


public class WorkshopGUI implements Hoverable,Pausable {

    private static int duration = 1000;
    private static int cycleCount = 9;
    private FarmGUI farmGUI;
    private Workshop workshop;
    private Image image;
    private boolean rotate;
    private int width ;
    private int height;
    private int columns = AnimationConstants.WORKSHOP[0];
    private ImageView imageView;
    private Animation animation;
    private ImageView infoImageView;
    private Image infoImage;
    private VBox infoVBox;
    private double infoWidth = MainStage.getInstance().getWidth() / 3;

    public WorkshopGUI(Workshop workshop , boolean rotate , int fitWidth, FarmGUI farmGUI) throws FileNotFoundException {
        this.workshop = workshop;
        this.farmGUI = farmGUI;
        workshop.setWorkshopGUI(this);
        image = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures","Workshops",
                workshop.getType().toString() ,"0" + workshop.getLevel() + ".png").toString()));
        imageView = new ImageView(image);
        width = (int) image.getWidth() / columns;
        height = (int) image.getHeight() / columns;

        imageView.setViewport(new Rectangle2D(0, 0,width,height));
        imageView.setFitWidth(fitWidth);
        imageView.setPreserveRatio(true);
        imageView.setOpacity(1);

        infoImage = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures","Workshops", "info.png").toString()));
        initInfo();
        setMouseEvent(imageView);
        this.rotate = rotate;
        if(rotate){
            imageView.setScaleX(-1);
        }


    }


    public void addToRoot(Pane pane){
        pane.getChildren().add(imageView);
    }

    public void relocate(double x, double y){
        imageView.relocate(x,y);
    }


    public void produce(){
        animation = new SpriteAnimation(imageView, Duration.millis(duration), AnimationConstants.WORKSHOP[1], columns,
                0, 0, width, height);
        animation.setCycleCount(cycleCount);
        animation.play();
        animation.setOnFinished(event -> {
            animation = null;
        });
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void upgrade(){
        try {
            image = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir"),"res","Textures","Workshops",
                    workshop.getType().toString() ,"0" + workshop.getLevel() + ".png").toString()));
            width = (int) image.getWidth() / columns;
            height = (int) image.getHeight() / columns;
            imageView.setImage(image);
            imageView.setViewport(new Rectangle2D(0, 0, width, height));
            if(rotate){
                imageView.setScaleX(-1);
            }
            duration = workshop.getProductionTime();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setOnClick(EventHandler<? super MouseEvent> eventHandler) { imageView.setOnMouseClicked(eventHandler); }

    public Workshop getWorkshop() {
        return workshop;
    }

    public void setOnHold(EventHandler<? super MouseEvent> eventHandler) {
        imageView.setOnMousePressed(eventHandler);
    }

    public void initTooltip(Tooltip tooltip) {
        Tooltip.install(imageView, tooltip);
        tooltip.setStyle("-fx-font-family: 'Spicy Rice'; -fx-font-size: 16px");
    }


    private void initInfo() {
        infoVBox = new VBox();
        infoVBox.setAlignment(Pos.CENTER);

        Button okButton = new Button("Ok");
        Hoverable.setMouseHandler(okButton);
        okButton.setOnMouseClicked(event -> {
            FarmGUI.getSoundPlayer().playTrack("click");
            FarmGUI.anchorPane.getChildren().removeAll(infoVBox);
            FarmGUI.anchorPane.getChildren().remove(farmGUI.getPauseRectangle());
            farmGUI.resume();
        });
        okButton.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 20));
        VBox.setMargin(okButton, new Insets(20, 0, 0, 0));

        Text workshopName = new Text(workshop.getName().replace("_", " "));
        workshopName.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 36));
        workshopName.setFill(Color.BLACK);
        VBox.setMargin(workshopName, new Insets(20, 0, 40, 0));
        infoVBox.setId("infoBox");
        double width = MainStage.getInstance().getWidth() ;
        double height = MainStage.getInstance().getHeight() ;
        double anchor = (400 > height / 3)? height / 2 - 200: height / 3;
        AnchorPane.setBottomAnchor(infoVBox, anchor - 200);
        AnchorPane.setTopAnchor(infoVBox, anchor - 200);
        AnchorPane.setRightAnchor(infoVBox, width / 2 - width / 10);
        AnchorPane.setLeftAnchor(infoVBox, width / 2  - width / 10);
        infoVBox.getChildren().add(workshopName);
        renderInputsAndOutputs();
        infoVBox.getChildren().add(okButton);
    }

    private void renderInputsAndOutputs() {
        Text input = new Text("Input(s):");
        input.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 30));
        input.setFill(Color.BLACK);
        infoVBox.getChildren().add(input);
        for (ProductType inputProduct : workshop.getNeededProducts()) {
            ImageView inputProductImageView = new ProductGUI(new Product(inputProduct), 1).getImageView();

            Text productName = new Text(inputProduct.toString());
            productName.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 26));
            productName.setFill(Color.CHOCOLATE);

            VBox.setMargin(productName, new Insets(0, 0, 10, 0));

            infoVBox.getChildren().addAll(inputProductImageView, productName);
        }
        Text output = new Text("Output :");
        output.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 30));
        output.setFill(Color.BLACK);

        Text outputName = new Text(workshop.getOutput().toString());
        outputName.setFont(Font.loadFont(getClass().getResourceAsStream("../fonts/spicyRice.ttf"), 28));
        outputName.setFill(Color.GOLDENROD);


        ImageView outputImageView = new ProductGUI(new Product(workshop.getOutput()), 1).getImageView();
        Insets center = new Insets(0, infoWidth / 2, 0, infoWidth / 2);
        VBox.setMargin(output, new Insets(60, 0, 10, 0));
        VBox.setMargin(outputImageView, new Insets(0, 0, 10, 0));
        VBox.setMargin(outputName, center);
        infoVBox.getChildren().addAll(output, outputImageView, outputName);
    }

    public void show(AnchorPane pane) {
        pane.getChildren().addAll(infoVBox);
    }

    @Override
    public void setRate(double rate) {
        if(rate == DurationManager.pauseRate ){
            pause();
            return;
        }
         if(rate == DurationManager.resumeRate){
            resume();
            return;
        }
         if(animation != null)
             animation.setRate(rate);
    }

    @Override
    public void pause() {
        if(animation != null)
            animation.pause();
    }

    @Override
    public void resume() {
        if(animation != null)
            animation.play();
    }

    public ImageView getInfoImageView() {
        return infoImageView;
    }
}
