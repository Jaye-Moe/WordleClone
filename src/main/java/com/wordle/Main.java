package com.wordle;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class Main extends Application {
    @Override
    public void start(@NotNull Stage stage) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        Scene scene=new Scene(gridPane,292,500);
        gridPane.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        stage.setScene(scene);
        stage.setTitle("Wordle Clone");

        UI ui = new UI();

        ui.generateRows();
        ui.displayRows(gridPane);

        final int[] currentRow = {0};
        final boolean[] gameIsOn = {true};
        final int[] gamesWon = {0};
        final int[] gamesPlayed = {0};
        Label gameStats = new Label();

        scene.setOnKeyPressed(event -> {
            String input = String.valueOf(event.getCode());
            if (gameIsOn[0]) {
                if (event.getCode() == KeyCode.BACK_SPACE) {
                    ui.checkBackSpaceInput(ui.getRows().get(currentRow[0]).getRow());
                } else if (event.getCode() == KeyCode.ENTER) {
                    currentRow[0] = ui.checkEnterKeyInput(ui.getRows().get(currentRow[0]).getRow(), currentRow[0]);
                } else if (input.matches("[A-Za-z]")) {
                    ui.checkLetterInput(ui.getRows().get(currentRow[0]).getRow(), input);
                }
                if (currentRow[0]==99){
                    gameIsOn[0] = false;
                    gamesWon[0]++;


                }
                if (currentRow[0]==999){
                    gameIsOn[0] = false;
                }
            }
            if(!gameIsOn[0]){
                gamesPlayed[0]++;
                System.out.println("Game over!!!!");


                gameStats.setText("");
                gameStats.setText("Games Won: " +gamesWon[0] + " / Games Played: " + gamesPlayed[0]);

                Image playAgainImageFile = new Image("file:PlayAgain.png");
                ImageView playAgainImage = new ImageView(playAgainImageFile);
                gridPane.add(playAgainImage,1,11,2,1);

                playAgainImage.setOnMouseClicked(ev ->{
                    System.out.println("Clicked Play Again");
                    ui.clearRows();
                    ui.clearGuessLetters();
                    ui.clearAnswerLetters();
                    gridPane.getChildren().clear();
                    ui.generateRows();
                    ui.displayRows(gridPane);
                    ui.clearMessage();
                    ui.generateAnswer();
                    gridPane.add(gameStats,1,6,4,1);

                    currentRow[0]=0;
                    gameIsOn[0]=true;
                });
            }
        });

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}