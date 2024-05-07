import base.Question;
import base.QuizGame;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game1Controller extends GameController {
    public Button exitButton;
    private QuizGame game;
    private List<Question> questions;
    private int currentQuestionId;
    private int score = 0;
    private List<Button> buttons = new ArrayList<>();

    @FXML
    public Button answer1;
    @FXML
    public Button answer2;
    @FXML
    public Button answer3;
    @FXML
    public Button answer4;
    @FXML
    private TextArea questionArea;
    @FXML
    private Label correctAnswer;
    @FXML
    private Label correctAnsTittle;
    @FXML
    private Button nextQuestionButton;
    @FXML
    private AnchorPane anchorPaneGame;

    public void initialize() throws IOException {
        game = new QuizGame();
        questions = game.loadQuestions();
        buttons.add(answer1);
        buttons.add(answer2);
        buttons.add(answer3);
        buttons.add(answer4);
        currentQuestionId = 0;
        questionArea.setWrapText(true);
        questionArea.setEditable(false);

        displayQuestion(currentQuestionId);
    }

    public void displayQuestion(int questionNumber) {
        Question question = questions.get(questionNumber);

        // diplay text of question and options
        questionArea.setText(question.getQuestion());
        answer1.setText(question.getOptions().get(0));
        answer2.setText(question.getOptions().get(1));
        answer3.setText(question.getOptions().get(2));
        answer4.setText(question.getOptions().get(3));

        // associate each answer choices with an index value => easy compare to correct answer
        answer1.setUserData(0);
        answer2.setUserData(1);
        answer3.setUserData(2);
        answer4.setUserData(3);

        // set normal style of answer buttons
        answer1.setStyle("-fx-border-color: #1D93F3; -fx-border-radius: 10 10 10 10; -fx-background-color: #ffffff; -fx-background-radius: 10 10 10 10;");
        answer2.setStyle("-fx-border-color: #1D93F3; -fx-border-radius: 10 10 10 10; -fx-background-color: #ffffff; -fx-background-radius: 10 10 10 10;");
        answer3.setStyle("-fx-border-color: #1D93F3; -fx-border-radius: 10 10 10 10; -fx-background-color: #ffffff; -fx-background-radius: 10 10 10 10;");
        answer4.setStyle("-fx-border-color: #1D93F3; -fx-border-radius: 10 10 10 10; -fx-background-color: #ffffff; -fx-background-radius: 10 10 10 10;");

        // invisible correctAnswer and correctAnsTittle
        correctAnswer.setVisible(false);
        correctAnsTittle.setVisible(false);
    }

    @FXML
    public void handleClickAnswer(ActionEvent actionEvent) {
        Button selectedButton = (Button) actionEvent.getSource();
        Question currentQuestion = questions.get(currentQuestionId);

        // if no answer was selected
        if (!currentQuestion.getAnswered()) {
            int i = (int) selectedButton.getUserData();
            if (selectedButton.getText().charAt(0) == currentQuestion.getAnswerChar()) {
                score++;
                buttons.get(i).setStyle("-fx-background-color: #00df3f; -fx-background-radius: 10 10 10 10;");

            } else {
                buttons.get(i).setStyle("-fx-background-color: #FC5D5D; -fx-background-radius: 10 10 10 10;");
                correctAnswer.setVisible(true);
                correctAnsTittle.setVisible(true);
                correctAnswer.setText(currentQuestion.getAnswer());
            }
            currentQuestion.setAnswered(true);
        }

    }

    public void handleClickNext(ActionEvent actionEvent) throws IOException {
        currentQuestionId++;
        if (currentQuestionId < (questions.size() - 1)) {
            displayQuestion(currentQuestionId);
        } else if (currentQuestionId == (questions.size() - 1)) {
            nextQuestionButton.setText("Nộp bài");
            displayQuestion(currentQuestionId);
        } else {
            showSccore();

            anchorPaneGame.getChildren().clear();
        }
    }

    public void handleClickExit(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("exit.fxml"));
        Parent root = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(exitButton.getScene().getWindow());
        dialogStage.setScene(new Scene(root));
        dialogStage.setTitle("Thoát");

        ExitController exitController = loader.getController();
        exitController.setDialogStage(dialogStage);
        dialogStage.showAndWait();

        if (exitController.dialogStage.getUserData().equals("1")) {
            showSccore();
            anchorPaneGame.getChildren().clear();
        }
    }

    public void showSccore() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("resultGame1.fxml"));
        Parent root = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(nextQuestionButton.getScene().getWindow());
        dialogStage.setScene(new Scene(root));
        dialogStage.setTitle("Điểm");

        Result2Controller controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.init(score);
        dialogStage.showAndWait();
    }




}
