import java.util.ArrayList;
import java.util.List;

/**
 * @author: Marcus Trujillo
 * @version:
 * represents the board we're playing off of.
 */
public class Board {
    int size;
    BoardSpace[][] board;
    List<String> playableLetters;

    public int getSize() {
        return size;
    }

    public List<String> getPlayableLetters() {
        return playableLetters;
    }

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
