import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @author: Marcus Trujillo
 * @version:
 * A retrieval tree that we're using to generate words for the solver to try.
 */
public class Trie {
    public Node root;
    private int wordCount;

    public Trie(){
        root = new Node(null);
    }

    /**
     * this inner class represents the nodes of the trie.
     * Each node has a hashmap of it's children
     * Each node has a letter. If a node is a root or the end of a word, then it's letter value is null
     */
     public class Node{
        public HashMap<String, Node> children = new HashMap<String, Node>(); //each "string" is a single letter of the word
        public String letter;

        public Node() {}
        public Node(String letter){
            this.letter = letter;
        }

        public boolean isCompleteWord(){
            if(children.containsValue(null)) return true;
            return false;
        }

        public boolean hasChild(String letter){
            if(children.containsKey(letter)) return true;
            return false;
        }
    }


    public void addWord(String word){
        Node currentNode = root;
        for(int i=0; i < word.length(); i++){
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
        wordCount++;
    }

    public Boolean searchWord(String word) {
        if (word == null|| word == "") return false;
        Node current = root;
        for(int i=0; i < word.length(); i++){
            String currentLetter = word.substring(i,i+1);
            HashMap<String, Node> children = current.children;
            if (children.containsKey(currentLetter)){
                current = children.get(currentLetter);
            }
        }
        if (current.children.containsKey(null)){
            return true;
        } else {
            return false;
        }
    }

    public Boolean searchPrefix(String word) {
        if (word == null) return false;
        Node cur = root;
        for(int i=0; i < word.length(); i++){
            String currentLetter = word.substring(i,i+1);
            HashMap<String, Node> child = cur.children;
            if (child.containsKey(currentLetter)){
                cur = child.get(currentLetter);
            } else{
                return false;
            }
        }
        return true;
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
