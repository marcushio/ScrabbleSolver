import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
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
 * @version:
 * brief class description
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
        playerTray = new HBox();
        this.controller = controller;
        playArea = new HBox();
            boardArea = paintNewGrid(Constants.BOARD_DIMENSIONS, model.getBoard(), controller);
            scoreboard = new VBox(5);
                humanScore = new Text("Your Score: 0" );
                botScore = new Text("Bot Score: 0");
                Button moveAcrossButton = new Button("EXECUTE ACROSS");
                moveAcrossButton.setOnMouseClicked(controller.new AcrossButtonHandler());
                Button moveDownButton = new Button("EXECUTE DOWN");
                moveDownButton.setOnMouseClicked(controller.new DownButtonHandler());
                Button resetButton = new Button("RESET");
                resetButton.setOnMouseClicked(controller.new ResetButtonHandler());
        scoreboard.getChildren().addAll(humanScore, botScore, moveAcrossButton, moveDownButton, resetButton);
        playArea.getChildren().addAll(boardArea, scoreboard);
        drawPlayerTray(model);
        root.getChildren().addAll(playArea, new Text("\t\t\t\t\t\t\t\tPLAYERS TRAY"), playerTray);
        primaryStage.setTitle("SCRA-SCRA-SCRABBLE");
        primaryStage.setScene(new Scene(root, 900, 835));
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
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
                DisplaySquare tempCanvas = new DisplaySquare(board.getSpaceAt(i,j), controller, i, j);
                tempCanvas.addEventHandler(MouseEvent.MOUSE_CLICKED, controller.new SpaceHandler() );
                grid.getChildren().add(tempCanvas);
            }
        }
        return grid;
    }

    /**
     * Fills the player's display tray based on player's tray in the model
     */
    private void drawPlayerTray(Model model){
        playerTray.getChildren().clear();
        playerTray.getChildren().add(new Text("   \t\t                "));
        for (Tile tile : model.getHumanTray() ) {
            DisplayTile newDisplayTile = new  DisplayTile(tile, controller);
            playerTray.getChildren().add(newDisplayTile);
        }
        if(model.getHumanTray().isEmpty()){
            //add an empty rect if this is empty so it doens't screw up the disp
            Canvas emptyTray = new Canvas();
            GraphicsContext gc = emptyTray.getGraphicsContext2D();
            gc.setStroke(Color.BLACK); gc.strokeRect(0,0,350, 50);
            playerTray.getChildren().add(emptyTray);
        }
    }

    /**
     * updates the UI based on the new information of the model
     */
    @Override
    public void update(Observable model, Object arg) {
        Model updatedModel = (Model) model;
        drawPlayerTray(updatedModel);
        boardArea = paintNewGrid(Constants.BOARD_DIMENSIONS, updatedModel.getBoard(), controller);
        humanScore.setText("Your score: " + updatedModel.getPlayerScore()); //= new Text(updatedModel.getPlayerScore() + " ");
        botScore.setText("AI score: " + updatedModel.getBotScore()); // = new Text(updatedModel.getBotScore() + " ");
        playArea.getChildren().clear();
        playArea.getChildren().addAll(boardArea, scoreboard);
    }
}
