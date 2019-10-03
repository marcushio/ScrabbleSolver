import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Marcus Trujillo
 * @version: brief class description
 */
public class Constants {
    public static final String TILE_CONFIG_FILE = "scrabble_tiles.txt";
    public static String dictionaryFilename;

    public static final String doubleLetter = "Double\nLetter\nScore";
    public static final String tripleLetter = "Triple\nLetter\nScore";
    public static final String doubleWord = "Triple\nLetter\nScore";
    public static final String tripleWord = "Triple\nLetter\nScore";
    public static final String tripleScore = "Triple\n";
    public static final String doubleScore = "Double\n";
    public static final String LETTER = "Letter\n";
    public static final String WORD = "Word\n";
    public static final int TILE_WIDTH = 40;
    public static final int TILE_HEIGHT = 40;
    public static final int STANDARD_BOARD_SIZE = 15;
    public Map<String, Integer> prototypes = new HashMap<String, Integer>() ;

    public Constants(){
        readTileInfo();
    }

    /**
     * returns the point value of a given letter
     */
    public int getLetterPoints(String letter){
        return prototypes.get(letter);
    }

    /**
     * reads tile information from the file.
     */
    private void readTileInfo(){
        String fileLine = "";
        try (BufferedReader fileReader = new BufferedReader(new FileReader("res"+ File.separator+Constants.TILE_CONFIG_FILE))) {
            while ((fileLine = fileReader.readLine()) != null) {
                //parse line into letter, points, and frequency
                String[] arr = fileLine.split(" ");
                String letter = arr[0];
                int pointValue = Integer.parseInt(arr[1]);
                //int frequency = Integer.parseInt(arr[2]);
                prototypes.put( letter, pointValue);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
