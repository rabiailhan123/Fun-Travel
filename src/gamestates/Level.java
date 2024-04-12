// 150122827 Başak Akyüz - 150122531 Oğuz Fatih Akgemci - 150121063 Rabia
// This class is the level. It reads level files and creates vehicles, roads, cities etc.

package gamestates;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import objects.*;
import static utilz.Constants.*;

public class Level extends GridPane {
    public int[][] cells = new int[10][10];     // To store if a cell is empty or filled
    private boolean unlocked;       // To check if level is unlocked
    private int score;
    private int levelNum;
    private ArrayList<City> cities = new ArrayList<>();
    private ArrayList<FixedCell> fixedCells = new ArrayList<>();
    private Vehicle vehicle;
    private Animation animation;
    private Playing playing;        // To access current playing object

    public Level(int levelNum, boolean unlocked, Playing playing) {
        setAlignment(Pos.CENTER);

        // Create 10 rows and 10 columns CELL_SIZE wide, all cells are empty
        for (int i=0; i<10; i++) {
            RowConstraints row = new RowConstraints(CELL_SIZE);
            ColumnConstraints column = new ColumnConstraints(CELL_SIZE);
            getColumnConstraints().add(column);
            getRowConstraints().add(row);
        }

        this.unlocked = unlocked;
        this.levelNum = levelNum;
        this.playing = playing;

        // Load level from level.txt file
        loadLevelFromFile();

        // Display level
        loadLevelToPane();
    }

