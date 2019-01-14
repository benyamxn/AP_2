package GUI;

import GUI.animation.AnimationConstants;
import GUI.animation.SpriteAnimation;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.Cell;
import model.Domesticated;

import java.nio.file.Paths;



public class CellGUI {

    private  static final double DURATION = 100;
    private static int columns = AnimationConstants.GRASS[0];
    private static Image grassImage = new Image(Paths.get(System.getProperty("user.dir"),"res","Textures","Grass","grass1.png").toString());
    private ImageView imageView;
    private static int width = (int) grassImage.getWidth() / columns;
    private static int height = (int) grassImage.getHeight() / columns;
    private Cell cell;
    public CellGUI(Cell cell) {
        this.cell = cell;
        imageView = new ImageView();
    }

    public void render(){
        int index = cell.getGrassLevel();
        final int x = (index % columns) * width;
        final int y = (index / columns) * height;
        imageView.setViewport(new Rectangle2D(x, y, width, height));
        imageView.setOpacity(1);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void growGrass(){
        int before = cell.getGrassLevel();
        cell.growGrass();
        int after = cell.getGrassLevel();
        final Animation animation = new SpriteAnimation(imageView, Duration.millis(DURATION),4 , columns,
                (before % columns) * width,(before / columns) * height,width,height);
        animation.play();
    }

    public Cell getCell() {
        return cell;
    }
}
