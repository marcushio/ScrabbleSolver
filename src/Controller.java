import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
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
    ArrayList<DisplaySquare> moveSquares ;

    public Controller(HashSet<String> dictionary, Trie trie, Model model ){
        this.dictionary = dictionary;
        ai = new AI(trie);
        this.human = new Player();
        this.model = model;
        moveSquares = new ArrayList<DisplaySquare>();
    }

    public void addModel(Model newModel){ this.model = newModel; }
    public void addGUI(GUI gui){ this.gui = gui; }
    public void setDictionary(HashSet<String> newDictionary){
        this.dictionary = newDictionary;
    }
    public void setAITrie(Trie trie){
        ai.setTrie(trie);
    }


//Event handlers live below this line
    public class SpaceHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event){
            DisplaySquare currentDisplaySpace = (DisplaySquare) event.getSource();
            if(!event.isConsumed()) {
                if (selectedTile != null) { // a space was clicked while a tile was selected.. put that tile on that space and take it out da tray
                    paintTileOnSpace(selectedTile, currentDisplaySpace);
                    unpaintTrayTile(selectedTile);
                    currentDisplaySpace.setTile(selectedTile);
                    //moveSquares.add(currentDisplaySpace);

                    //System.out.println("square with " + currentDisplaySpace.getTile().getTile().getLetter() + " added at spot 1");

                    selectedTile = null; //nothing should be selected after a tile is placed
                    selectedSpace = null;
                } else { // no tile selected we just have to worry about spaces
                    if (selectedSpace == null) { //we had no space selected before
                        highlight(currentDisplaySpace);
                        selectedSpace = currentDisplaySpace;
                    } else if (selectedSpace != null) { // we had a previously selected space, we have to update which is selected
                        unhighlight(selectedSpace);
                        highlight(currentDisplaySpace);
                        selectedSpace = currentDisplaySpace;
                    }
                }
                if (currentDisplaySpace.getTile() != null) {
                    moveSquares.add(currentDisplaySpace);
                    System.out.println(event.toString());
                    System.out.println("square with " + currentDisplaySpace.getTile().getTile().getLetter() + " added at spot 2");
                }
            }
            event.consume();
        }
    }

    public class TileHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event){
            if(!event.isConsumed()) {
                DisplayTile currentDisplayTile = (DisplayTile) event.getSource();
                if (selectedSpace != null) { //we selected a tile while a space was selected, add tile to this space
                    paintTileOnSpace(currentDisplayTile, selectedSpace);//paint it on
                    unpaintTrayTile(currentDisplayTile);//don't have tile in tray
                    selectedSpace.setTile(currentDisplayTile);
                    moveSquares.add(selectedSpace);
                    System.out.println("added " + selectedSpace.getTile().getTile().getLetter() );
                    selectedSpace = null;
                    selectedTile = null; //nothing should be selected after placing tile
                } else { //no space selected at the same time as this tile
                    if (selectedTile == null) { // there was not a tile selected before
                        highlight(currentDisplayTile);
                        selectedTile = currentDisplayTile;
                    } else if (selectedTile != null) { //there was a selected tile before we selected this one
                        unhighlight(selectedTile);
                        highlight(currentDisplayTile);
                        selectedTile = currentDisplayTile;
                    }

                }
                event.consume();
            }
        }
    }

    public class DownButtonHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event){
            ArrayList<Tile> moveTiles = new ArrayList<Tile>();
            for(DisplaySquare displaySquare : moveSquares){
                moveTiles.add(displaySquare.getTile().getTile()); //lol that was a little lack of foresight
            }                                                       //first one gets displayTile second gets tile

            int startRow = moveSquares.get(0).getRowIndex();
            int startCol = moveSquares.get(0).getColIndex();
            Move newMove = new Move(moveTiles, startRow, startCol, false, human );
            System.out.println("oh jeez we're trying a move...");
            //newMove.execute(model.getBoard().getBoard()); //lol how many times did I do this? first gets Board second gets Boardspaces[][];
            model.executeMove(newMove);
            moveSquares.clear();
        }
    }

    public class AcrossButtonHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event){
            ArrayList<Tile> moveTiles = new ArrayList<Tile>();
            for(DisplaySquare displaySquare : moveSquares){
                moveTiles.add(displaySquare.getTile().getTile()); //lol that was a little lack of foresight
            }                                                       //first one gets displayTile second gets tile
            int startRow = moveSquares.get(0).getRowIndex();
            int startCol = moveSquares.get(0).getColIndex();
            Move newMove = new Move(moveTiles, startRow, startCol, true, human );
            System.out.println("oh jeez we're trying a move...");
            //newMove.execute(model.getBoard().getBoard()); //lol how many times did I do this? first gets Board second gets Boardspaces[][];
            model.executeMove(newMove);
            moveSquares.clear();
        }
    }

    public class ResetButtonHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event){
            //i could store tempboard tiles then return them to tray here...

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
