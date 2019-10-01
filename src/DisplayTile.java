import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author: Marcus Trujillo
 * @version: brief class description
 */
class DisplayTile extends Canvas {
    int rowIndex, colIndex;
    String letter;

    public DisplayTile(BoardSpace space) {
        setWidth(Constants.TILE_WIDTH); setHeight(Constants.TILE_HEIGHT);
        if(space.isEmpty()) this.letter = null;
        else this.letter = space.getLetter();
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
            gc.strokeText(space.getLetter(), 20, 20);
        }
    }

    public int getColIndex() {
        return colIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }
}
