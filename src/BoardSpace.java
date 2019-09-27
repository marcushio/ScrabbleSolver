/**
 * @author: Marcus Trujillo
 * @version: 9/26/2019
 * This represents each individual space on the board. They store if they have multipliers and if they're empty
 *
 */
enum MultiplierType{LETTER, WORD;}

public class BoardSpace {
    String letter;
    int multiplyBy;
    MultiplierType multiplier;

    /**
     * We construct the space based off the String read in from standard input.
     * @param textSpace is the string that is read in from Standard input. ".." means it's empty, "3." means a triple
     *                  word, ".3" would be a triple letter. We expect good input with an integer repping the multiplier
     */
    public BoardSpace(String textSpace){
        if(textSpace.length() < 2) {
            letter = textSpace;
        } else if (textSpace.length() == 2) {
            if (textSpace.charAt(0) != '.') {
                multiplyBy = Integer.parseInt(textSpace.substring(0, 1));
                multiplier = MultiplierType.WORD;
            }
            if (textSpace.charAt(1) != '.') {
                multiplyBy = Integer.parseInt(textSpace.substring(1));
                multiplier = MultiplierType.LETTER;
            }
        }
    }

    /**
     * Check's if the space has been used by a letter
     * @return true if the space is not occupied by a letter, else false
     */
    private boolean isEmpty(){
        if ( letter == null ) return true;
        return false;
    }
}
