/**
 * @author: Marcus Trujillo
 * @version: 9/26/2019
 * This represents each individual space on the board. They store if they have multipliers and if they're empty
 *
 */
enum MultiplierType{LETTER, WORD, NONE;}

public class BoardSpace {
    private String letter;
    private int multiplyBy;
    private MultiplierType multiplierType;
    private int rowIndex, colIndex;

    /**
     * We construct the space based off the String read in from standard input.
     * @param textSpace is the string that is read in from Standard input. ".." means it's empty, "3." means a triple
     *                  word, ".3" would be a triple letter. We expect good input with an integer repping the multiplier
     */
    public BoardSpace(String textSpace){
        multiplyBy = 1;
        if(textSpace.length() < 2) {
            letter = textSpace;
        } else if (textSpace.length() == 2) {
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
     * Check's if the space has been used by a letter
     * @return true if the space is not occupied by a letter, else false
     */
    public boolean isEmpty(){
        if ( letter == null ) return true;
        return false;
    }

    public String getLetter(){ return letter; }

    public int getMultiplyBy() { return multiplyBy; }

    public MultiplierType getMultiplierType(){ return multiplierType; }

    public int getRowIndex() { return rowIndex; }

    public int getColIndex() { return colIndex; }
}
