// 150122827 Başak Akyüz - 150122531 Oğuz Fatih Akgemci - 150121063 Rabia
// This is the animation pane. Animation pane is added to the level as a layer
// It holds 10 columns and 10 rows
// This class contains all methods about path creation and vehicle animation

package objects;

import gamestates.Level;
import javafx.animation.PathTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.animation.Animation.Status;
import javafx.util.Duration;

import java.util.ArrayList;

import static utilz.Constants.CELL_SIZE;

public class Animation extends Pane {
    private int [][] cells;     // To check if a cell is empty or filled
    private ArrayList<Integer[]> moves = new ArrayList<>();     // Points of path in level grid pane
    private Level level;        // To access current level object
    private Polyline polyline = new Polyline();     // Path
    private PathTransition pathTransition;      // Animation for vehicle

    public Animation(Level level) {
        this.level = level;

        // Init cells array
        cells = new int[10][10];

        // Inıt path transition
        pathTransition = new PathTransition();

        polyline.setStrokeWidth(CELL_SIZE/5.0);
        polyline.setStroke(Color.BLUE);
    }

    // Vehicle animation method
    public void drive() {
        // Remove vehicle from grid pane
        level.getChildren().remove(level.getVehicle());

        // Add vehicle to animation pane
        if (!getChildren().contains(level.getVehicle())) {
            level.getVehicle().setLayoutX(0);
            level.getVehicle().setLayoutY(0);
            getChildren().add(level.getVehicle());
        }

        pathTransition.setDuration(Duration.millis(150*polyline.getPoints().size()));
        pathTransition.setNode(level.getVehicle());
        pathTransition.setPath(polyline);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setOnFinished(event -> {
            // Hide path
            polyline.setVisible(false);

            // Remove vehicle from animation pane
            getChildren().remove(level.getVehicle());

            // setTranslateX() and setTranslateY() methods are used to set the translation or displacement of a node relative to its current position.
            // If you want to move the node downwards/to right, you can use a positive value, and if you want to move it upwards/to left, you can use a negative value.
            // Before animation starts these values are 0, but then they change and this effects vehicle's appearance in grid pane, so we reset them.
            level.getVehicle().setTranslateX(0);
            level.getVehicle().setTranslateY(0);
            level.getVehicle().setRotate(0);

            // Update vehicle location on grid pane
            level.add(level.getVehicle(), level.getVehicle().getLocation().getColumnIndex(), level.getVehicle().getLocation().getRowIndex());
        });

        // Start animation
        pathTransition.play();
    }

    // Check whether animation is running
    public boolean isPlaying() {
        return pathTransition.getStatus() == Status.RUNNING;
    }

    // Draw path on animation pane
    public void drawPath(City destination) {
        // Remove path previously painted
        getChildren().remove(polyline);
        polyline.getPoints().removeAll(polyline.getPoints());

        // Set path visibility true
        polyline.setVisible(true);

        // Create path points
        moves.clear();
        createPath(destination);

        // Add points to poly line
        for (Integer[] move : moves) {
            polyline.getPoints().add((double) ((2*move[2]+1)*(CELL_SIZE/2)));
            polyline.getPoints().add((double) ((2*move[1]+1)*(CELL_SIZE/2)));
        }
        getChildren().add(polyline);
    }

