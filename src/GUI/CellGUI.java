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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;



public class CellGUI {

    private  static final double DURATION = 300;
    private static int columns = AnimationConstants.GRASS[0];
    private static Image grassImage;

    static {
        try {
            grassImage = new Image(new FileInputStream(Paths.get(System.getProperty("user.dir").toString(),"res","Textures","Grass","grass1.png").toString()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ImageView imageView = new ImageView(grassImage);
    private static int width = (int) grassImage.getWidth() / columns;
    private static int height = (int) grassImage.getHeight() / columns;
    private Cell cell;

    public CellGUI(Cell cell) {
        this.cell = cell;
        imageView.setOpacity(1);
        if (cell.getGrassLevel() == 0)
            imageView.setOpacity(0);
    }

    public void render(){
        int index = cell.getGrassLevel();
        final int x = (index % columns) * width;
        final int y = (index / columns) * height;
        imageView.setViewport(new Rectangle2D(x, y, width, height));

    }

    public ImageView getImageView() {
        return imageView;
    }

    public void growGrass(int before){
        int after = cell.getGrassLevel();
        if(before != after) {
            final Animation animation = new SpriteAnimation(imageView, Duration.millis(DURATION), 4, columns,
                    (before % columns) * width, (before / columns) * height, width, height);
            animation.play();
        }
    }

    public Cell getCell() {
        return cell;
    }
}
