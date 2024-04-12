// 150122827 Başak Akyüz - 150122531 Oğuz Fatih Akgemci - 150121063 Rabia
// This class represents fixed cell

package objects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static utilz.Constants.FIXED_CELL_IMAGE;
import static utilz.Constants.FIXED_CELL_IMAGE_WIDTH;

public class FixedCell extends ImageView {
    private int rowIndex;
    private int columnIndex;

    public FixedCell(int cellID) {
        rowIndex = (cellID-1)/10;
        columnIndex = (cellID-1)%10;
        setImage(new Image(FIXED_CELL_IMAGE, FIXED_CELL_IMAGE_WIDTH, 0, true, false));
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }
}
