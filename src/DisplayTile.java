import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * @author: Marcus Trujillo
 * @version:
 * brief class description
 */
public class DisplayTile extends Canvas {
    public int NUM_YSTART = 15, NUM_XSTART = 25;
    private boolean isSelected;
    private Tile tile;

    public DisplayTile(Tile tile, Controller controller){
        isSelected = false;
        this.tile = tile;
        setWidth(Constants.TILE_WIDTH); setHeight(Constants.TILE_HEIGHT);
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.setLineWidth(3);
        gc.setStroke(Color.BLACK);
        if(tile.isSelected()) {
            gc.setStroke(Color.CYAN);
            gc.strokeRect(0,0,Constants.TILE_WIDTH,Constants.TILE_HEIGHT);
        } else { gc.strokeRect(0,0,Constants.TILE_WIDTH,Constants.TILE_HEIGHT ); }
        gc.setLineWidth(1);
        gc.strokeText(tile.getLetter(), NUM_XSTART, NUM_YSTART);
        this.setOnMouseClicked(controller.new TileHandler());
    }

    public boolean isSelected(){ return isSelected; }

    public Tile getTile(){ return tile; }

    public void select(){
        isSelected = true;
        //tile.setSelected(true); I'm going a different route, let's see if it pans out
    }
}
