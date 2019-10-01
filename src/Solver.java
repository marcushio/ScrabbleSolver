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
 * @version: 9/20/2019
 * brief class description
 */
public class Solver {
    private String filename;
    private Trie trie;
    private BoardSpace[][] board;
    //private Board board;
    private List<Character> tray = new ArrayList<Character>();
    private HashSet<String> dict;

    public void solve(){

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
            String line = null;
            System.out.println("Enter your board configuration");
            int boardSize = Integer.parseInt(scanner.nextLine());
            board = new BoardSpace[boardSize][boardSize];

            for(int i = 0; i < boardSize; i++){
                for(int j = 0; j < boardSize; j++){
                    line = scanner.next();
                    board[i][j] = new BoardSpace(line);
                }
            }

            String tray = scanner.next();
            System.out.println("Tray: " + tray);

            /* this was just to show everything is getting entered.
            for(int i = 0; i < boardSize; i++){
                for(int j = 0; j < boardSize; j++){
                    System.out.println(board[i][j]);
                }
            }
             */
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args){
        Solver solver = new Solver();
        solver.readDictionary(args[0]);
        System.out.println("dictionary file has been read.");
        //solver.readBoard();
    }
}
