
import java.util.ArrayList;
/**
 * @author: Marcus Trujillo
 * @version: 10/1/2019
 * This encapsulates a move in this scrabble game.
 */

public class Move {
    private ArrayList<Tile> tiles;
    private int startRow;
    private int startCol;
    private boolean across;
    int score;
    Player player;
    Anchor anchor;

    public Move(ArrayList<Tile> tiles, int startRow, int startCol, boolean across, int score, Player player) {
        this.tiles = tiles;
        this.startRow = startRow;
        this.startCol = startCol;
        this.across = across;
        this.score = score;
        this.player = player;
        this.anchor = null;
    }

    public Move(ArrayList<Tile> tiles, int startRow, int startCol, boolean across, Player player) {
        this.tiles = tiles;
        this.startRow = startRow;
        this.startCol = startCol;
        this.across = across;
        this.player = player;
        this.anchor = null;
    }

    public Move(ArrayList<Tile> tiles, int startRow, int startCol, boolean across, int score, Player player, Anchor anchor) {
        this.tiles = tiles;
        this.startRow = startRow;
        this.startCol = startCol;
        this.across = across;
        this.score = score;
        this.player = player;
        this.anchor = anchor;
    }

    public boolean isAcross(){ return across; }

    public int getStartCol() {
        return startCol;
    }

    public int getStartRow() {
        return startRow;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    //should add a proper player's move isValid

    public boolean isValid(BoardSpace[][] boardArray){
        int row = startRow;
        int col = startCol;

        for (Tile tile: tiles){
            //boardArray[row][col].addTile(tile);
            if (across){
                col++;
            } else {
                row++;
            }
            player.removeTrayTile(tile);
        }
        if(isAcross() && col+1 < boardArray.length && !boardArray[row][col +1].isEmpty()){ return false; } //these are a bit of a hack to fix the postfix bugs
        if(!isAcross() && row+1 < boardArray.length && !boardArray[row +1][col].isEmpty()){ return true; } //if I find time I'll need a better soln.
        if(isAcross() && startCol-1 >   0          && !boardArray[row][startCol -1].isEmpty()){ return false; }
        if(!isAcross() && startRow-1 >     0  && !boardArray[startRow -1 ][col].isEmpty()){ return false; }
        return true;
    }

    public BoardSpace[][] execute(BoardSpace[][] boardArray){
        int row = startRow;
        int col = startCol;

        for (Tile tile: tiles){
            boardArray[row][col].addTile(tile);
            if (across){
                col++;
            } else {
                row++;
            }
            player.removeTrayTile(tile);
        }
        return boardArray;
    }
    //maybe i oughta make a toString for readability?

}
