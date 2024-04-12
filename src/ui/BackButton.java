// 150122827 Başak Akyüz - 150122531 Oğuz Fatih Akgemci - 150121063 Rabia
// This is the back button. It allows users to go back to previous menu

package ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import static utilz.Constants.*;

public class BackButton extends ImageView {
    private WritableImage[] images;

    public BackButton() {
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
        Image btAtlas = new Image(BACK_BUTTON, 192, 64, true, false);
        PixelReader pixelReader = btAtlas.getPixelReader();     // Load image as pixels

        // Crop full image into 3 pieces
        for (int i = 0; i < images.length; i++)
            images[i] = new WritableImage(pixelReader, i *  BACK_BUTTON_WIDTH, 0, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
    }
}
