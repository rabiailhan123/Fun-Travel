// 150122827 Başak Akyüz - 150122531 Oğuz Fatih Akgemci - 150121063 Rabia
// This is level button. It allows user to load a level among unlocked levels

package ui;

import gamestates.Level;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

import static utilz.Constants.*;

public class LevelButton extends Button {
    private WritableImage[] images;
    private static ArrayList<Level> levels;
    private int levelNum;

    public LevelButton(int levelNum) {
        this.levelNum = levelNum;

        loadImages();

        setAlignment(Pos.CENTER);
        setPrefSize(LEVEL_BUTTON_WIDTH, LEVEL_BUTTON_WIDTH);

        setText(Integer.toString(levelNum));
        setFont(FONT);
        setTextFill(Color.BLACK);
        setTextAlignment(TextAlignment.CENTER);

        // To make button more interactive change images based on user's actions
        setOnMouseEntered(event -> {
            if (levels.get(levelNum-1).isUnlocked())
                setBackground(images[1]);
        });
        setOnMouseExited(event -> {
            if (levels.get(levelNum-1).isUnlocked())
                setBackground(images[0]);
        });
        setOnMousePressed(event -> {
            if (levels.get(levelNum-1).isUnlocked())
                setBackground(images[2]);
        });
    }

    // Load images for button
    private void loadImages() {
        images = new WritableImage[3];
        Image lvlAtlas = new Image(LEVEL_BUTTONS);
        PixelReader pixelReader = lvlAtlas.getPixelReader();

        // Crop full image into 3 pieces
        for (int i = 0; i < images.length; i++)
            images[i] = new WritableImage(pixelReader, i * LEVEL_BUTTON_WIDTH, 0, LEVEL_BUTTON_WIDTH, LEVEL_BUTTON_WIDTH);
    }

    // Update background, this method is called every time level menu is displayed
    public void update() {
        // If level is locked make it look like it is an inactive button
        if (levels.get(levelNum-1).isUnlocked())
            setBackground(images[0]);
        else
            setBackground(images[1]);
    }

    // Set background
    private void setBackground(WritableImage image) {
        setBackground(new Background(new BackgroundImage(image, null, null, null, null)));
    }

    public static void setLevels(ArrayList<Level> levels) {
        LevelButton.levels = levels;
    }

    public static ArrayList<Level> getLevels() {
        return levels;
    }
}