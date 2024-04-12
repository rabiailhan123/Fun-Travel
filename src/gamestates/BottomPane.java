// 150122827 Başak Akyüz - 150122531 Oğuz Fatih Akgemci - 150121063 Rabia
// This class is the bottom pane of the game. It contains drive
// button and information about passengers on the cities

package gamestates;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import static utilz.Constants.FONT;
import static utilz.Constants.INFO_FONT;

public class BottomPane extends HBox {
    private Playing playing;        // To access current playing object
    private Button drive = new Button("DRIVE");
    private Label info = new Label();

    public BottomPane(Playing playing) {
        this.playing = playing;

        // Bind width property of the info
        info.prefWidthProperty().bind(widthProperty().divide((2)));
        info.setTextFill(Color.BLACK);
        info.setFont(INFO_FONT);

        // Bind width property of the drive button
        drive.prefWidthProperty().bind(widthProperty().divide(2));
        drive.setFont(FONT);

        setBackground(new Background(new BackgroundFill(Color.web("#e4e4e4"), null, null)));
        getChildren().addAll(info, drive);

        // Set event handlers for drive button
        drive.setOnAction(event -> this.playing.drive());
    }

    // Update info label's text
    // This method is called if a city is clicked or drive animation is finished
    public void setInfo(String info) {
        this.info.setText(info);
    }
}
