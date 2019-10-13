import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * @author: Marcus Trujillo
 * @version: brief class description
 */
public class GUI implements Observer {
    private Controller controller;
    private HBox playArea;
    private VBox scoreboard;
    private TilePane boardArea;
    private HBox playerTray;
    private VBox root;
    private Text humanScore;
    private Text botScore;

    public GUI(Stage primaryStage, Model model, Controller controller ){
        root = new VBox();

        playArea = new HBox();
            boardArea= paintNewGrid(Constants.BOARD_DIMENSIONS, model.getBoard(), controller);
            scoreboard = new VBox();
                humanScore = new Text("Your Score: 0" );
                botScore = new Text("Bot Score: 0");
                scoreboard.getChildren().addAll(humanScore, botScore);
        playArea.getChildren().addAll(boardArea, scoreboard);
        playerTray = fillPlayerTray(model);
        root.getChildren().addAll(playArea, playerTray);
        primaryStage.setTitle("SCRA-SCRA-SCRABBLE");
        primaryStage.setScene(new Scene(root, 900, 830));
        primaryStage.show();
    }

    /**
     *
     */
    public TilePane paintNewGrid(int size, Board board, Controller controller){
        TilePane grid = new TilePane();
        grid.setPrefColumns(size);
        grid.setPrefRows(size);
        grid.setHgap(2);
        grid.setVgap(2);

        for(int i = 0; i < size; i++){
            for(int j= 0; j < size; j++) {
                DisplaySquare tempCanvas = new DisplaySquare(board.getSpaceAt(i,j), controller);

                tempCanvas.addEventHandler(MouseEvent.MOUSE_CLICKED, controller.new TileHandler() );
                grid.getChildren().add(tempCanvas);
            }
        }
        return grid;
    }

    /**
     * Fills the player's display tray based on player's tray in the model
     */
    private HBox fillPlayerTray(Model model){
        HBox displayTray = new HBox();
        for (Tile tile : model.getHumanTray() ) {
            displayTray.getChildren().add(new DisplayTile(tile, controller));
        }
        if(model.getHumanTray().isEmpty()){
            //add an empty rect if this is empty so it doens't screw up the disp
            Canvas emptyTray = new Canvas();
            GraphicsContext gc = emptyTray.getGraphicsContext2D();
            gc.setStroke(Color.BLACK); gc.strokeRect(0,0,350, 50);
            displayTray.getChildren().add(emptyTray);
        }
        return displayTray;
    }

    /**
     * updates the UI based on the new information of the model
     */
    @Override
    public void update(Observable o, Object arg) {

    }

    public static void main(String[] args){
        BoardSpace boardSpaces[][] = null;
        int boardSize = 0;
        //read a board config
        try(Scanner scanner = new Scanner(System.in)){
            String token = null;
            System.out.println("Enter your board configuration");
            boardSize  = Integer.parseInt(scanner.nextLine());
            boardSpaces = new BoardSpace[boardSize][boardSize];

            for(int i = 0; i < boardSize; i++){
                for(int j = 0; j < boardSize; j++){
                    token = scanner.next();
                    boardSpaces[i][j] = new BoardSpace(token);
                }
            }

            String tray = scanner.next();
            System.out.println("Tray: " + tray);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        //make a new board based on config
        Board  board = new Board(boardSize, boardSpaces );
        //paint gui based on this

    }
}
