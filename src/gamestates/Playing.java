// 150122827 Başak Akyüz - 150122531 Oğuz Fatih Akgemci - 150121063 Rabia
// This class contains level, top pane and bottom pane.
// It has all levels in an array list
// And displays them as user plays a level

package gamestates;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import main.Game;
import static utilz.Constants.*;

public class Playing extends VBox {
	public int currentLevelNum; // Number of displayed level
	private int levelSave; // Number of last unlocked level
	private int numOfLevels; // Number of levels in levels folder

	private Game game; // To access current game object
	private TopPane topPane = new TopPane(this);
	private BottomPane bottomPane = new BottomPane(this);
	public ArrayList<Level> levels = new ArrayList<>();

	public Playing(Game game) {
		this.game = game;

		setBackground(new Background(new BackgroundImage(
				new Image(MENU_BACKGROUND, MENU_BACKGROUND_WIDTH, 0, true, false), null, null, null, null)));

		loadAllLevels();
		setPadding(new Insets(5, 10, 5, 10));
		setSpacing(10 * SCALE);
		setAlignment(Pos.TOP_CENTER);

		getChildren().add(0, topPane);
		getChildren().add(levels.get(0));
		getChildren().add(2, bottomPane);

		// When ESC key is released go back to menu
		setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				// Reset current level
				levels.set(currentLevelNum - 1, new Level(currentLevelNum, true, this));
				game.update("LEVEL_MENU", 0);
			}
		});
	}

	// Load all levels from levels folder
	private void loadAllLevels() {
		// Get last unlocked level number from level_save.txt
		try {
			Scanner input = new Scanner(new File(LEVEL_SAVE));
			levelSave = input.nextInt();
			input.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		// Get number of level files in levels folder
		numOfLevels = new File(LEVEL_FOLDER).list().length;

		// Create levels
		for (int i = 1; i <= numOfLevels; i++)
			levels.add(new Level(i, i <= levelSave, this));
	}

	// Update playing pane
	public void update(int levelNum) {
		// If level is unlocked do not continue with the method
		if (!levels.get(levelNum - 1).isUnlocked())
			return;

		// If a new level is unlocked update levelSave and level-save.txt file
		if (levelNum > levelSave) {
			updateSave(levelNum);
		}

		// Update current level number
		currentLevelNum = levelNum;

		// Reset previous level in case "NEXT LEVEL" button is pressed before completing
		// the level
		// While calling this method from level menu, if Level 1 is called
		// currentLevelNum-2 becomes -1.
		// So the method gives an IndexOutOfBound error. To prevent this we added an if
		// condition.
		if (currentLevelNum > 2)
			levels.set(currentLevelNum - 2, new Level(currentLevelNum - 1, true, this));
		else
			levels.set(0, new Level(1, true, this));

		// Update level number and reset score
		topPane.setLevelNum(currentLevelNum);
		topPane.setScore(0);

		// If next level exists, check if it is unlocked and set color of NEXT LEVEL
		// label to green or red
		if (currentLevelNum != numOfLevels)
			topPane.getNextLevel().setTextFill(levels.get(currentLevelNum).isUnlocked() ? Color.GREEN : Color.RED);

		// If next level does not exist set visibility false
		topPane.getNextLevel().setVisible(currentLevelNum != numOfLevels);

		// Remove previous level from pane and add current one
		getChildren().remove(1);
		getChildren().add(1, levels.get(levelNum - 1));

		// Hide all nodes so playing can be displayed
		game.hideAll();

		// If playing is previously added to the game pane just set visibility true
		// If not add playing to game pane for the first time
		if (game.getChildren().contains(this))
			setVisible(true);
		else
			game.getChildren().add(game.getPlaying());
	}

	// Delete game save
	public void deleteSave() {
		for (Level level : levels)
			level.setUnlocked(false);

		levels.get(0).setUnlocked(true);
		updateSave(1);
	}

	// Update last unlocked level number in file
	private void updateSave(int levelSave) {
		this.levelSave = levelSave;

		try {
			// Overwrite the file
			FileWriter output = new FileWriter(LEVEL_SAVE, false);
			output.write(Integer.toString(levelSave));
			output.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// Drive button action
	public void drive() {
		// If animation is not running and destination of vehicle is not null, start
		// animation
		if (!levels.get(currentLevelNum - 1).getAnimation().isPlaying()
				&& levels.get(currentLevelNum - 1).getVehicle().getDestination() != null) {
			levels.get(currentLevelNum - 1).getAnimation().drive();

			// Move passengers from location city
			levels.get(currentLevelNum - 1).movePassengers();

			// Update vehicle location
			levels.get(currentLevelNum - 1).getVehicle().updateLocation();

			// Set destination to null
			levels.get(currentLevelNum - 1).getVehicle().setDestination(null);

			// If level is completed, unlock next level
			if (levels.get(currentLevelNum - 1).checkCompleted()) {
				levels.get(currentLevelNum).setUnlocked(true);
				topPane.getNextLevel().setTextFill(Color.GREEN);
			}

			// Clear information in bottom pane
			bottomPane.setInfo("");

			// Update score
			topPane.setScore(levels.get(currentLevelNum - 1).getScore());
		}
	}

	public BottomPane getBottomPane() {
		return bottomPane;
	}
}