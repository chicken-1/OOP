import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class GameController extends GeneralController {

    @FXML
    private Button multiChoiceGame;

    @FXML
    private Button matchingGame;

    @FXML
    private Button listenGame;

    @FXML
    private Button exitBtn;

    @FXML
    public void handleClickMultiGame(ActionEvent actionEvent) throws IOException {
        // load the game pane
        Parent multiGamePane = FXMLLoader.load(getClass().getResource("Game1.fxml"));

        // get the root layout (pane stack)
        StackPane root = (StackPane) multiChoiceGame.getScene().getRoot();

        // remove the search pane from the root layout and add the game pane
        root.getChildren().remove(0);
        root.getChildren().add(multiGamePane);
    }

    public void handleClickMatchingGame(ActionEvent actionEvent) throws IOException {
        // load the game pane
        Parent matchingGamePane = FXMLLoader.load(getClass().getResource("matchGame.fxml"));

        // get the root layout (pane stack)
        StackPane root = (StackPane) matchingGame.getScene().getRoot();

        // remove the search pane from the root layout and add the game pane
        root.getChildren().remove(0);
        root.getChildren().add(matchingGamePane);
    }

    public void handleClickListenGame(ActionEvent actionEvent) throws IOException {
        Parent listenGamePane = FXMLLoader.load(getClass().getResource("listenGame.fxml"));
        StackPane root = (StackPane) listenGame.getScene().getRoot();
        root.getChildren().remove(0);
        root.getChildren().add(listenGamePane);
    }
}
