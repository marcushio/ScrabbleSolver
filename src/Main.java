import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

/**
 * @author: Marcus Trujillo
 * @version 9/20/2019
 * This class sets up all the components and launches the program.
 *
 */
public class Main {


    public static void main(String[] args){

        //String filename = "sowpods.txt";
        //3 tasks that need to be done
        //1 read in dictionary
        //2 read in board
        //3
        Main main = new Main();
        Controller controller = new Controller(main.fillDictionary(args[0]));
    }

    /**
     * fills a hash set with valid words
     */
    private HashSet<String> fillDictionary(String filename) {
        HashSet<String> dictionary = new HashSet<String>();
        String word = null;
        try (BufferedReader fileReader = new BufferedReader(new FileReader("res" + File.separator +  filename))) {
            while ((word = fileReader.readLine()) != null) {
                dictionary.add(word);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return dictionary;
    }


}
