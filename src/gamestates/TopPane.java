// 150122827 Başak Akyüz - 150122531 Oğuz Fatih Akgemci - 150121063 Rabia
// This class is the top pane of the game. It contains level number,
// score and next level button/label

package gamestates;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import static utilz.Constants.FONT;

public class TopPane extends HBox {
    private Label levelNumLbl;
    private Label scoreLbl;
    private Label nextLevel;

    public TopPane(Playing playing) {
        levelNumLbl = new Label("LEVEL #" + (playing.currentLevelNum + 1));
        levelNumLbl.setAlignment(Pos.CENTER_LEFT);
        levelNumLbl.setFont(FONT);
        levelNumLbl.setTextFill(Color.WHITE);
        levelNumLbl.prefWidthProperty().bind(playing.widthProperty().divide(3));

        scoreLbl = new Label("SCORE: " + 0);
        scoreLbl.setAlignment(Pos.CENTER);
        scoreLbl.setFont(FONT);
        scoreLbl.setTextFill(Color.WHITE);
        scoreLbl.prefWidthProperty().bind(playing.widthProperty().divide(3));

        nextLevel = new Label("NEXT LEVEL >");
        nextLevel.setAlignment(Pos.CENTER_RIGHT);
        nextLevel.setFont(FONT);
        nextLevel.prefWidthProperty().bind(playing.widthProperty().divide(3));
        // Call next level
        nextLevel.setOnMouseReleased(event -> {
            playing.update(playing.currentLevelNum+1);
        });

        getChildren().addAll(levelNumLbl, scoreLbl, nextLevel);
    }

    // Update level number
    public void setLevelNum(int levelNum) {
        levelNumLbl.setText("LEVEL #" + levelNum);
    }

    // Update score
    public void setScore(int score) {
        scoreLbl.setText("SCORE: " + score);
    }

    public Label getNextLevel() {
        return nextLevel;
    }
}
