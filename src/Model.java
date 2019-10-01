import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * @author: Marcus Trujillo
 * @version: 9/20/2019
 * brief class description
 */
public class Model {
    Board board;
    Player player;
    List<Tile> tilePool;

    /**
     * fills the collection of tiles that can be drawn by reading from a file that contains all inforamation about
     * the number of each tiles, and the point specifications of each.
     */
    private void fillTilePool(){
        String fileLine = "";
        try (BufferedReader fileReader = new BufferedReader(new FileReader("res"+File.separator+Constants.TILE_CONFIG_FILE))) {
            while ((fileLine = fileReader.readLine()) != null) {
                //parse line into letter, points, and frequency
                String[] arr = fileLine.split(" ");
                String letter = arr[0];
                int pointValue = Integer.parseInt(arr[1]);
                int frequency = Integer.parseInt(arr[2]);
                for(int i= 0; i <= frequency; i++) {
                    tilePool.add( new Tile(letter, pointValue) );
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args){
        Model model = new Model(); 
    }
}
