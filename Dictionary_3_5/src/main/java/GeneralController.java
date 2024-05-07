import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class GeneralController {
    @FXML
    private Button gameButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button translateBtn;

    /* set up navigation method. */
    @FXML
    public void handleClickGame(ActionEvent actionEvent) throws IOException {
        //**** change by pane
        // load the game pane
        Parent gamePane = FXMLLoader.load(getClass().getResource("gameGeneral.fxml"));

        // get the root layout (pane stack)
        StackPane root = (StackPane) gameButton.getScene().getRoot();

        // remove the search pane from the root layout and add the game pane
        root.getChildren().remove(0);
        root.getChildren().add(gamePane);
    }

    @FXML
    public void handleClickSearch(ActionEvent actionEvent) throws IOException {
        Parent searchPane = FXMLLoader.load(getClass().getResource("search.fxml"));

        // get the root layout (pane stack)
        StackPane root = (StackPane) searchButton.getScene().getRoot();

        // remove the search pane from the root layout and add the game pane
        root.getChildren().remove(0);
        root.getChildren().add(searchPane);
    }

    @FXML
    public void handleClickTranslate(ActionEvent actionEvent) throws IOException {
        Parent searchPane = FXMLLoader.load(getClass().getResource("ggTranslate.fxml"));

        // get the root layout (pane stack)
        StackPane root = (StackPane) translateBtn.getScene().getRoot();

        // remove the search pane from the root layout and add the game pane
        root.getChildren().remove(0);
        root.getChildren().add(searchPane);
    }
}



