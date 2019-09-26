import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

/**
 * @author: Marcus Trujillo
 * @version: 9/20/2019
 * brief class description
 */
public class Solver {
    String filename;
    String[][] board;
    List<Character> tray = new ArrayList<Character>();

    public void solve(){

    }

    private HashSet<String> fillDictionary(String filename) {
        HashSet<String> dictionary = new HashSet<String>();
        String dataLine = null;
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filename))) {
            while ((dataLine = fileReader.readLine()) != null) {
                dictionary.add(dataLine);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return dictionary;
    }

    private void getBoardConfig(){
        try(Scanner scanner = new Scanner(System.in)){
            System.out.println("Enter your board configuration");
            int boardSize = Integer.parseInt(scanner.nextLine());
            board = new String[boardSize][boardSize];

            for(int i = 0; i < boardSize; i++){
                for(int j = 0; j < boardSize; j++){
                    board[i][j] = scanner.next();
                }
            }

            String tray = scanner.next();
            System.out.println("Tray: " + tray);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args){
        Solver solver = new Solver();
        solver.fillDictionary(args[0]);
        solver.getBoardConfig();
    }
}
