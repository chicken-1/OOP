import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Dictionary_application extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
       // transfer by pane
        // load the search pane
        Parent searchPane = FXMLLoader.load(getClass().getResource("search.fxml"));

        // create a stack pane as the root layout
        StackPane root = new StackPane();

        // add the search pane to the root layout
        root.getChildren().add(searchPane);

        // create a scene with the root layout
        Scene scene = new Scene(root, 875, 600);

        // set the scene and show the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dictionary Application");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
