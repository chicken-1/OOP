import base.MatchGame;
import javafx.css.Styleable;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.util.*;

public class MatchingGameController extends GameController {
    private MatchGame game;
    private List<String> engList = new ArrayList<>();
    private List<TextField> engTextFields = new ArrayList<>();
    private List<TextField> viTextField = new ArrayList<>();
    private List<TextField> ansTextFields = new ArrayList<>();
    private List<HBox> ansBoxes = new ArrayList<>();
    //private List<TextField> engTextCopy = new ArrayList<>();
    private int score = 0;

   /* private TextField eng1Copy = new TextField();
    private TextField eng2Copy = new TextField();
    private TextField eng3Copy = new TextField();
    private TextField eng4Copy = new TextField();
    private TextField eng5Copy = new TextField();*/

    @FXML
    private AnchorPane anchorGamePane;
    @FXML
    public Button submitBtn;
    @FXML
    public Button exitBtn;
    @FXML
    private TextField eng11;
    @FXML
    private TextField eng12;
    @FXML
    private TextField eng13;
    @FXML
    private TextField eng14;
    @FXML
    private TextField eng15;
    @FXML
    private TextField viet11;
    @FXML
    private TextField viet12;
    @FXML
    private TextField viet13;
    @FXML
    private TextField viet14;
    @FXML
    private TextField viet15;
    @FXML
    private TextField ans11;
    @FXML
    private TextField ans12;
    @FXML
    private TextField ans13;
    @FXML
    private TextField ans14;
    @FXML
    private TextField ans15;
    @FXML
    private HBox answer11;
    @FXML
    private HBox answer12;
    @FXML
    private HBox answer13;
    @FXML
    private HBox answer14;
    @FXML
    private HBox answer15;



    public void initialize() {
        game = new MatchGame();
        game.loadWords();
        game.shuffleWords();
        for (int i = 0; i < 5; i++) {
            System.out.println(game.shuffledWords.get(i)[0] + " " + game.shuffledWords.get(i)[3]);
        }

        // set up eng boxes
        engTextFields.add(eng11);
        engTextFields.add(eng12);
        engTextFields.add(eng13);
        engTextFields.add(eng14);
        engTextFields.add(eng15);
        viTextField.add(viet11);
        viTextField.add(viet12);
        viTextField.add(viet13);
        viTextField.add(viet14);
        viTextField.add(viet15);
        ansBoxes.add(answer11);
        ansBoxes.add(answer12);
        ansBoxes.add(answer13);
        ansBoxes.add(answer14);
        ansBoxes.add(answer15);
        /*engTextCopy.add(eng1Copy);
        engTextCopy.add(eng2Copy);
        engTextCopy.add(eng3Copy);
        engTextCopy.add(eng4Copy);
        engTextCopy.add(eng5Copy);*/


        for (int i = 0; i < 5; i++) {
            engList.add(game.shuffledWords.get(i)[0]);
        }

        Collections.shuffle(engList);

        for (int i = 0; i < 5; i++) {
            engTextFields.get(i).setText(engList.get(i));
            viTextField.get(i).setText(getVietText(game.shuffledWords.get(i)[3]));
            engTextFields.get(i).setEditable(false);
            viTextField.get(i).setEditable(false);
            //ansTextFields.get(i).setEditable(false);

            /*engTextCopy.get(i).setStyle("-fx-background-color: #B5E1E8;\n" +
                    "    -fx-text-fill: #000000;\n" +
                    "    -fx-font-size: 14px;\n" +
                    "    -fx-font-family: \"SVN-Gilroy SemiBold\";\n" +
                    "    -fx-background-radius: 10 10 10 10; /* Bo tròn góc nền \n" +
                    "    -fx-border-radius: 10 10 10 10; /* Bo tròn góc viền ");
            engTextCopy.get(i).setText(engList.get(i));
            engTextCopy.get(i).setLayoutX(50);
            engTextCopy.get(i).setLayoutY(115 + 75 * i);
            engTextCopy.get(i).setEditable(false);
            engTextCopy.get(i).setVisible(true);*/
        }




    }

    public String getVietText(String input) {
        if (input.contains(",") && !input.contains("(")) {
            return input.substring(0, input.indexOf(','));
        } else if (input.contains("(") && !input.contains(",")) {
            return input.substring(0, input.indexOf("("));
        } else if (input.contains(",") && input.contains("(")) {
            int i = Math.min(input.indexOf(","), input.indexOf("("));
            return input.substring(0, i);
        } else {
            return input;
        }
    }

