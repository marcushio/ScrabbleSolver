import javafx.application.Application;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

/**
 * @author: Marcus Trujillo
 * @version 9/20/2019
 * This class sets up all the components and launches the program.
 *
 */
public class Main extends Application {
    Stage primaryStage;
    Constants info;
    GUI gui;
    Controller controller;
    Board board;

    /**
     * Launches the program
     * @param args
     */
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        info = new Constants(); 
        board = readBoard(); //read board config and make new board for test
        controller = new Controller(fillDictionary("sowpods.txt"));
        gui = new GUI(primaryStage, board, controller);
    }

    private Board readBoard(){
        BoardSpace[][] board = null;
        Board newBoard = null;
        try(Scanner scanner = new Scanner(System.in)){
            String token = null;
            System.out.println("Enter your board configuration");
            int boardSize = Integer.parseInt(scanner.nextLine());
            board = new BoardSpace[boardSize][boardSize];

            for(int i = 0; i < boardSize; i++){
                for(int j = 0; j < boardSize; j++){
                    token = scanner.next();
                    board[i][j] = new BoardSpace(token);
                }
            }
            newBoard = new Board(boardSize, board);
            String tray = scanner.next();
            //printBoard(boardSize);
            //System.out.println("Tray: " + tray);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return newBoard;
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
