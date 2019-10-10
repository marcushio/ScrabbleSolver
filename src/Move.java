
import java.util.ArrayList;
/**
 * @author: Marcus Trujillo
 * @version: 10/1/2019
 * This encapsulates a move in this scrabble game.
 */

public class Move {
    ArrayList<Tile> tiles;
    int startRow;
    int startCol;
    boolean across;
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

    public Move(ArrayList<Tile> tiles, int startRow, int startCol, boolean across, int score, Player player, Anchor anchor) {
        this.tiles = tiles;
        this.startRow = startRow;
        this.startCol = startCol;
        this.across = across;
        this.score = score;
        this.player = player;
        this.anchor = anchor;
    }

    /*
    @Override
    public String toString() {
        StringBuilder word = new StringBuilder();
        for (Tile tile: tiles){
            word.append(tile.letter);
        }
        return player.name + " places '" + word.toString() + "' for " + score + " points \n";
    }

     */

    void execute(Tile[][] boardArray){
        int row = startRow;
        int col = startCol;

        for (Tile tile: tiles){
            boardArray[row][col] = tile;
            if (across){
                col++;
            } else {
                row++;
            }
            player.removeTrayTile(tile);
        }
        //player.awardPoints(score);
        HumanMove.execute(player);
        player.fillTray();

    }

}
