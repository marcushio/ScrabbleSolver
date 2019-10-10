import java.util.ArrayList;

/**
 *
 */
public class AI{
    private Trie trie;
    Player bot;
    int maxScore ;
    ArrayList<Tile> bestWord;
    ArrayList<Tile> tray;
    Anchor currentAnchor;
    private BoardSpace[][] boardSpaces;

    public AI(Player bot) {
        this.bot = bot;
    }

    boolean makeFirstMove(){
        bestWord = new ArrayList<>();
        getStartingWord( tray , bestWord, "", 0); //maxScore get's updated in here.
        if (maxScore == 0){ return false; }
        Move move = new Move(bestWord , 7 , 7 - (bestWord.size() / 2) , true , maxScore , bot);
        //return the new board
        refillTray();
        return true;
    }

    public void refillTray(){
        while (tray.size() < Constants.TRAY_SIZE){
            Tile newTile = TilePool.getInstance().takeOutTile();
            if (newTile == null){ return; }
            tray.add(newTile);
        }
    }

    boolean makeSubsequentMove(){
        maxScore = 0;
        bestWord = new ArrayList<Tile>();
        for (Anchor anchor : findAnchors()){
            ArrayList<Tile> inputTiles = new ArrayList<Tile>(tray);
            inputTiles.add(anchor.anchorTile);
            findHighestScoringWord(inputTiles, new ArrayList<Tile>(), "", 0, anchor);
        }
        if (bestWord == null || bestWord.size() == 0){
            //should i have some kind of get new rack of tiles thing?
            return false;
        } else {

            int startCol;
            int startRow;
            if (currentAnchor.across){
                startCol = currentAnchor.col - getAnchorPosition(currentAnchor, bestWord);
                startRow = currentAnchor.row;
            } else {
                startCol = currentAnchor.col;
                startRow = currentAnchor.row - getAnchorPosition(currentAnchor, bestWord);
            }

            Move move = new Move(bestWord , startRow , startCol ,currentAnchor.across, maxScore , bot);
            //move.execute(Board?);
        }
        return true;
    }

    private int getAnchorPosition(Anchor anchor, ArrayList<Tile> word){
        for (int c = 0 ; c < word.size() ; c++){
            if (word.get(c).letter == anchor.anchorTile.letter){
                return c;
            }
        }
        return -1000;
    }

    private boolean fitsOnBoard(Anchor anchor, ArrayList<Tile> word){
        //check if word would cause spilling off the edge of the board
        int anchorPos = getAnchorPosition(anchor, word);
        int prefixLength = anchorPos;
        int posfixLength = word.size() - anchorPos - 1 ;

        if (anchor.prefixCap >= prefixLength && anchor.postfixCap >= posfixLength){
            return true;
        } else {
            return false;
        }
    }

    private void  findHighestScoringWord(ArrayList<Tile> inputTiles, ArrayList<Tile> tilesToBeUsed, String currentWord, int score, Anchor anchor){
        for (int tileNo = 0 ; tileNo < inputTiles.size() ; tileNo++){
            Tile curTile = inputTiles.get(tileNo);
            if (curTile == null) break; //ok I gotta admit this was pretty dang sloppy I have to do a better way of handling a null tile
            if (isValidPrefix(currentWord + curTile.letter)	){

                ArrayList<Tile> remainingTiles = new ArrayList<Tile>( inputTiles);
                ArrayList<Tile> tilesInWord = new ArrayList<Tile>(tilesToBeUsed);
                remainingTiles.remove(tileNo);
                tilesInWord.add(curTile);
                findHighestScoringWord(remainingTiles, tilesInWord ,currentWord  + curTile.letter, score + curTile.pointValue , anchor);

                if (currentWord.length() >= 7){
                    score += 50;
                }

                //need to check if anchor is in the word before we propose it as an answer
                if (tilesToBeUsed.contains(anchor.anchorTile) || curTile.equals(anchor.anchorTile)){
                    if (isValidWord(currentWord + curTile.letter)){
                        if (fitsOnBoard(anchor, tilesInWord)){
                            if (maxScore < score + curTile.pointValue){
                                maxScore =  score + curTile.pointValue;
                                bestWord = tilesInWord;
                                currentAnchor = anchor;
                            }
                        }
                    }
                }
            }
        }
    }

    boolean isValidPrefix(String prefix){
        if (trie.searchPrefix(prefix)){
            return true;
        } else {
                return false;
        }
    }

    boolean isValidWord(String word){
        if (trie.searchWord(word)){
                return true;
        } else {
                return false;
        }
    }

    void  getStartingWord(ArrayList<Tile> inputTiles, ArrayList<Tile> tilesToBeUsed, String currentWord, int score){
        for (int tileNo = 0 ; tileNo < inputTiles.size() ; tileNo++){
            Tile curTile = inputTiles.get(tileNo);

            if (isValidPrefix(currentWord + curTile.letter)){

                ArrayList<Tile> remainingTiles = new ArrayList<Tile>( inputTiles);
                ArrayList<Tile> tilesInWord = new ArrayList<Tile>(tilesToBeUsed);
                remainingTiles.remove(tileNo);
                tilesInWord.add(curTile);
                getStartingWord(remainingTiles, tilesInWord ,currentWord  + curTile.letter, score + curTile.pointValue );

                if (currentWord.length() >= 6){
                    score += 50;
                }

                if (isValidWord(currentWord + curTile.letter)){
                    if (maxScore < score + curTile.pointValue){
                        maxScore =  score + curTile.pointValue;
                        bestWord = tilesInWord;
                    }
                }
            }
        }
    }

