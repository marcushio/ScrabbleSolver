import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

/**
 * @author: Marcus Trujillo
 * @version: 10/2/2019
 * Takes a board configuration and outputs the highest point play that can be made.
 */
public class Solver {
    private Trie trie;
    private BoardSpace[][] boardSpaces;
    private List<Tile> playableLetters;
    private Board board;
    private List<Tile> tray;
    private String stringTray;
    private HashSet<String> dict;

    public Solver(String filename){
        //all we're doing is traversing a tree and seeing which one is that highest scoring word that's it
        // we traverse based on what we have in our tray
        readDictionary(filename);
        tray = new ArrayList<Tile>();
    }
    public Solver(Trie trie){
        this.trie = trie;
    }

    /**
     * This reads in the dictionary of valid words in the scrabble game so that the solver has access to all the
     * words and stores them in it's trie for easier lookup later.
     * @param filename
     */
    private void readDictionary(String filename) {
        dict = new HashSet<String>();
        trie = new Trie();
        String word = null;
        try (BufferedReader fileReader = new BufferedReader(new FileReader("res" + File.separator +  filename))) {
            while ((word = fileReader.readLine()) != null) {
                dict.add(word);
                trie.addWord(word);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Used to read the board configuration in. The specs say that it will be fed in through standard input. It sets
     * all it's fields according to the input.
     * The format is the size of the board in the first line, followed by the actual board represented as a string and last the tray.
     */
    private void readBoard(){
        try(Scanner scanner = new Scanner(System.in)){
            String token = null;
            System.out.println("Enter your board configuration");
            int boardSize = Integer.parseInt(scanner.nextLine());
            boardSpaces = new BoardSpace[boardSize][boardSize];
            for(int i = 0; i < boardSize; i++){
                for(int j = 0; j < boardSize; j++){
                    token = scanner.next();
                    boardSpaces[i][j] = new BoardSpace(token);
                    System.out.println(i + " " + j);
                }
            }
            stringTray = scanner.next();
            board = new Board(boardSize, boardSpaces);
            //printBoard(boardSize);
            //System.out.println("Tray: " + tray);
        } catch (NullPointerException ex){
            System.out.println("Null pointer thrown in readboard at ");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args){
        Constants constantValues = new Constants();
        Solver solver = new Solver(args[0]);
        System.out.println("dictionary file has been read.");
        solver.readBoard();
    }
}
