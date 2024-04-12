// 150122827 Başak Akyüz - 150122531 Oğuz Fatih Akgemci - 150121063 Rabia
// This class is where all game states is displayed on

package main;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import gamestates.Menu;
import gamestates.LevelMenu;
import gamestates.Playing;
import gamestates.Scores;

import static utilz.Constants.*;

public class Game extends StackPane {
    private Menu menu;
    private LevelMenu levelMenu;
    private Playing playing;
    private Scores scores;
    public String STATE;        // Current displayed state

    public Game() {
        // Initialize classes
        initClasses();
        // Set background
        setBackground(new Background(new BackgroundImage(new Image(MENU_BACKGROUND, MENU_BACKGROUND_WIDTH*SCALE, 0, true, false), null, null, null, null)));
        // Update STATE to "MENU" and display menu
        update("MENU", 0);
    }

    // Initialize objects
    private void initClasses() {
        // Send current game object as an argument to the constructors
        menu = new Menu(this);
        playing = new Playing(this);
        levelMenu = new LevelMenu(this);
        scores = new Scores(this);
    }

    // This method updates what is displayed on Game pane
    // If we are loading a level we call this method with level number
    // If not we send 0 for levelNum
    public void update(String STATE, int levelNum) {
        // Update STATE to new state
        this.STATE = STATE;

        // Call update method of the new state
        // If state is quit, exit the program
        switch (STATE) {
            case "MENU" -> menu.update();
            case "LEVEL_MENU" -> levelMenu.update();
            case "PLAYING" -> playing.update(levelNum);
            case "SCORES" -> scores.update();
            case "QUIT" -> System.exit(0);
        }
    }

    // Set visibility of all nodes displayed on Game pane to false
    // We call this method from other states' update methods
    // When we want to display another state first we hide existing one
    public void hideAll() {
        for (Node node : getChildren())
            node.setVisible(false);
    }

    public LevelMenu getLevelMenu() {
        return levelMenu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public Scores getScores() {
        return scores;
    }
}