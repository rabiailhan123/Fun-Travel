// 150122827 Başak Akyüz - 150122531 Oğuz Fatih Akgemci - 150121063 Rabia
// This class is the level menu. It allows the user to choose any level from among the unlocked levels

package gamestates;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import main.Game;
import ui.BackButton;
import ui.LevelButton;

import static utilz.Constants.SCALE;

public class LevelMenu extends GridPane {
    private LevelButton[] buttons;
    private BackButton backButton;
    private Game game;      // To access current game object

    public LevelMenu(Game game) {
        this.game = game;

        setHgap(10*SCALE);
        setVgap(10*SCALE);
        setAlignment(Pos.CENTER);

        loadButtons();
        setEventHandlers();
    }

    // Create as many buttons as the number of levels
    private void loadButtons() {
        int i, j=0;

        int numOfLevels = game.getPlaying().levels.size();
        buttons = new LevelButton[numOfLevels];

        // Set levels array list, we will use it to check if a level is unlocked or not
        LevelButton.setLevels(game.getPlaying().levels);

        // Create buttons
        for (i=0; i<buttons.length; i++) {
            buttons[i] = new LevelButton(i+1);
        }

        // Load buttons to LevelMenu pane
        // Each row will have 5 elements
        for (i=0; i<=buttons.length/5; i++) {
            for (j=0; j<5 && i*5+j<buttons.length; j++) {
                add(buttons[i*5+j], j, i);
            }
        }

        // Create back button and add it to the pane
        backButton = new BackButton();
        add(backButton, j%5, i-1);
    }

    // Set event handlers for buttons
    private void setEventHandlers() {
        for (int i=0; i<buttons.length; i++) {
            int levelNum = i;

            // If level is unlocked, load level
            buttons[i].setOnMouseReleased(event -> {
                if (LevelButton.getLevels().get(levelNum).isUnlocked())
                    game.update("PLAYING", levelNum+1);
            });
        }

        // If back button is released go back to main menu
        // We did not use setOnMouseClicked here because it effects the image change for button
        backButton.setOnMouseReleased(event -> game.update("MENU", 0));
    }

    // Display level menu on scene
    public void update() {
        // Update buttons' background image in case lock status of a level is changed
        for (LevelButton button : buttons)
            button.update();

        // Hide all nodes so level menu can be displayed
        game.hideAll();

        // If level menu is previously added to the game pane just set visibility true
        // If not add level menu to game pane for the first time
        if (game.getChildren().contains(this))
            setVisible(true);
        else
            game.getChildren().add(game.getLevelMenu());
    }
}
