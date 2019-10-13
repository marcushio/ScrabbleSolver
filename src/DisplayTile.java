import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * @author: Marcus Trujillo
 * @version: brief class description
 */
public class DisplayTile extends Canvas {
    int NUM_YSTART = 15, NUM_XSTART = 25;

    public DisplayTile(Tile tile, Controller controller){
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        if(tile.isSelected()) {
            gc.setStroke(Color.CYAN);
            gc.strokeRect(0,0,Constants.TILE_WIDTH,Constants.TILE_HEIGHT);
        } else { gc.strokeRect(0,0,Constants.TILE_WIDTH,Constants.TILE_HEIGHT ); }

        gc.strokeText(tile.getLetter(), NUM_XSTART, NUM_YSTART);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, controller.new TileHandler());
    }
}
