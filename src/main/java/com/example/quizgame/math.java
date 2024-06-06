package com.example.quizgame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class math extends Application {

    private int score = 0;
    private int currentQuestionIndex = 0;
    private Label questionLabel;
    private Label scoreLabel;
    private Label feedbackLabel;
    private Label timerLabel;
    private HBox buttonBox; // Define the HBox for answer buttons
    private Button startButton;
    private Button exitButton;
    private Button playAgainButton;
    private Timeline timeline;
    private int remainingTime;
    private final int TIME_LIMIT = 10; // 10 seconds for each question

    private List<Question> questions;
    private List<Question> randomQuestions;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Math Quiz Game");

        Image backgroundImage = new Image("https://agileprep.ca/_next/image?url=https:%2F%2Fagileprep.ca%2Fpublic%2Fcourses%2F12%2FcourseCoverImg.jpg&w=3840&q=75");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(800); // Set the width to match your scene
        backgroundImageView.setFitHeight(600); // Set the height to match your scene

        startButton = new Button("Start Quiz");
        startButton.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        startButton.setOnAction(event -> startQuiz(primaryStage));

        exitButton = new Button("End Quiz");
        exitButton.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        exitButton.setOnAction(event -> primaryStage.close());

        startButton.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-background-color: #ffcc00; -fx-text-fill: white; -fx-background-radius: 30;");
        exitButton.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-background-color: #ff4d4d; -fx-text-fill: white; -fx-background-radius: 30;");
        startButton.setStyle(startButton.getStyle() + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10,0,0,1);");
        exitButton.setStyle(exitButton.getStyle() + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10,0,0,1);");

        VBox buttonLayout = new VBox(20);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getChildren().addAll(startButton, exitButton);

        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundImageView, buttonLayout);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        initializeQuestions();
    }

    private void initializeQuestions() {
        questions = new ArrayList<>();
        questions.add(new Question("What is 1 + 9?", new String[]{"8", "9", "10", "11"}, 2));
        questions.add(new Question("What is 5 * 3?", new String[]{"12", "15", "18", "20"}, 1));
        questions.add(new Question("What is 9 - 3?", new String[]{"2", "3", "6", "5"}, 2));
        questions.add(new Question("What is 15 - 7?", new String[]{"5", "6", "7", "8"}, 3));
        questions.add(new Question("What is 2 * 3?", new String[]{"9", "10", "6", "12"}, 2));
        questions.add(new Question("What is 4 + 4?", new String[]{"12", "14", "16", "8"}, 3));
        questions.add(new Question("What is 10 - 2?", new String[]{"3", "4", "8", "6"}, 2));
        questions.add(new Question("What is 7 + 3?", new String[]{"10", "2", "25", "27"}, 0));
        questions.add(new Question("What is 2 + 2?", new String[]{"3", "4", "5", "6"}, 1));
        questions.add(new Question("What is 3 * 1?", new String[]{"2", "3", "4", "5"}, 1));
        questions.add(new Question("What is 4 - 1?", new String[]{"1", "2", "3", "3"}, 2));
        questions.add(new Question("What is 6 + 2?", new String[]{"6", "7", "8", "9"}, 2));
        questions.add(new Question("What is 5 / 1?", new String[]{"4", "5", "6", "7"}, 1));
        questions.add(new Question("What is 7 - 2?", new String[]{"4", "5", "6", "7"}, 1));
        questions.add(new Question("What is 3 + 5?", new String[]{"7", "8", "9", "10"}, 1));
        questions.add(new Question("What is 9 - 4?", new String[]{"4", "5", "6", "7"}, 1));
        questions.add(new Question("What is 2 * 2?", new String[]{"3", "4", "5", "6"}, 1));
        questions.add(new Question("What is 6 / 2?", new String[]{"2", "3", "4", "5"}, 1));
        questions.add(new Question("What is 1 + 1?", new String[]{"1", "2", "3", "4"}, 1));
        questions.add(new Question("What is 5 + 2?", new String[]{"6", "7", "8", "9"}, 1));
        questions.add(new Question("What is 8 - 3?", new String[]{"4", "5", "6", "7"}, 1));
        questions.add(new Question("What is 3 * 2?", new String[]{"5", "6", "7", "8"}, 1));
        questions.add(new Question("What is 10 / 2?", new String[]{"4", "5", "6", "7"}, 1));

    }

    public void startQuiz(Stage primaryStage) {
        primaryStage.setTitle("Math Quiz Game");
        Image backgroundImage = new Image("https://agileprep.ca/_next/image?url=https:%2F%2Fagileprep.ca%2Fpublic%2Fcourses%2F12%2FcourseCoverImg.jpg&w=3840&q=75");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setPreserveRatio(true);
        backgroundImageView.setFitWidth(800); // Initial width, will adjust with window
        backgroundImageView.setFitHeight(600); // Initial height, will adjust with window

        // Labels
        Label instructionsLabel = new Label("Welcome to the Math Quiz Game!");
        instructionsLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 0,0,0,1)");

        questionLabel = new Label();
        questionLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #00FFFF; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 0,0,0,1)");

        scoreLabel = new Label("Score: " + score);
        scoreLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        feedbackLabel = new Label();
        feedbackLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        timerLabel = new Label("Time: " + TIME_LIMIT);
        timerLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        // Buttons
        Button[] answerButtons = new Button[4];
        for (int i = 0; i < 4; i++) {
            answerButtons[i] = new Button();
            answerButtons[i].setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-background-color: #FF69B4; -fx-text-fill: white; -fx-background-radius: 30;");
            answerButtons[i].setMaxWidth(Double.MAX_VALUE); // Allow buttons to resize
            int finalI = i;
            answerButtons[i].setOnAction(e -> checkAnswer(finalI));
        }

        buttonBox = new HBox(10); // Initialize the HBox
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(answerButtons);

        // Layouts
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(instructionsLabel, questionLabel, buttonBox, scoreLabel, feedbackLabel, timerLabel);
        layout.setStyle("-fx-background-color: transparent;");

        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundImageView, layout);
        root.setStyle("-fx-background-color: transparent;");

        Scene scene = new Scene(root, 1200, 648); // Initial size, will adjust with window

        // Make the scene responsive
        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            backgroundImageView.setFitWidth(newValue.doubleValue());
        });

        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            backgroundImageView.setFitHeight(newValue.doubleValue());
        });

        primaryStage.setScene(scene);
        primaryStage.show();

        randomQuestions = new ArrayList<>(questions);
        Collections.shuffle(randomQuestions);

        updateQuestion(); // Initialize UI with the first question
    }


    private void updateQuestion() {
        if (currentQuestionIndex < randomQuestions.size()) {
            Question currentQuestion = randomQuestions.get(currentQuestionIndex);
            questionLabel.setText(currentQuestion.getQuestionText());
            for (int i = 0; i < 4; i++) {
                Button button = (Button) buttonBox.getChildren().get(i);
                buttonBox.getChildren().get(i);
                button.setText(currentQuestion.getAnswerChoices()[i]);
            }
            remainingTime = TIME_LIMIT;
            timerLabel.setText("Time: " + remainingTime);
            startTimer();
        } else {
            // All questions answered
            questionLabel.setText("Quiz completed!");
            buttonBox.setDisable(true);

            // Show the end scene
            showEndScene();
        }
    }

    private void startTimer() {
        if (timeline != null) {
            timeline.stop();
        }
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            remainingTime--;
            timerLabel.setText("Time: " + remainingTime);
            if (remainingTime <= 0) {
                currentQuestionIndex++;
                updateQuestion();
            }
        }));
        timeline.setCycleCount(TIME_LIMIT);
        timeline.play();
    }

    private void checkAnswer(int answerIndex) {
        if (timeline != null) {
            timeline.stop();
        }
        Question currentQuestion = randomQuestions.get(currentQuestionIndex);
        if (answerIndex == currentQuestion.getCorrectAnswerIndex()) {
            feedbackLabel.setText("Correct!");
            score++;
        } else {
            feedbackLabel.setText("Incorrect. Try again!");
        }
        scoreLabel.setText("Score: " + score);
        currentQuestionIndex++;
        updateQuestion();
    }

    private void showEndScene() {
        // Clear the labels
        questionLabel.setText("");
        feedbackLabel.setText("");

        // Display final score and offer to play again
        Label finalScoreLabel = new Label("Final Score: " + score);
        finalScoreLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: black; -fx-font-weight: bold;");

        playAgainButton = new Button("Play Again");
        playAgainButton.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-background-color: #FF69B4; -fx-text-fill: white; -fx-background-radius: 30;");
        playAgainButton.setOnAction(event -> {
            // Reset the quiz
            score = 0;
            currentQuestionIndex = 0;
            Collections.shuffle(randomQuestions);
            updateQuestion();
            startQuiz((Stage) finalScoreLabel.getScene().getWindow());
        });

        VBox endLayout = new VBox(20);
        endLayout.setAlignment(Pos.CENTER);
        Image backgroundImage = new Image("https://agileprep.ca/_next/image?url=https:%2F%2Fagileprep.ca%2Fpublic%2Fcourses%2F12%2FcourseCoverImg.jpg&w=3840&q=75");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        endLayout.getChildren().addAll(finalScoreLabel, playAgainButton);

        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundImageView, endLayout);

        Scene endScene = new Scene(root, 800, 600); // Same size as before
        Stage primaryStage = (Stage) questionLabel.getScene().getWindow(); // Get the current stage
        primaryStage.setScene(endScene);
    }


    public static void main(String[] args) {
        launch(args);
    }
}

class Question {
    private String questionText;
    private String[] answerChoices;
    private int correctAnswerIndex;

    public Question(String questionText, String[] answerChoices, int correctAnswerIndex) {
        this.questionText = questionText;
        this.answerChoices = answerChoices;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getAnswerChoices() {
        return answerChoices;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}
