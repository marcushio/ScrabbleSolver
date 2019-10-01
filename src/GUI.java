import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

/**
 * @author: Marcus Trujillo
 * @version: brief class description
 */
public class GUI implements Observer {
    private TilePane playArea;
    private Canvas computerTray;
    private HBox playerTray;
    private VBox root;

    public GUI(Stage primaryStage, Board model, Controller controller ){
        root = new VBox();

        computerTray = new Canvas(200, 40);
        GraphicsContext gc = computerTray.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0,0,200,40);

        //playArea = new TilePane();
        playArea = paintNewGrid(model.getSize(), model, controller);
        playerTray = new HBox();
        root.getChildren().addAll(computerTray, playArea, playerTray);
        primaryStage.setTitle("SCRA-SCRA-SCRABBLE");
        primaryStage.setScene(new Scene(root, 600, 550));
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
                DisplayTile tempCanvas = new DisplayTile(board.getSpaceAt(i,j));
                tempCanvas.addEventHandler(MouseEvent.MOUSE_CLICKED, controller.new TileHandler() );
                grid.getChildren().add(tempCanvas);
            }
        }
        return grid;
    }

    /**
     * updates the UI based on the new information of the model
     */
    @Override
    public void update(Observable o, Object arg) {

    }
}
