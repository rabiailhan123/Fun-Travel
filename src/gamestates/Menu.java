// 150122827 Başak Akyüz - 150122531 Oğuz Fatih Akgemci - 150121063 Rabia
// This class is the menu pane. We access all other states from here using menu buttons

package gamestates;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import main.Game;
import ui.MenuButton;

import static utilz.Constants.SCALE;

public class Menu extends VBox {
    private MenuButton[] buttons = new MenuButton[4];
    private Game game;      // To access current game object

    public Menu(Game game) {
        this.game = game;

        loadButtons();
        setEventHandlers();

        setAlignment(Pos.CENTER);
        setSpacing(20*SCALE);
        getChildren().addAll(buttons);
    }

    // Create menu buttons
    private void loadButtons() {
        buttons[0] = new MenuButton(0);
        buttons[1] = new MenuButton(1);
        buttons[2] = new MenuButton(2);
        buttons[3] = new MenuButton(3);
    }

    // Set event handlers for menu buttons
    private void setEventHandlers() {
        // Start a new game. Delete the previous save and load level menu to the game pane
        buttons[0].setOnMouseReleased(event -> {game.getPlaying().deleteSave(); game.update("LEVEL_MENU", 0);});
        // Continue with saved game. Load level menu to the game pane
        buttons[1].setOnMouseReleased(event -> game.update("LEVEL_MENU", 0));
        // Load the highest scores pane to the game pane
        buttons[2].setOnMouseReleased(event -> game.update("SCORES", 0));
        // Quit the game
        buttons[3].setOnMouseReleased(event -> game.update("QUIT", 0));
    }

    // Display menu on the game pane
    public void update() {
        // Hide all nodes so menu can be displayed
        game.hideAll();

        // If menu is previously added to the game pane just set visibility true
        // If not add menu to game pane for the first time
        if (game.getChildren().contains(this))
            setVisible(true);
        else
            game.getChildren().add(this);
    }
}
