import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.HashSet;
import java.util.Scanner;

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
    DisplaySquare selectedSquare;
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
            DisplaySquare dispBoardSquare = (DisplaySquare) event.getSource();
            //selection logic
            if(selectedSquare != null){ //we already have a selected square so we need to unhighlight the old one
                GraphicsContext gc = selectedSquare.getGraphicsContext2D();
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1);
                gc.strokeRect(0,0,Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
            } else if(dispBoardSquare == selectedSquare){ //deselect if you clicked the thing that's already selected
                GraphicsContext gc = selectedSquare.getGraphicsContext2D();
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1);
                gc.strokeRect(0,0,Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
                selectedSquare = null;
            }
            GraphicsContext gc = selectedSquare.getGraphicsContext2D();
            gc.setStroke(Color.CYAN);
            gc.setLineWidth(3);
            gc.strokeRect(0,0,Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
            selectedSquare = dispBoardSquare; //update our selected square
            if(selectedTile != null){
                gc.setLineWidth(1);
                gc.setStroke(Color.BLACK);
                gc.clearRect(0,0,Constants.TILE_WIDTH,Constants.TILE_HEIGHT);
                gc.strokeRect(0,0,Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
                gc.strokeText(selectedTile.getTile().getLetter(), 20, 20);
                gc.strokeText(selectedTile.getTile().getPointValue() + "", 40,40);
                GraphicsContext trayBrush = selectedTile.getGraphicsContext2D();
                trayBrush.clearRect(0,0,Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
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
            if(selectedTile != null && selectedTile == displayTile){ //we're unselecting a tile
                GraphicsContext gc = selectedTile.getGraphicsContext2D();
                gc.setLineWidth(3);
                gc.setStroke(Color.BLACK);
                gc.strokeRect(0,0,Constants.TILE_WIDTH,Constants.TILE_HEIGHT);
                selectedTile = null;
            } else if (selectedTile == null){ //nothing selected
                GraphicsContext gc = displayTile.getGraphicsContext2D();
                gc.setLineWidth(3);
                gc.setStroke(Color.CYAN);
                gc.strokeRect(0, 0, Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
                selectedTile = displayTile;
            }
        }
    }

    public class MoveButtonHandler implements EventHandler {
        @Override
        public void handle(Event event){
            //code to handle a move
        }
    }

    public class ClearButtonHandler implements EventHandler {
        @Override
        public void handle(Event event){
            //clear should reset things back to previous state before putting disp tiles onto board.
        }
    }
}
