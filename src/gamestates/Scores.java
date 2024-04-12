// 150122827 Başak Akyüz - 150122531 Oğuz Fatih Akgemci - 150121063 Rabia
// This class displays the highest score for each level

package gamestates;

import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import main.Game;
import ui.BackButton;

import static utilz.Constants.*;

public class Scores extends VBox {
    private Game game;      // To access current game object
    private BackButton backButton = new BackButton();
    private String text;        // The highest scores
    private Label highScores = new Label("");

    public Scores(Game game) {
		this.game = game;

        setAlignment(Pos.CENTER);
        setSpacing(5*SCALE);

        highScores.setFont(HIGHSCORES_FONT);
        highScores.setTextFill(Color.web("#F7EF8A"));

        Text cup = new Text("<< TOP SCORES >>");
        cup.setFill(Color.GOLD);
        cup.setFont(HIGHSCORES_FONT);

        // Set event handler for back button, go back to main menu
        backButton.setOnMouseReleased(event -> game.update("MENU", 0));

        getChildren().addAll(cup, highScores, backButton);
    }

    // Display scores on game pane
    public void update() {
        // Read the highest scores form file
        try {
            text = new String(Files.readAllBytes(Paths.get(HIGHSCORE)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        highScores.setText(text);

        // Hide all nodes so scores can be displayed
        game.hideAll();

        // If scores is previously added to the game pane just set visibility true
        // If not add scores to game pane for the first time
        if (game.getChildren().contains(this))
            setVisible(true);
        else
            game.getChildren().add(game.getScores());
    }

}