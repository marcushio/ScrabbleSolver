import javafx.event.Event;
import javafx.event.EventHandler;

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
            //code to handle a space being clicked
        }
    }

    public class TileHandler implements EventHandler {
        @Override
        public void handle(Event event){
            DisplayTile displayTile = (DisplayTile) event.getSource();
            model.selectTileInTray(displayTile.getTile());
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
