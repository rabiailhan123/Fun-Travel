// 150122827 Başak Akyüz - 150122531 Oğuz Fatih Akgemci - 150121063 Rabia
// This is menu button. It allows user to load different pages such as level menu, playing, high scores
// Or exit the game

package ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import static utilz.Constants.*;

public class MenuButton extends ImageView {
    private WritableImage[] images;
    private int rowIndex;

    public MenuButton(int rowIndex) {
        this.rowIndex = rowIndex;
        loadImages();
        setImage(images[0]);

        // To make button more interactive change images based on user's actions
        setOnMouseEntered(event -> setImage(images[1]));
        setOnMouseExited(event -> setImage(images[0]));
        setOnMousePressed(event -> setImage(images[2]));
    }

    // Load images for button
    private void loadImages() {
        images = new WritableImage[3];
        Image btAtlas = new Image(MENU_BUTTONS);
        PixelReader pixelReader = btAtlas.getPixelReader();

        // Based on which row the button is, crop its part
        // For example first row is PLAY button so if row is 0 load PLAY button row as 3 images
        // Crop full image into 3 pieces
        for (int i = 0; i < images.length; i++)
            images[i] = new WritableImage(pixelReader, i *  B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
    }
}
