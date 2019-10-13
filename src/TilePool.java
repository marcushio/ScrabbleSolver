import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
/**
 * @author: Marcus Trujillo
 * @version: 10/13/2019
 * Represents the pool of tiles that we can pull from when our tray of tiles isn't full
 */

public class TilePool {
    ArrayList<Tile> tileSet;

    public TilePool(){
        tileSet = new ArrayList<Tile>();
        for(Map.Entry<String, Integer> entry : Constants.tileFrequencies.entrySet()){
            addMultipleTiles(entry.getKey(), Constants.letterPoints.get(entry.getKey()), entry.getValue());
        }
    }

    public TilePool(ArrayList<Tile> tileSet){
        this.tileSet = tileSet;
    }

    public Tile takeOutTile(){
        if (isEmpty()) return null;
        Random random = new Random();
        int index = random.nextInt(tileSet.size());
        Tile tile = tileSet.get(index);
        tileSet.remove(index);
        return tile;
    }

    public void addMultipleTiles(Tile tile , int quantity){
        for (int i = 0 ; i < quantity ; i++){
            tileSet.add(tile);
        }
    }

    public void addMultipleTiles(String letter, int points , int quantity){
        for (int i = 0 ; i < quantity ; i++){
            tileSet.add(new Tile(letter, points));
        }
    }

    boolean isEmpty(){
        return (tileSet.size() == 0);
    }
}