    @FXML
    public void handleClickSubmit(MouseEvent mouseEvent) {
        for (int i = 0; i < 5; i++) {
            String ans = ((TextField) ansBoxes.get(i).getChildren().get(0)).getText();
            String correctAns = game.shuffledWords.get(i)[0];
            if (ans.equals(correctAns)) {
                score++;
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Điểm: ");
        alert.setHeaderText(null);
        alert.setContentText(String.valueOf(score));
        alert.showAndWait();

    }

    @FXML
    public void handleClickExit(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Thoát");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có muốn thoát khỏi lượt chơi này không?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            anchorGamePane.getChildren().clear();
        }
    }


    public TextField findEngField(String content) {
        for (TextField textField : engTextFields) {
            if (textField.getText().equals(content)) {
                return textField;
            }
        }
        return null;
    }

    public void handleDragDrop(DragEvent dragEvent, HBox targetBox) {
        String str = dragEvent.getDragboard().getString();
        targetBox.getChildren().clear();
        TextField a = findEngField(str);
        double x = a.getLayoutX();
        double y = a.getLayoutY();

        a.setEditable(false);
        a.setStyle("-fx-background-color: #EAEDF6;\n" +
                "    -fx-text-fill: #000000;\n" +
                "    -fx-font-size: 14px;\n" +
                "    -fx-font-family: \"SVN-Gilroy SemiBold\";\n" +
                "    -fx-background-radius: 10 10 10 10;\n" +
                "    -fx-border-radius: 10 10 10 10;");
        a.setPrefWidth(108);
        a.setPrefHeight(47);
        a.setAlignment(Pos.CENTER);
        targetBox.getChildren().add(a);

        /*// if click on answer box, english word will return it's previous posistion
        a.setOnMouseClicked(event -> {
            targetBox.getChildren().remove(a);
            TextField t = new TextField();
            t.setText("answer");
            t.setStyle(" -fx-background-color: #EAEDF6;\n" +
                    "    -fx-text-fill: #818BA0;\n" +
                    "    -fx-font-size: 14px;\n" +
                    "    -fx-font-family: \"SVN-Gilroy SemiBold\";\n" +
                    "    -fx-background-radius: 10 10 10 10; /* Bo tròn góc nền \n" +
                    "    -fx-border-radius: 10 10 10 10; /* Bo tròn góc viền ");
            t.setPrefHeight(47);
            t.setPrefWidth(108);
            targetBox.getChildren().add(t);
            a.setLayoutX(x);
            a.setLayoutY(y);
        });*/
    }

    public void handleDragDetect(MouseEvent mouseEvent, TextField textField) {
        Dragboard db = textField.startDragAndDrop(TransferMode.ANY);

        ClipboardContent content = new ClipboardContent();
        content.putString(textField.getText());

        db.setContent(content);

        mouseEvent.consume();
    }

    // handler of english field
    public void handleDragDetect1e(MouseEvent mouseEvent) {
       handleDragDetect(mouseEvent, eng11);
    }

    public void handleDragDetect2e(MouseEvent mouseEvent) {
        handleDragDetect(mouseEvent, eng12);
    }

    public void handleDragDetect3e(MouseEvent mouseEvent) {
        handleDragDetect(mouseEvent, eng13);
    }

    public void handleDragDetect4e(MouseEvent mouseEvent) {
        handleDragDetect(mouseEvent, eng14);
    }

    public void handleDragDetect5e(MouseEvent mouseEvent) {
        handleDragDetect(mouseEvent, eng15);
    }

    // handler of answer boxes
    public void handleDragDrop1(DragEvent dragEvent) {
        handleDragDrop(dragEvent, answer11);
    }

    public void handleDragOver(DragEvent dragEvent) {
        if (dragEvent.getDragboard().hasString()) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void handleDragDrop2(DragEvent dragEvent) {
        handleDragDrop(dragEvent, answer12);
    }

    public void handleDragDrop3(DragEvent dragEvent) {
        handleDragDrop(dragEvent, answer13);
    }

    public void handleDragDrop4(DragEvent dragEvent) {
        handleDragDrop(dragEvent, answer14);
    }

    public void handleDragDrop5(DragEvent dragEvent) {
        handleDragDrop(dragEvent, answer15);
    }


}