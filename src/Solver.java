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
    private BoardSpace[][] board;
    //private Board board;
    //private List<Tile> tray = new ArrayList<Tile>();
    private String tray;
    private HashSet<String> dict;

    public Solver(String filename){
        readDictionary(filename);
    }
    public Solver(Trie trie){
        this.trie = trie;
    }

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

    private void readBoard(){
        try(Scanner scanner = new Scanner(System.in)){
            String token = null;
            System.out.println("Enter your board configuration");
            int boardSize = Integer.parseInt(scanner.nextLine());
            board = new BoardSpace[boardSize][boardSize];

            for(int i = 0; i < boardSize; i++){
                for(int j = 0; j < boardSize; j++){
                    token = scanner.next();
                    board[i][j] = new BoardSpace(token);
                }
            }
            tray = scanner.next();
            //printBoard(boardSize);
            //System.out.println("Tray: " + tray);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void solve(){
        Trie.Node currentNode;
        //first
    }

    //this is just for debugging go ahead and delete this later
    private void printBoard(int boardSize){
            for(int i = 0; i < boardSize; i++){
                for(int j = 0; j < boardSize; j++){
                    System.out.println(board[i][j]);
                }
            }

    }

    public static void main(String[] args){
        Solver solver = new Solver(args[0]);
        System.out.println("dictionary file has been read.");
        //solver.readBoard();
    }
}
