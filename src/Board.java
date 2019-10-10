import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * @author: Marcus Trujillo
 * @version:
 * represents the board we're playing off of.
 * I think for the sake of ease later, it would make sense to have two playable lists. One for up, and one for down.
 */
public class Board extends Observable {
    private int size;
    private BoardSpace[][] boardSpaces;
    private Tile[][] board;
    private List<Tile> downPlayableLetters;
    private List<Tile> acrossPlayableLetters;
    private static final Board instance;

    static{ instance = new Board();}

    public static Board getInstance(){
        return instance;
    }

    public Board(int size){
        this.size = size;
        boardSpaces = new BoardSpace[size][size];
    }

    public Board(int size, BoardSpace[][] board){
        this.size = size;
        this.boardSpaces = board;
        playableLetters = new ArrayList<Tile>();
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if( ! board[i][j].isEmpty() ) {
                    playableLetters.add( board[i][j].getTile() ) ;
                    //iterate through and see which are playable in which direction
                }
            }
        }
    }

    //this is just for debugging go ahead and delete this later
    private void printBoard(int boardSize){
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                System.out.println(boardSpaces[i][j]);
            }
        }
    }


    //Getters and Setters live below this line
    /**
     *
     * @return
     */
    public int getSize() { return size; }

    /**
     *
     * @return
     */
    public List<Tile> getPlayableLetters() { return playableLetters; }

    /**
     *
     * @param row
     * @param column
     * @return the specific space on the board at the given row and column
     */
    public BoardSpace getSpaceAt(int row, int column){
        return boardSpaces[row][column];
    }

    public Tile[][] getBoard(){ return board;}
}
