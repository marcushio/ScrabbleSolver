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
    GUI gui;
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
                //once i know disp works I'll have to add actual tile to space and maybe prevent overlaps? or at least swap tiles
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
            DisplayTile currentDisplayTile = (DisplayTile) event.getSource();
            if(selectedSpace != null){ //we selected a tile while a space was selected, add tile to this space
                paintTileOnSpace(currentDisplayTile, selectedSpace);//paint it on
                unpaintTrayTile(currentDisplayTile);//don't have tile in tray
                //once i know disp works I'll have to actually give the sapce a tile
                selectedSpace = null;
                selectedTile = null; //nothing should be selected after placing tile
            } else { //no space selected at the same time as this tile
                if(selectedTile == null){ // there was not a tile selected before
                    highlight(currentDisplayTile);
                    selectedTile = currentDisplayTile;
                } else if(selectedTile != null){ //there was a selected tile before we selected this one
                    unhighlight(selectedTile);
                    highlight(currentDisplayTile);
                    selectedTile = currentDisplayTile;
                }

            }
        }
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
            model.updateGUI();
        }
    }
    //painting utility methods
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
}