    ArrayList<Anchor> findAnchors(){
        ArrayList<Anchor> anchors = new ArrayList<Anchor>();
        Tile[][] tileArr =  Board.getInstance().getBoard();
        for (int row = 0 ; row < tileArr.length ; row ++){
            for (int col = 0 ; col < tileArr[0].length ; col ++){
                if (!tileArr[row][col].letter.equals(" ")){

                    int startCol = col;
                    int endCol = col;

                    //check how far left the word can go without collisions
                    if (col > 0 && tileArr[row][col - 1].isBlank){
                        while (startCol > 0){
                            if (row != Constants.BOARD_DIMENSIONS - 1 && ! tileArr[row + 1][startCol - 1].letter.equals(" ")){
                                break;
                            }
                            if ( row != 0 && ! tileArr[row - 1][startCol - 1].letter.equals(" ")){
                                break;
                            }
                            if (startCol == 1){
                                startCol--;
                                break;
                            }
                            if (!tileArr[row    ][startCol -2].letter.equals(" ")){
                                break;
                            }
                            startCol--;
                        }
                    }

                    //check how far right the word can go without collisions
                    if (col < Constants.BOARD_DIMENSIONS - 1 && !tileArr[row][col + 1].letter.equals(" ")){
                        while (endCol < Constants.BOARD_DIMENSIONS -1 ){

                            if (row != Constants.BOARD_DIMENSIONS - 1 && !tileArr[row + 1][endCol + 1].letter.equals(" ")){
                                break;
                            }
                            if ( row != 0 && !tileArr[row - 1][endCol + 1].letter.equals(" ")){
                                break;
                            }
                            if (endCol == Constants.BOARD_DIMENSIONS - 2){
                                endCol++;
                                break;
                            }

                            if (!tileArr[row    ][endCol + 2].letter.equals(" ")){
                                break;
                            }

                            endCol++;
                        }
                    }

                    //add the horizontal anchors
                    if (col - startCol > 0 && endCol - col > 0){ // words that can go left or right
                        anchors.add(new Anchor(row, col, tileArr[row][col], col - startCol, endCol - col , true));
                    } else {
                        //if only one then we need to do additional checks
                        if (col - startCol > 0){
                            if (col < Constants.BOARD_DIMENSIONS - 1 && !tileArr[row][col + 1].letter.equals(" ")){  // words that can only go left
                                anchors.add(new Anchor(row, col, tileArr[row][col], col - startCol, endCol - col , true));
                            }
                        }
                        if (endCol - col > 0){
                            if (col > 0 && !tileArr[row][col - 1 ].letter.equals(" ")){ // words that can only go right
                                anchors.add(new Anchor(row, col, tileArr[row][col], col - startCol, endCol - col , true));
                            }
                        }
                    }

                    //check not at edges - have to re-think algorithm for edges.
                    int startRow = row;
                    int endRow = row;

                    //check how high the word can go without collisions
                    if (row > 0 && tileArr[row - 1][col].letter.equals(" ")){
                        while (startRow > 0){
                            if (col < Constants.BOARD_DIMENSIONS - 1 && !tileArr[startRow - 1][col + 1].letter.equals(" ")){
                                break;
                            }
                            if (col > 0 && !tileArr[startRow - 1][col - 1].letter.equals(" ")){
                                break;
                            }
                            if (startRow == 1){
                                startRow--;
                                break;
                            }
                            if (!tileArr[startRow - 2][col    ].letter.equals(" ")){
                                break;
                            }
                            startRow--;
                        }

                    }

                    //check how low the word can go without collisions
                    if (row < Constants.BOARD_DIMENSIONS - 1 && tileArr[row + 1][col].letter.equals(" ")){
                        while (endRow < Constants.BOARD_DIMENSIONS -1){
                            if (col < Constants.BOARD_DIMENSIONS - 1 && !tileArr[endRow + 1][col + 1].letter.equals(" ")){
                                break;
                            }
                            if (col > 0 &&	!tileArr[endRow + 1][col - 1].letter.equals(" ")){
                                break;
                            }
                            if (endRow == Constants.BOARD_DIMENSIONS - 2){
                                endRow++;
                                break;
                            }
                            if(!tileArr[endRow + 2][col].letter.equals(" ")){
                                break;
                            }

                            endRow++;
                        }
                    }


                    if (row - startRow > 0 && endRow - row > 0){
                        anchors.add(new Anchor(row, col, tileArr[row][col], row - startRow, endRow - row, false));
                    } else{//if only one then we need to do additional checks
                        if (row - startRow > 0){ //words that can only go up
                            if (row < Constants.BOARD_DIMENSIONS-1 && tileArr[row+1][col].letter.equals(" ")){
                                anchors.add(new Anchor(row, col, tileArr[row][col], row - startRow, endRow - row, false));
                            }
                        }
                        if (endRow - row > 0){ //words that can only go down
                            if (row > 0 && tileArr[row-1][col].letter.equals(" ")){
                                anchors.add(new Anchor(row, col, tileArr[row][col], row - startRow, endRow - row, false));
                            }
                        }
                    }


                }
            }
        }
        return anchors;
    }

}
