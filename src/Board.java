import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * @author: Marcus Trujillo
 * @version:
 * represents the board we're playing off of.
 * I think for the sake of ease later, it would make sense to have two playable lists. One for up, and one for down.
 */
public class Board{
    private int size;
    private BoardSpace[][] boardSpaces;

    /**
     * this constructor is meant for standard boards
     */
    public Board(){
        boardSpaces = Constants.standardBoard; //need to get this from Constants because a standard board is read in from file
        this.size = 15;
        Constants.setBoardDimensions(15);
    }

//i don't think this is going to be used you might end up deleting this
    public Board(int size){
        this.size = size;
        boardSpaces = new BoardSpace[size][size];
    }

    public Board(int size, BoardSpace[][] board){
        this.size = size;
        this.boardSpaces = board;
    }

    //Getters and Setters live below this line
    /**
     *
     * @return
     */
    public int getSize() { return size; }

    /**
     *
     * @param row
     * @param column
     * @return the specific space on the board at the given row and column
     */
    public BoardSpace getSpaceAt(int row, int column){
        return boardSpaces[row][column];
    }

    public BoardSpace[][] getBoard(){ return boardSpaces;}
}
