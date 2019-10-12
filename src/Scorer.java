/**
 * @author: Marcus Trujillo
 * @version: 10/1/2019
 * This handles the scoring logic.
 */

public class Scorer {
    /**
     *
     * @param move is the encapsulation of the move we're trying to play on the board
     * @param board is the board that we're playing on
     * @return the score of the potential move, -1 if it's not a valid move
     */
    public static int score(Move move, BoardSpace[][] board){
        int wordScore = 0;
        //valid check goes here but for now we're not protecting... we'll get there
        int multiplyBy = 1; //make multiplier 1 by default (aka the multiplier doesn't change the value
        int row = move.getStartRow();
        int col = move.getStartCol();
        int index = 0;
        if (move.isAcross()) {index = col; }
        else { index = row; }
        for(Tile tile: move.getTiles()){
            BoardSpace space = board[row][col];
            board[row][col].addTile(tile);
                MultiplierType multiplierType = space.getMultiplierType();
                if(multiplierType == MultiplierType.LETTER) {
                    wordScore += (space.getTile().getPointValue() * space.getMultiplyBy());
                } else if(multiplierType == MultiplierType.WORD) {
                    //I don't think it's going to get more than one word multiplier but just in case, take the biggest
                    if(space.getMultiplyBy() > multiplyBy) multiplyBy = space.getMultiplyBy();
                }
            if(move.isAcross()){ col++; }
            else{ row++; }
        }
        return wordScore * multiplyBy;
    }
}
