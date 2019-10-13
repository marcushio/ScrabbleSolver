import java.util.ArrayList;
import java.util.List;

/**
 * @author: Marcus Trujillo
 * @version: 10/1/2019
 * Represents a player in the game of scrabble
 */
public class Player {
    private List<Tile> tray = new ArrayList<Tile>();
    private int score = 0;
    public Player(){};
    /**
    *
    */
    public Player(TilePool tilePool){
        fillTray(tilePool);
    }
    /**
     *
     * @param sock
     */
    public void fillTray(TilePool sock){
        while (tray.size() <= Constants.TRAY_SIZE ){
            tray.add(sock.takeOutTile());
        }
    }

    private void removeTileFromTray(Tile tile){
        tray.remove(tile);
    }

    public void updateScore(int points){
        score += points;
    }

    public void removeTrayTile(Tile tile){
        tray.remove(tile);
    }

    public List<Tile> getTray(){ return tray; }


}
