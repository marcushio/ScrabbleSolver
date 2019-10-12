import java.util.HashMap;
import java.util.Map;

/**
 * @author: Marcus Trujillo
 * @version: 9/26/2019
 * This represents each individual space on the board. They store if they have multipliers and if they're empty
 *
 */
enum MultiplierType{LETTER, WORD, NONE;}

public class BoardSpace {
    private Map<String, Integer> info = new HashMap<String, Integer>(Constants.letterPoints); //just an object that stores info about the game like tile scores etc.
    private Tile tile; //null if no tile has been played on this space
    private int multiplyBy;
    private MultiplierType multiplierType;
    private int rowIndex, colIndex;
    private String stringRep;

    /**
     * We construct the space based off the String read in from standard input.
     * @param textSpace is the string that is read in from Standard input. ".." means it's empty, "3." means a triple
     *                  word, ".3" would be a triple letter. We expect good input with an integer repping the multiplier
     */
    public BoardSpace(String textSpace){
        multiplyBy = 1;
        info = new HashMap<String, Integer>(Constants.letterPoints);
        if(textSpace.length() < 2) {
            tile = new Tile(textSpace, info.get(textSpace));
            stringRep = " " + textSpace;
        } else if (textSpace.length() == 2) {
            stringRep = textSpace;
            if (textSpace.charAt(0) != '.') {
                multiplyBy = Integer.parseInt(textSpace.substring(0, 1));
                multiplierType = MultiplierType.WORD;
            }
            else if (textSpace.charAt(1) != '.') {
                multiplyBy = Integer.parseInt(textSpace.substring(1));
                multiplierType = MultiplierType.LETTER;
            } else {
                multiplierType = MultiplierType.NONE;
            }
        }
    }

    /**
     * constructor for a blank space on the board
     * @param row
     * @param column
     */
    public BoardSpace(int row, int column){
        info = new HashMap<String, Integer>(Constants.letterPoints);
        stringRep = "..";
        multiplyBy = 1;
        tile = null;
        multiplierType = MultiplierType.NONE;
        this.rowIndex = row;
        this.colIndex = column;
    }

    public BoardSpace(BoardSpace oldSpace){
        info = new HashMap<String, Integer>(Constants.letterPoints);
        stringRep = oldSpace.toString();
        multiplyBy = oldSpace.getMultiplyBy();
        tile = oldSpace.getTile();
        multiplierType = oldSpace.getMultiplierType();
        this.rowIndex = oldSpace.getRowIndex();
        this.colIndex = oldSpace.getColIndex();
    }

    /**
     * Check's if the space has been used by a letter
     * @return true if the space is not occupied by a letter, else false
     */
    public boolean isEmpty(){
        if ( tile == null ) return true;
        return false;
    }

    public void addTile(Tile tile){
        this.tile = tile;
        stringRep = " " + tile.getLetter();
    }

    public Tile getTile(){ return tile; }

    public int getMultiplyBy() { return multiplyBy; }

    public MultiplierType getMultiplierType(){ return multiplierType; }

    public int getRowIndex() { return rowIndex; }

    public int getColIndex() { return colIndex; }

    @Override
    public String toString(){
        return stringRep;
    }
}
