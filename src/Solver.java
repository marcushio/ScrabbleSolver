import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * @author: Marcus Trujillo
 * @version: 10/2/2019
 * Takes a board configuration and outputs the highest point play that can be made.
 */
public class Solver {
    private Trie trie;
    private BoardSpace[][] boardSpaces;
    //private List<Tile> playableLetters;
    private Board board;
    private ArrayList<Tile> tray;
    private String stringTray;
    private HashSet<String> dict;

    public Solver(String filename){
        readDictionary(filename);
        tray = new ArrayList<Tile>();
    }
    public Solver(Trie trie){
        this.trie = trie;
    }

    public class BoardConfig{ //use subclass to store all the board configurations that are read in
        int size;
        BoardSpace[][] board;
        ArrayList<Tile> tray;
        public BoardConfig(int size, BoardSpace[][] board, ArrayList<Tile> tray){
            this.size = size;
            this.board = board;
            this.tray = tray;
        }

        public int getSize() {
            return size;
        }

        public ArrayList<Tile> getTray() {
            return tray;
        }

        public BoardSpace[][] getBoard() {
            return board;
        }
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
        //try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filename)))){
            while ((word = fileReader.readLine()) != null) {
                dict.add(word); //I get the feeling I'm not going to the need the HashMap dict in solver
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
    private ArrayList<BoardConfig> readBoards(){
        ArrayList<BoardConfig> boardConfigs = new ArrayList<BoardConfig>();
        System.out.println("Enter your board config");
        Scanner scanner = new Scanner(System.in);
            while(scanner.hasNext()) {
                String token = null; // i can probably integrate this instead of explicitly stating this var
                int boardSize = Integer.parseInt(scanner.next());
                Constants.setBoardDimensions(boardSize);
                //System.out.println("Enter a board");
                boardSpaces = new BoardSpace[boardSize][boardSize];
                for (int i = 0; i < boardSize; i++) {
                    for (int j = 0; j < boardSize; j++) {
                        token = scanner.next();
                        boardSpaces[i][j] = new BoardSpace(token.toLowerCase());
                    }
                }
                //System.out.println("Enter a tray");
                stringTray = scanner.next();
                //tray = makeTrayFromString(stringTray);
                boardConfigs.add(new BoardConfig(boardSize, boardSpaces, makeTrayFromString(stringTray)));
                //board = new Board(boardSize, boardSpaces);
                //printBoard(boardSize);
                //System.out.println("Tray: " + tray);
            }
        return boardConfigs;
    }

    private ArrayList<Tile> makeTrayFromString(String stringTray){
        ArrayList<Tile> tray = new ArrayList<Tile>();
        for(int i = 0; i < stringTray.length(); i++){
            String letter = stringTray.substring(i, i+1);
            tray.add(new Tile(letter, Constants.letterPoints.get(letter)));
        }
        return tray;
    }

    private boolean boardIsEmpty(BoardSpace[][] board){
        for(BoardSpace[] row: board){
            for(BoardSpace space: row){
                if(!space.isEmpty()) return false;
            }
        }
        return true;
    }

    public void printBoard(BoardSpace[][] board){
        for(BoardSpace[] row: board){
            System.out.println();
            for(BoardSpace space: row)
                System.out.print(space.toString() + " ");
        }
    }

    public static void main(String[] args){
        Constants constantValues = new Constants();
        Solver solver = new Solver(args[0]);
        System.out.println("dictionary file has been read.");
        ArrayList<BoardConfig> boardConfigs = solver.readBoards();
        System.out.println("boards have been read");
        AI ai = new AI(new Player());
        ai.setTrie(solver.trie);
        for(BoardConfig config : boardConfigs) {
            constantValues.setBoardDimensions(config.getSize());
            ai.setBoard(config.getBoard());
            System.out.println("\n\nInput Board");
            solver.printBoard(ai.boardSpaces);
            ai.setTray(config.getTray());
            System.out.print("\nTray: ");
            for(Tile tile : ai.getTray()){
                System.out.print(tile.letter);
            }
            Move newMove = null;
            if (solver.boardIsEmpty(config.getBoard())) {
                newMove = ai.makeFirstMove();
            } //this'll be problematic with smaller boards atm
            else {
                newMove = ai.makeSubsequentMove();
            }

            if(newMove == null) {System.out.println("Couldn't find a move... I'm  sorry");}
            else {
                System.out.println("\nmove returned... AI played "+ newMove.getWord() + " for " + Scorer.score(newMove, ai.boardSpaces));
                BoardSpace[][] newBoard = newMove.execute(config.getBoard());
                System.out.print("move executed... here's the new board configuration");
                solver.printBoard(newBoard);
            }
        }
    }
}
