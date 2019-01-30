package GUI;

import GUI.animation.AnimationConstants;
import GUI.animation.SpriteAnimation;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.Cell;
import model.Product;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;



public class CellGUI  implements  Pausable{

    private  static final double DURATION = 500;
    private static int columns = AnimationConstants.GRASS[0];
    private static Image grassImage;
    private static Image battleImage;
    private double[] location;
    private Animation animation;

    static {
        try {
            grassImage = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir").toString(),"res","Textures","Grass","grass4.png").toString()));
            battleImage = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir").toString(),"res","Textures","Animals","battle_1.png").toString()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ImageView imageView = new ImageView(grassImage);
    private static int width = (int) grassImage.getWidth() / columns;
    private static int height = (int) grassImage.getHeight() / columns;
    private Cell cell;

    public CellGUI(Cell cell, double width, double height ,double[] location) {
        this.cell = cell;
        cell.setCellGUI(this);
        imageView.setOpacity(1);
        if (cell.getGrassLevel() == 0)
            imageView.setOpacity(0);
        imageView.setFitHeight(height);
        this.location = location;
    }


    public void render(){
        int index = cell.getGrassLevel();
        if(index == 0){
            imageView.setVisible(false);
            return;
        }
        imageView.setVisible(true);
        index--;
        final int x = (index % columns) * width;
        final int y = (index / columns) * height;
        imageView.setViewport(new Rectangle2D(x, y, width, height));
    }
    public ImageView getImageView() {
        return imageView;
    }

    public void growGrass(int before){
        imageView.setVisible(true);
        int after = cell.getGrassLevel();
        if(before != after) {
            animation = new SpriteAnimation(imageView, Duration.millis(DURATION), 4, columns,
                    ((before) % columns) * width, ((before) / columns) * height, width, height);
            setRate(DurationManager.getRate());
            animation.play();
            animation.setOnFinished(event -> animation = null);
        }
    }

    public Cell getCell() {
        return cell;
    }

    public void battle() {
        imageView.setVisible(false);
        FarmGUI.getSoundPlayer().playTrack("fight");
        double[] size = new double[]{ battleImage.getWidth() / 5,  battleImage.getHeight() / 4};
        ImageView temp = new ImageView();
        temp.setImage(battleImage);
        temp.setViewport(new Rectangle2D(0, 0, size[0], size[1]));
        temp.setFitHeight(FarmGUI.cellHeight);
        temp.setFitWidth(FarmGUI.cellWidth);
        FarmGUI.anchorPane.getChildren().add(temp);
        temp.relocate(location[0],location[1]);
        animation = new SpriteAnimation(temp, Duration.millis(DURATION), 20, 5, 0, 0, (int)size[0],(int) size[1]);
        setRate(DurationManager.getRate());
        animation.play();
        animation.setOnFinished(event -> {
            FarmGUI.anchorPane.getChildren().remove(temp);
            imageView.setVisible(true);
            animation = null;
        });
    }

    public void setLocation(double[] location) {
        this.location = location;
    }

    public void placeProduct(Product product) {
        ProductGUI productGUI = new ProductGUI(product, 2);
        productGUI.getImageView().relocate(location[0], location[1]);
        FarmGUI.anchorPane.getChildren().add(productGUI.getImageView());
    }

    public void placeProduct(Product[] products) {
        for (Product product : products) {
            placeProduct(product);
        }
    }

    public void removeProduct(Product product) {
        product.getProductGUI().getImageView().setVisible(false);
        FarmGUI.anchorPane.getChildren().remove(product.getProductGUI().getImageView());
        product.setProductGUI(null);
    }


    @Override
    public void setRate(double rate) {
        if(rate == DurationManager.pauseRate ){
            pause();
        }
        else if(rate == DurationManager.resumeRate){
            resume();
        }
        else if(animation != null)
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


}
