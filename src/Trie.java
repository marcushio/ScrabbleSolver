import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @author: Marcus Trujillo
 * @version: brief class description
 */
public class Trie {
    private Node root;

    public Trie(){
        root = new Node(null);
    }

    /**
     * this inner class represents the nodes of the trie.
     * Each node has a hashmap of it's children
     * Each node has a letter. If a node is a root or the end of a word, then it's letter value is null
     */
    private class Node{
        public HashMap<String, Node> children = new HashMap<String, Node>(); //each "string" is a single letter of the word
        public String letter;

        public Node(String letter){
            this.letter = letter;
        }
        private boolean isCompleteWord(){
            if(children.containsValue(null)) return true;
            return false;
        }
    }

    public void addWord(String word){
        Node currentNode = root;
        for(int i=0; i < word.length(); i++){ //I'm probs going to change this loop structure
            String currentLetter = word.substring(i,i+1);
            if( !currentNode.children.containsKey( currentLetter )){ //check if this letter is a child of current node
                Node newNode = new Node(currentLetter);
                currentNode.children.put( currentLetter, newNode );
                currentNode = newNode;
            } else {
                currentNode = currentNode.children.get(currentLetter);
            }
        }
        currentNode.children.put(null, null);
    }

    public static void main(String[] args){
        String[] testWords = {"ant", "any", "all", "dog", "do"} ;
        Trie trie = new Trie();
        for(String word : testWords){
            trie.addWord(word);
        }
        System.out.println("sample tree filled");
    }
}
