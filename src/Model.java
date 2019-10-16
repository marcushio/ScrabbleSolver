import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author: Marcus Trujillo
 * @version: 9/20/2019
 * The model is the representation of the internal data.
 */
public class Model  extends Observable {
    private Board board;
    private Player player;
    private AI ai;
    public TilePool tilePool;

    public Model(Trie trie){
        board = new Board(); //make a standard size board
        tilePool = new TilePool();
        player = new Player(tilePool);
        ai = new AI(trie, board.getBoard(), tilePool, new Player(tilePool));
    }

    public void selectTileInTray(Tile tile){
        List<Tile> trayCopy = player.getTray();
        for(Tile trayTile : trayCopy){
            if(trayTile == tile){
                trayTile.setSelected(true);
            }
        }
        setChanged();
        notifyObservers();
    }

    public void updateGUI(){
        setChanged();
        notifyObservers();
    }

    public boolean tilePoolIsEmpty(){
        return tilePool.isEmpty();
    }

    public void setEndPlayerTray(){
        ArrayList<Tile> endTray = makeEndTray();
        player.setTray(endTray);
        setChanged();
        notifyObservers();
    }

    public List<Tile> getHumanTray(){ return player.getTray(); }

    public Board getBoard(){ return board; }

    public int getPlayerScore(Player player){
        return player.getScore();
    }

    public int getPlayerScore(){ return player.getScore(); }

    public int getBotScore(){ return ai.getScore(); }

    public void executeMove(Move move){ // should rename to executePlayer move
        int moveScore = Scorer.score(move, board.getBoard());
        player.updateScore(moveScore);
        int row = move.getStartRow();
        int col = move.getStartCol();

        for (Tile tile: move.getTiles()){
            board.getBoard()[row][col].addTile(tile);
            if (move.isAcross()){
                col++;
            } else {
                row++;
            }
            player.removeTrayTile(tile);
        }
        player.fillTray(tilePool);
        ai.setBoard(board.getBoard());
        Move botsMove = ai.makeSubsequentMove();

        ai.updateScore(Scorer.score(botsMove, board.getBoard())); //score on the board before it gets the move enacted on it.
        botsMove.execute(board.getBoard());
        ai.refillTray(tilePool);
        setChanged();
        notifyObservers();
        //return boardArray;
    }

private ArrayList<Tile> makeEndTray(){
      ArrayList<Tile> endTray  = new ArrayList<Tile>();
      endTray.add(new Tile("g", 0));
    endTray.add(new Tile("a", 0));
    endTray.add(new Tile("m", 0));
    endTray.add(new Tile("e", 0));
    endTray.add(new Tile("*", 0));
    endTray.add(new Tile("o", 0));
    endTray.add(new Tile("v", 0));
    endTray.add(new Tile("e", 0));
    endTray.add(new Tile("r", 0));
    return endTray;
}
}
