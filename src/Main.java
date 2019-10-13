import javafx.application.Application;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

/**
 * @author: Marcus Trujillo
 * @version 9/20/2019
 * This class sets up all the components and launches the program.
 *
 */
public class Main extends Application {
    Stage primaryStage;
    Constants info;
    GUI gui;
    Controller controller;
    Board board;

    /**
     * Launches the program
     * @param args
     */
    public static void main(String[] args) {
        Constants.readTileInfo();
        Constants.setDictionaryFilename(args[0]);
        Constants.readStandardBoard();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        board = new Board();
        controller = new Controller();
        readDictionary(Constants.dictionaryFilename);
        gui = new GUI(primaryStage, board, controller);
    }

    /**
     * This reads in the dictionary of valid words in the scrabble game so that the solver has access to all the
     * words and so there is a lookup dictionary to check if a player's words are valid.
     * @param filename
     */
    private void readDictionary(String filename) {
        HashSet<String> dict = new HashSet<String>();
        Trie trie = new Trie();
        String word = null;
        try (BufferedReader fileReader = new BufferedReader(new FileReader("res" + File.separator +  filename))) {
            while ((word = fileReader.readLine()) != null) {
                dict.add(word);
                trie.addWord(word);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        controller.setDictionary(dict);
        controller.setAITrie(trie);
    }
}
