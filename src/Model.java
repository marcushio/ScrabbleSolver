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
    private Player player, bot;
    public TilePool tilePool;

    public Model(){
        board = new Board(); //make a standard size board
        player = new Player();
        bot = new Player();
        tilePool = new TilePool();
    }

    public List<Tile> getHumanTray(){ return player.getTray(); }

    public Board getBoard(){ return board; }


    /** pretty sure this is not model's job, that belongs to constants, prep this for deletion
     * fills the collection of tiles that can be drawn by reading from a file that contains all inforamation about
     * the number of each tiles, and the point specifications of each.

     private void fillTilePool(){
     String fileLine = "";
     try (BufferedReader fileReader = new BufferedReader(new FileReader("res"+File.separator+Constants.TILE_CONFIG_FILE))) {
     while ((fileLine = fileReader.readLine()) != null) {
     //parse line into letter, points, and frequency
     String[] arr = fileLine.split(" ");
     String letter = arr[0];
     int pointValue = Integer.parseInt(arr[1]);
     int frequency = Integer.parseInt(arr[2]);
     for(int i= 1; i <= frequency; i++) {
     tilePool.add( new Tile(letter, pointValue) );
     }
     }
     } catch (IOException ex) {
     ex.printStackTrace();
     }
     }
     */
}
