// 150122827 Başak Akyüz - 150122531 Oğuz Fatih Akgemci - 150121063 Rabia
// This class represents vehicle

package objects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static utilz.Constants.*;

public class Vehicle extends ImageView {
    private City location;
    private City destination;
    private final int MAX_CAPACITY;     // Max seats
    private int capacity;       // Empty seats

    public Vehicle(City location, int maxCapacity) {
        this.location = location;
        this.MAX_CAPACITY = maxCapacity;
        this.capacity = maxCapacity;
        setImage();
    }

    // Set image based on vehicle's capacity
    private void setImage() {
        int imgNum;

        if (capacity<6)
            imgNum = 1;
        else if (capacity<14)
            imgNum = 2;
        else
            imgNum = 3;

        setImage(new Image(CAR_FILE+imgNum+CAR_FILE_EXTENSION, CAR_IMAGE_WIDTH, CAR_IMAGE_HEIGHT, true, false));
    }

    public City getLocation() {
        return location;
    }

    // Update location after drive button is pressed
    public void updateLocation() {
        this.location = this.destination;
    }

    public void setDestination(City destination) {
        this.destination = destination;
    }

    public City getDestination() {
        return destination;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getMaxCapacity() {
        return MAX_CAPACITY;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}