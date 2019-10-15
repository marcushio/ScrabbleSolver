import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.HashSet;

/**
 * @author: Marcus Trujillo
 * @version: 9/20/2019
 * Handles the logic of the game
 */
public class Controller {
    HashSet<String> dictionary;
    AI ai;
    Player human;
    Model model;
    DisplaySquare selectedSpace;
    DisplayTile selectedTile;

    public Controller(HashSet<String> dictionary, Trie trie, Model model ){
        this.dictionary = dictionary;
        ai = new AI(trie);
        this.human = new Player();
        this.model = model;
    }

    public void addModel(Model newModel){ this.model = newModel; }
    public void setDictionary(HashSet<String> newDictionary){
        this.dictionary = newDictionary;
    }
    public void setAITrie(Trie trie){
        ai.setTrie(trie);
    }


//Event handlers live below this line
    public class SpaceHandler implements EventHandler {
        @Override
        public void handle(Event event){
            DisplaySquare currentDisplaySpace = (DisplaySquare) event.getSource();
            if(selectedTile != null){ // a space was clicked while a tile was selected.. put that tile on that space and take it out da tray
                paintTileOnSpace(selectedTile, currentDisplaySpace);
                unpaintTrayTile(selectedTile);
                selectedTile = null; //nothing should be selected after a tile is placed
                selectedSpace = null;
            } else { // no tile selected we just have to worry about spaces
                if(selectedSpace == null){ //we had no space selected before
                    highlight(currentDisplaySpace);
                    selectedSpace = currentDisplaySpace;
                } else if(selectedSpace != null){ // we had a previously selected space, we have to update which is selected
                    unhighlight(selectedSpace);
                    highlight(currentDisplaySpace);
                    selectedSpace = currentDisplaySpace;
                }
            }

        }
    }

    public class TileHandler implements EventHandler {
        @Override
        public void handle(Event event){
            DisplayTile displayTile = (DisplayTile) event.getSource();
            if(selectedTile != null){ //if we already have a selected tile unhighlight old one highlight new one
                GraphicsContext gc = selectedTile.getGraphicsContext2D();
                gc.setLineWidth(3);
                gc.setStroke(Color.BLACK);
                gc.strokeRect(0,0,Constants.TILE_WIDTH,Constants.TILE_HEIGHT);
                selectedTile = displayTile;
                gc = displayTile.getGraphicsContext2D();
                gc.setLineWidth(3);
                gc.setStroke(Color.CYAN);
                gc.strokeRect(0, 0, Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
            }
            if(selectedTile == displayTile){ //we're unselecting a tile
                GraphicsContext gc = selectedTile.getGraphicsContext2D();
                gc.setLineWidth(3);
                gc.setStroke(Color.BLACK);
                gc.strokeRect(0,0,Constants.TILE_WIDTH,Constants.TILE_HEIGHT);
                selectedTile = null;
            } else if (selectedTile == null){ //no previously selected tile
                if(selectedSpace != null){ //we just selected a tile and we have a space selected
                    paintTileOnSpace(displayTile, selectedSpace);
                } else { //selected a tile and no space selected
                    GraphicsContext gc = displayTile.getGraphicsContext2D();
                    gc.setLineWidth(3);
                    gc.setStroke(Color.CYAN);
                    gc.strokeRect(0, 0, Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
                    selectedTile = displayTile;
                }
            }
            if(selectedTile == null){ System.out.println("tile selected is: null");}
            else System.out.println("tile selected is: " + selectedTile.getTile().getLetter());
        }
    }

    private void paintTileOnSpace(DisplayTile tile, DisplaySquare space){
        GraphicsContext gc = space.getGraphicsContext2D();
        gc.clearRect(3,3,Constants.TILE_WIDTH - 6, Constants.TILE_HEIGHT -6);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.strokeText(tile.getTile().getLetter(), tile.NUM_XSTART, tile.NUM_YSTART );
        gc.strokeText(tile.getTile().getPointValue() + "", Constants.TILE_WIDTH -10, Constants.TILE_HEIGHT -10);
        gc.setLineWidth(3);
        gc.strokeRect(0,0, Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
    }

    private void unpaintTrayTile(Canvas canvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0,0,Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
    }

    private void highlight(Canvas canvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.CYAN);
        gc.setLineWidth(3);
        gc.strokeRect(0,0,Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
    }

    private void unhighlight(Canvas canvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3);
        gc.strokeRect(0,0, Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
    }

    public class MoveButtonHandler implements EventHandler {
        @Override
        public void handle(Event event){
            //code to handle a move
        }
    }

    public class ResetButtonHandler implements EventHandler {
        @Override
        public void handle(Event event){
            //clear should reset things back to previous state before putting disp tiles onto board.
        }
    }
}
