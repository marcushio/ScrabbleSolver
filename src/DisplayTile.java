import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author: Marcus Trujillo
 * @version: 9/26/2019
 * This is the tile that gets displayed in the GUI.
 */
class DisplayTile extends Canvas {
    int rowIndex, colIndex;
    String letter;

    public DisplayTile(BoardSpace space) {
        setWidth(Constants.TILE_WIDTH); setHeight(Constants.TILE_HEIGHT);
        if(space.isEmpty()) this.letter = null;
        else this.letter = space.getTile().getLetter();
        this.rowIndex = space.getRowIndex();
        this.colIndex = space.getColIndex();

        GraphicsContext gc = this.getGraphicsContext2D();
        if(space.isEmpty()){
            gc.setStroke(Color.BLACK);
            gc.strokeRect(0,0,Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
            if(space.getMultiplierType().equals(MultiplierType.LETTER)){
                gc.strokeRect(0,0,Constants.TILE_WIDTH,Constants.TILE_HEIGHT);
                gc.strokeText(Constants.LETTER, 20, 3);
                gc.strokeText(Integer.toString(space.getMultiplyBy()), 10, 20);
            }
            else if (space.getMultiplierType().equals(MultiplierType.WORD)){
                gc.strokeText(Constants.WORD, 20, 3);
                gc.strokeText(Integer.toString(space.getMultiplyBy()), 10, 20);
            }

        } else {
            gc.strokeRect(0,0,Constants.TILE_WIDTH,Constants.TILE_HEIGHT);
            gc.strokeText(space.getTile().getLetter(), 20, 20);
        }
    }

    public int getColIndex() {
        return colIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }
}