    // Load level from level.txt file
    private void loadLevelFromFile() {
        // Read file
        Scanner input;
        try {
            input = new Scanner(new File(LEVEL_FOLDER + LEVEL_FILE + levelNum + LEVEL_FILE_EXTENSION));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        while (input.hasNextLine()) {
            // Read line by line and separate values
            // We did not use delimiter because then the end of
            // the line is read with next line's object type
            String line = input.nextLine();
            String[] info = line.split(",");

            // Create objects
            switch (info[0]) {
                case "City" -> {
                    City city = new City(this, info[1], Integer.parseInt(info[2]), Integer.parseInt(info[3]));
                    cities.add(city);
                    // Set that cell filled
                    cells[city.getRowIndex()][city.getColumnIndex()] = 1;
                }
                case "Fixed" -> {
                    FixedCell fixedCell = new FixedCell(Integer.parseInt(info[1]));
                    fixedCells.add(fixedCell);
                    // Set that cell filled
                    cells[fixedCell.getRowIndex()][fixedCell.getColumnIndex()] = 1;
                }
                case "Vehicle" -> {
                    // Check cities in the arraylist to set vehicle's location city
                    for (City city : cities) {
                        if (city.getCityID() == Integer.parseInt(info[1]))
                            vehicle = new Vehicle(city, Integer.parseInt(info[2]));
                    }
                }
                case "Passenger" -> {
                    Passenger passenger = new Passenger(Integer.parseInt(info[1]));
                    // Check cities in the arraylist to set vehicle's location city
                    for (City city : cities) {
                        // Add passenger to its starting city
                        if (city.getCityID() == Integer.parseInt(info[2]))
                            city.addPassenger(passenger);
                        // Set destination city of passenger
                        else if (city.getCityID() == Integer.parseInt(info[3]))
                            passenger.setDestination(city);
                    }
                }
            }
        }
        input.close();
    }

    // Display level on screen
    public void loadLevelToPane() {
        // Add road to empty cells
        for (int i=0; i<10; i++)
            for (int j=0; j<10; j++)
                if (cells[i][j] == 0)
                    add(new ImageView(new Image(ROAD_IMAGE, ROAD_IMAGE_WIDTH, 0, true, false)), j, i);

        animation = new Animation(this);
        add(animation, 0, 0);
        setColumnSpan(animation, 10);
        setRowSpan(animation, 10);

        // Add cities to grid pane
        for (City city : cities)
            add(city, city.getColumnIndex(), city.getRowIndex());

        // Add fixed cells to grid pane
        for (FixedCell fixedCell : fixedCells)
            add(fixedCell, fixedCell.getColumnIndex(), fixedCell.getRowIndex());

        // Add vehicle to grid pane
        add(vehicle, vehicle.getLocation().getColumnIndex(), vehicle.getLocation().getRowIndex());
    }

    // Move passengers from one city to another
    public void movePassengers() {
        City currentCity = vehicle.getLocation();

        System.out.println("Passengers before moving");
        for (Passenger passenger: currentCity.getPassengers()) {
            System.out.println(passenger.getNumOfPassengers() + " ** Destination " + passenger.getDestination());
        }

        for(int i=0;i<currentCity.getPassengers().size();i++) {
          if(currentCity.getPassengers().get(i).getDestination()==vehicle.getDestination()) {
            Passenger passenger = currentCity.getPassengers().get(i);

            if(vehicle.getCapacity()>0) {
                if(passenger.getNumOfPassengers()>=vehicle.getCapacity()) {
                    passenger.setNumOfPassengers(passenger.getNumOfPassengers()-vehicle.getCapacity());
                    vehicle.setCapacity(0);
                }
                else {
                    vehicle.setCapacity(vehicle.getCapacity()-passenger.getNumOfPassengers());
                    passenger.setNumOfPassengers(0);
                }
            }
            else
                break;

            if (passenger.getNumOfPassengers() == 0)
                currentCity.getPassengers().remove(passenger);
          }
        }

        System.out.println("Passengers after moving");
        for (Passenger passenger: currentCity.getPassengers()) {
            System.out.println(passenger.getNumOfPassengers() + " ** Destination " + passenger.getDestination());
        }

        calculateScore();

        vehicle.setCapacity(vehicle.getMaxCapacity());
    }

    // Calculate score
    public void calculateScore() {
        int score;
        int cost = calculateDistance(vehicle.getLocation(), vehicle.getDestination());
        int numOfPassengers = Math.abs(vehicle.getMaxCapacity() - vehicle.getCapacity());
        double income = numOfPassengers * cost * 0.2;

        score = (int) (income - cost);

        // Set new score
        this.score += score;
    }

    // Calculate distance
    private int calculateDistance(City currentCity, City destCity) {
        int distance = (int) Math.ceil(Math.sqrt(Math.pow((currentCity.getRowIndex() - destCity.getRowIndex()), 2)
                + Math.pow((currentCity.getColumnIndex() - destCity.getColumnIndex()), 2)));
        return distance;
    }

    // Check if level is completed. Call after every time DRIVE button is pressed
    public boolean checkCompleted() {
        for (City city : cities)
            if (city.getPassengers().size()!=0)
                return false;

        // Level is completed, check the score and update the highest scores
        updateHighestScore();

        return true;
    }

    // This method updates the highest scores file if new score is greater than previous one
    public void updateHighestScore() {
        String updatedScores = "";
        Scanner input;

        // Read file
        try {
            input = new Scanner(new File(HIGHSCORE));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Read line by line and separate values
        while (input.hasNextLine()) {
            String line = input.nextLine();
            String[] info = line.split(" ");

            // If new score is greater than the previous one, update that specific line
            // If not do not continue with the method
            if (Integer.parseInt(info[1])==levelNum)
                if (score>Integer.parseInt(info[3]))
                    line = line.replace(info[3], Integer.toString(score));
                else
                    return;

            // Add lines to updatedScores
            updatedScores += line + "\n";
        }
        input.close();

        // Overwrite the file
        try {
            FileWriter output = new FileWriter(HIGHSCORE, false);
            output.write(updatedScores);
            System.out.println("FILE IS OVERWRITTEN");
            output.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public int getScore() {
        return score;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Animation getAnimation() {
        return animation;
    }

    public Playing getPlaying() {
        return playing;
    }
}