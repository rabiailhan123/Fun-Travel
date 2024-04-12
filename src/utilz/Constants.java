// 150122827 Başak Akyüz - 150122531 Oğuz Fatih Akgemci - 150121063 Rabia
// This class contains all constants
// File paths, image dimensions, fonts
// We created this class to change things easily

package utilz;

import javafx.scene.text.Font;

public class Constants {
        public static final float SCALE = 1f;
        public static final int CELL_DEFAULT_SIZE = 64;
        public static final int PADDING_DEFAULT = 145;
        public static final int PADDING = (int) (PADDING_DEFAULT * SCALE);
        public static final int CELL_SIZE = (int) (CELL_DEFAULT_SIZE * SCALE);
        public static final int GAME_WIDTH = CELL_SIZE * 10 + PADDING;
        public static final int GAME_HEIGHT = CELL_SIZE * 10 + PADDING;

        public static final String LEVEL_FOLDER = "levels/";
        public static final String LEVEL_FILE = "level";
        public static final String LEVEL_FILE_EXTENSION = ".txt";

        public static final String MENU_BACKGROUND = "res/menu-background.png";
        public static final int MENU_BACKGROUND_WIDTH = 785;

        public static final String FIXED_CELL_IMAGE = "res/fixed-cell.png";
        public static final int FIXED_CELL_IMAGE_WIDTH = 64;

        public static final String ROAD_IMAGE = "res/road.png";
        public static final int ROAD_IMAGE_WIDTH = 64;

        public static final String CITY_FILE = "res/city";
        public static final String CITY_FILE_EXTENSION = ".png";
        public static final int CITY_IMAGE_WIDTH = 64;

        public static final String CAR_FILE = "res/car";
        public static final String CAR_FILE_EXTENSION = ".png";
        public static final int CAR_IMAGE_WIDTH = 64;
        public static final int CAR_IMAGE_HEIGHT = 30;

        public static final String MENU_BUTTONS = "res/button-atlas.png";
        public static final int B_WIDTH_DEFAULT = 140;
        public static final int B_HEIGHT_DEFAULT = 56;

        public static final String LEVEL_BUTTONS = "res/level-button-atlas.png";
        public static final int LEVEL_BUTTON_WIDTH = 64;

        public static final String BACK_BUTTON = "res/back-button-atlas.png";
        public static final int BACK_BUTTON_WIDTH = 64;
        public static final int BACK_BUTTON_HEIGHT = 64;

        public static final String LEVEL_SAVE = "src/res/level-save.txt";

        public static final String HIGHSCORE = "src/res/highest-score.txt";

        // Load ttf file
        public static Font FONT = Font.loadFont(Constants.class.getResource("../res/pixel.ttf").toExternalForm() , 18);
        public static Font CITY_FONT = Font.loadFont(Constants.class.getResource("../res/pixel.ttf").toExternalForm() , 11);
        public static Font INFO_FONT = Font.loadFont(Constants.class.getResource("../res/pixel.ttf").toExternalForm() , 14);
        public static Font HIGHSCORES_FONT = Font.loadFont(Constants.class.getResource("../res/pixel.ttf").toExternalForm() , 25);
}
