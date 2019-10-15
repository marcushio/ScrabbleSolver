import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * @author: Marcus Trujillo
 * @version: 9/26/2019
 * This is the tile that gets displayed in the GUI.
 * I should probably change the name to display square, because I'm treating display spaces and tiles the same I think
 */
class DisplaySquare extends Canvas {
    private final int TEXT_XSTART = 1, TEXT_YSTART = 45, NUM_YSTART = 15, NUM_XSTART = 25;
    private int rowIndex, colIndex;
    private DisplayTile tile;

    public DisplaySquare(BoardSpace space, Controller controller) {
        setWidth(Constants.TILE_WIDTH); setHeight(Constants.TILE_HEIGHT);
        if(space.isEmpty()) this.tile = null;
        else this.tile = new DisplayTile(space.getTile(), controller);
        this.rowIndex = space.getRowIndex();
        this.colIndex = space.getColIndex();

        GraphicsContext gc = this.getGraphicsContext2D();
        gc.setLineWidth(3);
        if(space.isEmpty()){
            gc.setStroke(Color.BLACK);
            gc.strokeRect(0,0,Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
            if(space.getMultiplierType().equals(MultiplierType.LETTER)){
                gc.strokeRect(0,0,Constants.TILE_WIDTH,Constants.TILE_HEIGHT);
                gc.setLineWidth(1);
                gc.strokeText(Constants.LETTER, TEXT_XSTART, TEXT_YSTART );
                gc.strokeText(Integer.toString(space.getMultiplyBy()) + "x", NUM_XSTART, NUM_YSTART);
            }
            else if (space.getMultiplierType().equals(MultiplierType.WORD)){
                gc.setLineWidth(1);
                gc.strokeText(Constants.WORD, TEXT_XSTART, TEXT_YSTART );
                gc.strokeText(Integer.toString(space.getMultiplyBy()) + "x", NUM_XSTART, NUM_YSTART);
            }
        } else {
            gc.strokeRect(0,0,Constants.TILE_WIDTH,Constants.TILE_HEIGHT);
            gc.setLineWidth(1);
            gc.strokeText(space.getTile().getLetter(), NUM_XSTART, NUM_YSTART);
        }
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, controller.new SpaceHandler());
    }

    public int getColIndex() {
        return colIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    //public boolean isSelected(){ return isSelected; }
}
