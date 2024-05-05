import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GeneralController {
    @FXML
    private Button gameButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button apiButton;

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
    public void handleClickAPITranslate(ActionEvent actionEvent) throws IOException {
        Parent searchPane = FXMLLoader.load(getClass().getResource("ggTranslate.fxml"));

        // get the root layout (pane stack)
        StackPane root = (StackPane) apiButton.getScene().getRoot();

        // remove the search pane from the root layout and add the game pane
        root.getChildren().remove(0);
        root.getChildren().add(searchPane);
    }
}



