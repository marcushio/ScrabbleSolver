import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author: Marcus Trujillo
 * @version: brief class description
 */
public class Trie {
    String filename;
    private Node root;

    public Trie(String filename){
        this.filename = filename;
    }

    private class Node{
        List<Node> children = new ArrayList<Node>();
    }

    public Trie(){
        fillTrie();
    }

    /**
     * fills our tree from a scrabble dictionary (stored as a .txt) so we can lookup words.
     */
    private void fillTrie(){
        root = new Node();
            String dataLine = null;
            try (BufferedReader fileReader = new BufferedReader(new FileReader(filename))) {
                while((dataLine = fileReader.readLine()) != null){
                    dataLine.toCharArray();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
    }
}
