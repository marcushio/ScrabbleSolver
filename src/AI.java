import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Marcus Trujillo
 * @version: 10/15/19
 *
 * This is basically the computer player and all it's logic. We integrate this into the solver in order to complete
 * the sub-project.
 */
public class AI{
    private Trie trie;
    private TilePool tilePool;
    private Player bot;
    private int maxScore ;
    private ArrayList<Tile> bestWord;
    private ArrayList<Tile> tray;
    private Anchor currentAnchor;
    public BoardSpace[][] boardSpaces;

    public AI(Player bot) {
        this.bot = bot;
    }

    /**
     * this is a minimal constructor used when we only need to establish a Trie
     * @param trie
     */
    public AI(Trie trie) {
        this.bot = new Player();
        this.trie = trie;
    }

    /**
     * our robust constructor when we need full funcionality.
     * @param trie
     * @param board
     * @param sock
     * @param bot
     */
    public AI(Trie trie, BoardSpace[][] board, TilePool sock, Player bot){
        tray = new ArrayList<Tile>();
        this.bot = bot;
        refillTray(sock);
        this.trie = trie;
        this.boardSpaces = board;
        this.tilePool = sock;
        this.bot = bot;
    }

    /**
     * The procedure for making the first move is a little different, so it gets its own method, but is very similar to making
     * any subsequent move.
     * @return move
     */
    public Move makeFirstMove(){
        bestWord = new ArrayList<>();
        getStartingWord( tray , bestWord, "", 0); //maxScore get's updated in here.
        if (maxScore == 0){ return null; }
        //right now the AI is dumb and always starts on the first tile.... this is only safe with boardSize/2 >= 7
        Move move =
                new Move(bestWord , Constants.BOARD_DIMENSIONS/2 ,
                        Constants.BOARD_DIMENSIONS/2 - (bestWord.size() / 2) , true , maxScore , bot);
        //refillTray();
        return move;
    }

    /**
     * After ai makes it's move it needs to refill it's tray for the next turn.
     * @param sock
     */
    public void refillTray(TilePool sock){
        List<Tile> playertray = bot.getTray();
        while (playertray.size() < Constants.TRAY_SIZE){
           playertray.add(sock.takeOutTile());
        }
        this.tray = (ArrayList<Tile>)playertray;
    }

    /**
     * This is where the AI finds the best move to play. The readme has more details on the algorithm.
     * @return best move found
     */
    public Move makeSubsequentMove(){
        Move move = null;
        maxScore = 0;
        bestWord = new ArrayList<Tile>();
        for (Anchor anchor : findAnchors()){
            ArrayList<Tile> inputTiles = new ArrayList<Tile>(tray);
            inputTiles.add(anchor.anchorTile);
            findHighestScoringWord(inputTiles, new ArrayList<Tile>(), "", 0, anchor);
        }
        if (bestWord == null || bestWord.size() == 0){
            //should i have some kind of get new tray of tiles thing?
            return move;
        } else {
            int startCol;
            int startRow;
            if (currentAnchor.isAcross()){
                startCol = currentAnchor.col - getAnchorPosition(currentAnchor, bestWord);
                startRow = currentAnchor.row;
            } else {
                startCol = currentAnchor.col;
                startRow = currentAnchor.row - getAnchorPosition(currentAnchor, bestWord);
            }

            move = new Move(bestWord , startRow , startCol ,currentAnchor.isAcross(), maxScore , bot);
            //move.execute(Board?);
        }
        return move;
    }

    /**
     * Get's the position where the anchor sits in a word. if we're playing "bat" using 'a' as an anchor it'll be 1.
     * @param anchor
     * @param word
     * @return the position
     */
    private int getAnchorPosition(Anchor anchor, ArrayList<Tile> word){
        for (int c = 0 ; c < word.size() ; c++){
            if (word.get(c).letter == anchor.anchorTile.letter){
                return c;
            }
        }
        return -1000; //return something ridiculous if not there
    }

    /**
     * a check to be sure our proposed solution even fits on the board
     * @param anchor
     * @param word
     * @return
     */
    private boolean fitsOnBoard(Anchor anchor, ArrayList<Tile> word){
        //check if word would cause spilling off the edge of the board
        int anchorPos = getAnchorPosition(anchor, word);
        int prefixLength = anchorPos;
        int postfixLength = word.size() - anchorPos - 1 ;

        if (anchor.prefixCap >= prefixLength && anchor.postfixCap >= postfixLength){
            return true;
        } else {
            return false;
        }
    }

