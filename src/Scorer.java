/**
 * @author: Marcus Trujillo
 * @version:
 * Takes care of the scoring of moves that are made on the board.
 */
public class Scorer {
//   [l]
//[a][l][l]
//   [a]
//   [m]
    /**
     *
     * @param word is the word that you're trying to play
     * @param spaces is the sequence of spaces and tiles on the board that you're trying to play on.
     * @return the score of the potential move, -1 if it's not a valid move
     */
    public static int score(Tile[] word, BoardSpace[] spaces){
        int wordScore = 0;
        if(word.length != spaces.length) return -1;
        //valid check goes here
        boolean hasWordMultiplier = false;
        int multiplyBy = 1; //make multiplier 1 by default (aka the multiplier doesn't change the value
        for(int i=0; i < word.length; i++){
            MultiplierType multiplierType = spaces[i].getMultiplierType();
            if(multiplierType == MultiplierType.LETTER) {
                wordScore += (word[i].getPointValue() * spaces[i].getMultiplyBy());
            } else if(multiplierType == MultiplierType.WORD) {
                //I don't think it's going to get more than one word multiplier but just in case, take the biggest
                if(spaces[i].getMultiplyBy() > multiplyBy) multiplyBy = spaces[i].getMultiplyBy();
            }
        }
        return wordScore * multiplyBy;
    }
}
