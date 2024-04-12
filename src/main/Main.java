// 150122827 Başak Akyüz - 150122531 Oğuz Fatih Akgemci - 150121063 Rabia İlhan
// This class contains main method. This is where Game pane is added to scene and displayed on screen

package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static utilz.Constants.*;

public class Main extends Application {
    // Displacement of the stage
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) {
        // Create scene and add Game to the scene
        Scene scene = new Scene(new Game(), GAME_WIDTH, GAME_HEIGHT);

        // Stage init style is set to UNDECORATED so there is no window decoration to move the stage
        // To move the stage we added these mouse events
        scene.setOnMousePressed(event -> {
            // Get initial location of mouse cursor respected to the scene
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            // Change location of the window on screen
            // event.getScreenX() returns mouse cursor's location respected to the screen
            // Subtract xOffset from it, and we will get starting point of window respected to the screen
            // Then we use setX and setY to set x and y values of window(stage) on the screen
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}