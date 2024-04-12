// 150122827 Başak Akyüz - 150122531 Oğuz Fatih Akgemci - 150121063 Rabia
// This class represents city in level

package objects;

import java.util.ArrayList;

import gamestates.Level;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.paint.Color;

import static utilz.Constants.*;

public class City extends Button {
    String name;
    private int rowIndex;
    private int columnIndex;
    private int cityID;
    private Level level;        // To access current level object
    private ArrayList<Passenger> passengers = new ArrayList<>();

    public City(Level level, String name, int cellID, int cityID) {
        rowIndex = (cellID-1)/10;
        columnIndex = (cellID-1)%10;
        this.name = name;
        this.cityID = cityID;
        this.level = level;

        setImage();
        setFont(CITY_FONT);

        setOnAction(event -> {
            // If animation is not playing and the selected city is not the city that vehicle is currently in
            // update bottom pane info and draw path
            if (this.level.getVehicle().getLocation() != this && !this.level.getAnimation().isPlaying()) {
                this.level.getAnimation().drawPath(this);
                this.level.getVehicle().setDestination(this);
                this.level.getPlaying().getBottomPane().setInfo(updateInfo());
            }
        });
    }

    private String updateInfo() {
        City currentCity = level.getVehicle().getLocation();

        String info = this.name
                + " (City ID: " + this.cityID
                + ", Distance: " + calculateDistance(this, currentCity)
                + ", Vehicle Capacity: " + level.getVehicle().getMaxCapacity() + ")\n";

        for (int j=0; j<currentCity.getPassengers().size(); j++)
            if (currentCity.getPassengers().get(j).getDestination() == this)
                info += "\t" + currentCity.name + " > " + this.name + " ("
                        + currentCity.getPassengers().get(j).getNumOfPassengers() + " Passengers)\n";

        for (int j=0; j<this.getPassengers().size(); j++)
            info += "\t" + this.name + " > " + this.getPassengers().get(j).getDestination().name + " ("
                    + this.getPassengers().get(j).getNumOfPassengers() + " Passengers)\n";

        return info;
    }

    private int calculateDistance(City currentCity, City destCity) {
        int distance = (int) Math.ceil(Math.sqrt(Math.pow((currentCity.getRowIndex() - destCity.getRowIndex()), 2)
                + Math.pow((currentCity.getColumnIndex() - destCity.getColumnIndex()), 2)));

        return distance;
    }

    private void setImage() {
        // Number of images in res folder
        int numberOfImages = 6;

        setPrefSize(CELL_SIZE, CELL_SIZE);

        setText(name);
        setFont(CITY_FONT);
        setAlignment(Pos.BOTTOM_CENTER);
        setTextFill(Color.WHITE);

        // Set random image for the city
        Image image = new Image(CITY_FILE + (int) (Math.random() * (numberOfImages) + 1) + CITY_FILE_EXTENSION, CITY_IMAGE_WIDTH, 0, true, false);
        setBackground(new Background(new BackgroundImage(image, null, null, null, null)));
    }

    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
    }

    public int getCityID() {
        return cityID;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }
}