    /**
     * This is the biggest part. This is where we find the word we're going to use by basically traversing our Trie.
     * @param inputTiles
     * @param tilesToBeUsed
     * @param currentWord
     * @param score
     * @param anchor
     */
    private void  findHighestScoringWord(ArrayList<Tile> inputTiles, ArrayList<Tile> tilesToBeUsed, String currentWord, int score, Anchor anchor){
        for (int tileNo = 0 ; tileNo < inputTiles.size() ; tileNo++){
            Tile currentTile = inputTiles.get(tileNo);
            if (currentTile == null) break;
            if (isValidPrefix(currentWord + currentTile.letter)	){
                ArrayList<Tile> remainingTiles = new ArrayList<Tile>( inputTiles);
                ArrayList<Tile> tilesInWord = new ArrayList<Tile>(tilesToBeUsed);
                remainingTiles.remove(tileNo);
                tilesInWord.add(currentTile);
                //score check here when we know it's valid?
                findHighestScoringWord(remainingTiles, tilesInWord ,currentWord  + currentTile.letter, score + currentTile.pointValue , anchor);

                //need to check if anchor is in the word before we propose it as an answer
                if (tilesToBeUsed.contains(anchor.anchorTile) || currentTile.equals(anchor.anchorTile)){
                    if (isValidWord(currentWord + currentTile.letter)){
                        if (fitsOnBoard(anchor, tilesInWord)){
                            Move move = null; //let's make our move to score
                            if(anchor.isAcross()){ //make a move to check score bases on anchor being across type
                                move = new Move(tilesInWord,  anchor.row , (anchor.col - getAnchorPosition(anchor, tilesInWord)), true, bot );
                            } else if(!anchor.isAcross()){ // make a move to check score on anchor being vert type
                                move = new Move(tilesInWord, (anchor.row - getAnchorPosition(anchor,tilesInWord)), anchor.col,false, bot );
                            }
                            int currentWordScore = Scorer.score(move , boardSpaces);
                            if (currentWord.length() > 6){
                                currentWordScore += 50;
                            }

                            if (maxScore < currentWordScore && moveIsValid(move) ){
                                maxScore =  currentWordScore;
                                bestWord = tilesInWord;
                                currentAnchor = anchor;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * A check to be sure our proposed move actually works and doesn't overlap with other words.
     * @param move
     * @return
     */
    private boolean moveIsValid(Move move){
        BoardSpace[][] boardCopy = new BoardSpace[Constants.BOARD_DIMENSIONS][Constants.BOARD_DIMENSIONS];
        for(int i = 0; i < Constants.BOARD_DIMENSIONS; i++){
            for(int j = 0; j < Constants.BOARD_DIMENSIONS; j++){
                boardCopy[i][j] = new BoardSpace(boardSpaces[i][j]);
            }
        }
        int row = move.getStartRow();
        int col = move.getStartCol();
        String ourWord = "";
        String currentWord = "";
        for (Tile tile: move.getTiles()){
            ourWord += tile.getLetter();
            if (move.isAcross()){
                col++;
            } else {
                row++;
            }
        }
        currentWord = ourWord;
        if(move.isAcross()){
         //check if we keep going right we invalidate the word
            while(col+1 < boardCopy.length && !boardCopy[row][++col].isEmpty() ){
               if( isValidWord(currentWord += boardCopy[row][col].getTile().getLetter()) == false ) return false;
            }
            //check if we keep going left we invalidate the word
            col = move.getStartCol();
            currentWord = ourWord;
            while(col-1 >= 0 && !boardCopy[row][--col].isEmpty() ){
               if(!isValidWord( currentWord = boardCopy[row][col].getTile().getLetter() + currentWord ) ) return false;
            }
        } else if(!move.isAcross()){
            row = move.getStartRow(); col = move.getStartCol();
            currentWord = ourWord;
            //check if we keep going down we invalidate the word;
            while(row+1 < boardCopy.length && !boardCopy[++row][col].isEmpty()){
                if( !isValidWord(currentWord += boardCopy[row][col].getTile().getLetter() )) return false;
            }
            row = move.getStartRow();
            currentWord = ourWord;
            while(row-1 >= 0 && !boardCopy[--row][col].isEmpty()){
                if( !isValidWord( currentWord = boardCopy[row][col].getTile().getLetter() + currentWord  )) return false;
            }
        }
        return true;
    }

    /**
     * A utility function to check on the piece of the word we've made so far is actually part of a bigger word
     * @param prefix
     * @return
     */
    private boolean isValidPrefix(String prefix){
        if (trie.searchPrefix(prefix)){
            return true;
        } else {
            return false;
        }
    }

    /**
     * utility function to see if a word is valid.
     * @param word
     * @return
     */
    private boolean isValidWord(String word){
        if (trie.searchWord(word)){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Again finding the starting word is different so it get's it's own method.
     * @param inputTiles
     * @param tilesToBeUsed
     * @param currentWord
     * @param score
     */
    public void  getStartingWord(ArrayList<Tile> inputTiles, ArrayList<Tile> tilesToBeUsed, String currentWord, int score){
        for (int tileNo = 0 ; tileNo < inputTiles.size() ; tileNo++){
            Tile curTile = inputTiles.get(tileNo);

            if (isValidPrefix(currentWord + curTile.letter)){
                ArrayList<Tile> remainingTiles = new ArrayList<Tile>( inputTiles);
                ArrayList<Tile> tilesInWord = new ArrayList<Tile>(tilesToBeUsed);
                remainingTiles.remove(tileNo);
                tilesInWord.add(curTile);
                getStartingWord(remainingTiles, tilesInWord ,currentWord  + curTile.letter, score + curTile.pointValue );

                if (isValidWord(currentWord + curTile.letter)){
                    Move move = move = new Move(tilesInWord,  Constants.BOARD_DIMENSIONS/2, Constants.BOARD_DIMENSIONS/2, true, bot );
                    int currentWordScore = Scorer.score(move , boardSpaces);
                    if (currentWord.length() >= 6){
                        currentWordScore += 50;
                    }

                    if (maxScore < score + curTile.pointValue){
                        maxScore =  score + curTile.pointValue;
                        bestWord = tilesInWord;
                    }
                }
            }
        }
    }

    /**
     * the first step of making a move. We search through the board finding anchors to play off.
     * @return
     */
    public ArrayList<Anchor> findAnchors(){
        ArrayList<Anchor> anchors = new ArrayList<Anchor>();
        //BoardSpace[][] boardSpaces =  Board.getInstance().getBoardSpaces(); this is now set from the outside because my solver is using the ai
        for (int row = 0 ; row < boardSpaces.length ; row ++){
            for (int col = 0 ; col < boardSpaces[0].length ; col ++){
                Tile exp =  boardSpaces[row][col].getTile();
                if (exp != null){ //if no tile here

                    int startCol = col;
                    int endCol = col;

                    //check how far left the word can go without collisions
                    if (col > 0 && boardSpaces[row][col - 1].getTile() == (null)){
                        while (startCol > 0){
                            if (row != Constants.BOARD_DIMENSIONS - 1 &&  boardSpaces[row + 1][startCol - 1].getTile() != null){
                                break;
                            }
                            if ( row != 0 &&  boardSpaces[row - 1][startCol - 1].getTile() != null){
                                break;
                            }
                            if (startCol == 1){
                                startCol--;
                                break;
                            }
                            if (boardSpaces[row    ][startCol -2].getTile() != null){
                                break;
                            }
                            startCol--;
                        }
                    }

                    //check how far right the word can go without collisions
                    if (col < Constants.BOARD_DIMENSIONS - 1 && boardSpaces[row][col + 1].getTile() == null){
                        while (endCol < Constants.BOARD_DIMENSIONS -1 ){

                            if (row != Constants.BOARD_DIMENSIONS - 1 && boardSpaces[row + 1][endCol + 1].getTile() != null){
                                break;
                            }
                            if ( row != 0 && boardSpaces[row - 1][endCol + 1].getTile() != null ){
                                break;
                            }
                            if (endCol == Constants.BOARD_DIMENSIONS - 2){
                                endCol++;
                                break;
                            }
                            if (boardSpaces[row    ][endCol + 2].getTile() != (null)){
                                break;
                            }
                            endCol++;
                        }
                    }

                    //add the horizontal anchors
                    if (col - startCol > 0 && endCol - col > 0){ // words that can go left or right
                        anchors.add(new Anchor(row, col, boardSpaces[row][col].getTile(), col - startCol, endCol - col , true));
                    } else {
                        //if only one then we need to do additional checks
                        if (col - startCol > 0){
                            if (col < Constants.BOARD_DIMENSIONS - 1 && boardSpaces[row][col + 1].getTile() != (null)){  // words that can only go left I need to readd this later.
                                anchors.add(new Anchor(row, col, boardSpaces[row][col].getTile(), col - startCol, endCol - col , true));
                            }
                        }
                        if (endCol - col > 0){
                            if (col > 0 && boardSpaces[row][col - 1 ].getTile() != (null)){ // words that can only go right
                               anchors.add(new Anchor(row, col, boardSpaces[row][col].getTile(), col - startCol, endCol - col , true));
                            }
                        }
                    }

                    int startRow = row;
                    int endRow = row;

                    //check how high the word can go without collisions
                    if (row > 0 && boardSpaces[row - 1][col].getTile() == null){
                        while (startRow > 0){
                            if (col < Constants.BOARD_DIMENSIONS - 1 && boardSpaces[startRow - 1][col + 1].getTile() != (null)){
                                break;
                            }
                            if (col > 0 && boardSpaces[startRow - 1][col - 1].getTile() != (null)){
                                break;
                            }
                            if (startRow == 1){
                                startRow--;
                                break;
                            }
                            if (boardSpaces[startRow - 2][col    ].getTile() != (null)){
                                break;
                            }
                            startRow--;
                        }

                    }

                    //check how low the word can go without collisions
                    if (row < Constants.BOARD_DIMENSIONS - 1 && boardSpaces[row + 1][col].getTile() == (null)){
                        while (endRow < Constants.BOARD_DIMENSIONS -1){
                            if (col < Constants.BOARD_DIMENSIONS - 1 && boardSpaces[endRow + 1][col + 1].getTile() !=(null)){
                                break;
                            }
                            if (col > 0 &&	boardSpaces[endRow + 1][col - 1].getTile() != (null)){
                                break;
                            }
                            if (endRow == Constants.BOARD_DIMENSIONS - 2){
                                endRow++;
                                break;
                            }
                            if(boardSpaces[endRow + 2][col].getTile() != (null)){
                                break;
                            }

                            endRow++;
                        }
                    }


                    if (row - startRow > 0 && endRow - row > 0){
                        anchors.add(new Anchor(row, col, boardSpaces[row][col].getTile(), row - startRow, endRow - row, false));
                    } else{//if only one then we need to do additional checks
                        if (row - startRow > 0){ //words that can only go up I'm not using these yet because It's been causing bugs but I should fix this
                            if (row < Constants.BOARD_DIMENSIONS-1 && boardSpaces[row+1][col].getTile() == (null)){
                                anchors.add(new Anchor(row, col, boardSpaces[row][col].getTile(), row - startRow, endRow - row, false));
                            }
                        }
                        if (endRow - row > 0){ //words that can only go down
                            if (row > 0 && boardSpaces[row-1][col].getTile() == (null)){
                                anchors.add(new Anchor(row, col, boardSpaces[row][col].getTile(), row - startRow, endRow - row, false));
                            }
                        }
                    }
                }
            }
        }
        return anchors;
    }

    /**
     * Set to a different Trie if you need to change
     * @param trie
     */
    public void setTrie(Trie trie){
        this.trie = trie;
    }

    /**
     * set the board to whatever you need. Very necessary as a utility method for the solver because we check multiple
     * boards.
     * @param newBoard
     */
    public void setBoard(BoardSpace[][] newBoard){
        boardSpaces = newBoard;
        maxScore = 0;
    }

    /**
     * also a utility for the solver since we solve with multiple trays.
     * @param newTray
     */
    public void setTray(ArrayList<Tile> newTray){ this.tray = newTray; }

    /**
     * @return the tray of the AI.
     */
    public ArrayList<Tile> getTray(){ return tray; }

    /**
     * @return The score of the AI
     */
    public int getScore(){ return bot.getScore(); }

    /**
     * Update the score of the ai
     * @param score
     */
    public void updateScore(int score){ bot.updateScore(score); }
}
