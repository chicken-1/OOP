import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class AddWordController extends Application {
    private List<TextField> engTextCopy = new ArrayList<TextField>();
    private TextField eng1Copy = new TextField();
    private TextField eng2Copy = new TextField();
    private TextField eng3Copy = new TextField();
    private TextField eng4Copy = new TextField();
    private TextField eng5Copy = new TextField();

    @Override
    public void start(Stage primaryStage) {
        engTextCopy.add(eng1Copy);
        engTextCopy.add(eng2Copy);
        engTextCopy.add(eng3Copy);
        engTextCopy.add(eng4Copy);
        engTextCopy.add(eng5Copy);
        for (int i = 0; i < engTextCopy.size(); i++) {
            engTextCopy.get(i).setStyle("-fx-background-color: #B5E1E8;\n" +
                    "    -fx-text-fill: #000000;\n" +
                    "    -fx-font-size: 14px;\n" +
                    "    -fx-font-family: \"SVN-Gilroy SemiBold\";\n" +
                    "    -fx-background-radius: 10 10 10 10; /* Bo tròn góc nền */\n" +
                    "    -fx-border-radius: 10 10 10 10; /* Bo tròn góc viền */");
            engTextCopy.get(i).setText("HI");
            engTextCopy.get(i).setLayoutX(50);
            engTextCopy.get(i).setLayoutY(75*i + 115);
            engTextCopy.get(i).setEditable(false);
            engTextCopy.get(i).setVisible(true);
        }
        StackPane root = new StackPane();
        root.getChildren().add(eng1Copy);
        root.getChildren().add(eng2Copy);
        root.getChildren().add(eng3Copy);
        root.getChildren().add(eng4Copy);
        root.getChildren().add(eng5Copy);

        Scene scene = new Scene(root, 800, 800);

        primaryStage.setTitle("TextField Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}