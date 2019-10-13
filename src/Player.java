import java.util.ArrayList;
import java.util.List;

/**
 * @author: Marcus Trujillo
 * @version: 10/1/2019
 * brief class description
 */
public class Player {
    private List<Tile> tray = new ArrayList<Tile>();
    private int score = 0;

    private void removeTileFromTray(Tile tile){
        tray.remove(tile);
    }

    public void fillTray(){
        while (tray.size() <= Constants.TRAY_SIZE ){
            //TilePool sock = TilePool.getInstance();
            //tray.add(sock.takeOutTile());
        }
    }

    public void updateScore(int points){
        score += points;
    }

    public void removeTrayTile(Tile tile ){
        tray.remove(tile);
    }


}
