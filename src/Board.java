import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * @author: Marcus Trujillo
 * @version:
 * represents the board we're playing off of.
 */
public class Board extends Observable {
    int size;
    BoardSpace[][] board;
    List<String> playableLetters;

    public Board(int size){
        this.size = size;
        board = new BoardSpace[size][size];
    }

    public Board(int size, BoardSpace[][] board){
        this.size = size;
        this.board = board;
        //read through board to populate playables
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
    public List<String> getPlayableLetters() { return playableLetters; }

    /**
     *
     * @param row
     * @param column
     * @return the specific space on the board at the given row and column
     */
    public BoardSpace getSpaceAt(int row, int column){
        return board[row][column];
    }
}
