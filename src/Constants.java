import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author: Marcus Trujillo
 * @version: brief class description
 */
public class Constants {
    public static final String TILE_CONFIG_FILE = "scrabble_tiles.txt";
    public static String dictionaryFilename;
    public static int TRAY_SIZE = 7;
    public static int BOARD_DIMENSIONS = 15;
    public static BoardSpace[][] standardBoard;
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
    //public static Map<Tile, Integer> tileFrequencies = new HashMap<Tile, Integer>() ; //tile prototypes maps a tile to it's frequency
    public static Map<String, Integer> tileFrequencies = new HashMap<String, Integer>();
    public static Map<String, Integer> letterPoints = new HashMap<String, Integer>();

    public Constants(){
        readTileInfo();
    }

    /**
     * returns the point value of a given letter
     */
    public Map<String, Integer> getLetterPoints(String letter){
        return letterPoints;
    }

    /**
     *
     */
    public static void setBoardDimensions(int newDimens){
        BOARD_DIMENSIONS = newDimens;
    }

    /**
     *
     */
    public static void setDictionaryFilename(String filename){ dictionaryFilename = filename; }

    /**
     *
     */
    public static void readStandardBoard(){
        ArrayList<Solver.BoardConfig> boardConfigs = new ArrayList<Solver.BoardConfig>();
        try(Scanner scanner = new Scanner(new File("res" + File.separator + "standardBoard.txt"))){
            while(scanner.hasNext()) {
                String token = null; // i can probably integrate this instead of explicitly stating this var
                Constants.setBoardDimensions(15);
                standardBoard = new BoardSpace[15][15];
                for (int i = 0; i < 15; i++) {
                    for (int j = 0; j < 15; j++) {
                        token = scanner.next();
                        standardBoard[i][j] = new BoardSpace(token);
                    }
                }
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * reads tile information from the file.
     */
    public static void readTileInfo(){
        String fileLine = "";
        try (BufferedReader fileReader = new BufferedReader(new FileReader("res"+ File.separator+Constants.TILE_CONFIG_FILE))) {
            while ((fileLine = fileReader.readLine()) != null) {
                //parse line into letter, points, and frequency
                String[] arr = fileLine.split(" ");
                String letter = arr[0];
                int pointValue = Integer.parseInt(arr[1]);
                int frequency = Integer.parseInt(arr[2]);
                Tile newTile = new Tile(letter, pointValue);
                tileFrequencies.put(letter, frequency);
                letterPoints.put(letter, pointValue);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