    // Create path points
    private void createPath(City destination) {
        // Copy original cells array so it will not be modified
        for(int i = 0; i < level.cells.length; i++)
            cells[i] = level.cells[i].clone();

        // Set checkpoint counter to 1
        int checkPointCounter = 0;

        // Set initial values of i and j and add them to moves
        // i is for row index and j is for column index
        int i = level.getVehicle().getLocation().getRowIndex();
        int j = level.getVehicle().getLocation().getColumnIndex();
        System.out.println("INITIAL CHECKPOINT " + checkPointCounter + "  **  i = " + i + " ** j = " + j + " **");
        moves.add(new Integer[]{checkPointCounter++, i, j});

        // Set destination cell to empty so vehicle can move there
        cells[destination.getRowIndex()][destination.getColumnIndex()] = 0;

        // Continue until vehicle reaches destination
        while (!(i==destination.getRowIndex() && j==destination.getColumnIndex())) {
            boolean rowChanged = false;
            boolean columnChanged = false;

            // Change row until vehicle is in the same row as destination or the cell vehicle wants to move is not empty
            while (i!=destination.getRowIndex() && cells[i+Integer.compare(destination.getRowIndex(),i)][j]==0) {
                // Integer.compare method returns 0, 1 or -1
                // When we compare destination row index and i (current row index), if i is greater than
                // destination row index method will return -1 so i value is going to be decreased
                i += Integer.compare(destination.getRowIndex(),i);

                System.out.println("CHECKPOINT " + checkPointCounter + " **  ROW CHANGED ** i = " + i + " ** j = " + j + " **");

                // Add new indexes to moves
                moves.add(new Integer[]{checkPointCounter, i, j});

                // Set new location to filled so vehicle will not go over it again
                cells[i][j] = 1;

                rowChanged = true;
            }

            // Change column until vehicle is in the same column as destination or the cell vehicle wants to move is not empty
            while (j!=destination.getColumnIndex() && cells[i][j+Integer.compare(destination.getColumnIndex(),j)]==0) {
                // Integer.compare method returns 0, 1 or -1
                // When we compare destination column index and i (current column index), if j is lesser than
                // destination row index method will return 1 so j value is going to be increased
                j += Integer.compare(destination.getColumnIndex(), j);

                System.out.println("CHECKPOINT " + checkPointCounter + " ** COLUMN CHANGED ** i = " + i + " ** j = " + j + " **");

                // Add new indexes to moves
                moves.add(new Integer[]{checkPointCounter, i, j});

                // Set new location to filled so vehicle will not go over it again
                cells[i][j] = 1;

                columnChanged = true;
            }

            // If neither row nor column value is changed that could mean a few things
            // Each situation is explained in following comments
            if (!(rowChanged || columnChanged)) {
                // If row or column is not changed program has to find a new empty cell from neighbour cells
                int [] newEmptyCell = findEmptyCell(i , j);

                // If a new empty cell is found that means previously row and column could not be changed
                // because either current row and column indexes were the same as destination row and column
                // indexes or the cell vehicle wanted to move was not empty
                if (newEmptyCell != null) {
                    i = newEmptyCell[0];
                    j = newEmptyCell[1];

                    // Set new location to filled so vehicle will not go over it again
                    cells[i][j] = 1;

                    // Increase checkpoint counter and add new indexes to moves
                    moves.add(new Integer[]{++checkPointCounter, i, j});

                    System.out.println("NEW CHECKPOINT " + checkPointCounter +" ** NEW EMPTY CELL FOUND ** i = " + i + " ** j = " + j + " **");
                }
                else {
                    // If a new empty cell can not be found that means vehicle is stuck
                    // It has to go back to last checkpoint and look for a new path
                    int m = moves.size()-1;

                    // Set cells added to moves until last checkpoint to empty
                    while (m>=0 && moves.get(m)[0]==checkPointCounter) {
                        cells[moves.get(m)[1]][moves.get(m)[2]] = 0;
                        m--;
                    }

                    // Set failed checkpoint to filled so vehicle will not go over it again
                    cells[moves.get(m+1)[1]][moves.get(m+1)[2]] = 1;

                    // Set i and j to last checkpoint i and j
                    i = moves.get(m)[1];
                    j = moves.get(m)[2];
                    checkPointCounter--;
                    System.out.println("PATH FAILED ** GOING BACK TO CHECKPOINT " + checkPointCounter + " ** i = " + i + " ** j = " + j + " **");

                    // Remove failed checkpoint cells from moves
                    for (int n=moves.size()-1; n>m; n--)
                        moves.remove(moves.get(n));
                }
            }
        }
        System.out.println("FINAL ** i = " + i + " ** j = " + j + " **");
        System.out.println();
    }

    // Find new empty cell among neighbours
    private int[] findEmptyCell (int i, int j) {
        int[] result = new int[2];

        // Check cells above and below the current location
        for (int x=i-1; x<i+2; x++)
            try {
                if (cells[x][j] == 0) {
                    result[0] = x;
                    result[1] = j;
                    return result;
                }
            } catch (IndexOutOfBoundsException e) {
            }

        // Check cells to the right and left of the location
        for (int x=j-1; x<j+2; x++)
            try {
                if (cells[i][x] == 0) {
                    result[0] = i;
                    result[1] = x;
                    return result;
                }
            } catch (IndexOutOfBoundsException e) {
            }

        // If no empty cell is found return null
        return null;
    }
}