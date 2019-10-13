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


    public Controller(HashSet<String> dictionary, Trie trie ){
        this.dictionary = dictionary;
        ai = new AI(trie);
        this.human = new Player();
    }

    public class SpaceHandler implements EventHandler {
        @Override
        public void handle(Event event){
            //code to handle a space being clicked
        }
    }

    public class TileHandler implements EventHandler {
        @Override
        public void handle(Event event){
            //code to handle a tile being clicked
        }
    }

    public void setDictionary(HashSet<String> newDictionary){
        this.dictionary = newDictionary;
    }
    public void setAITrie(Trie trie){
        ai.setTrie(trie);
    }

}
