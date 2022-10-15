package com.wordle;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class UI {
    ArrayList<Letter> guessLetters;
    ArrayList<String> answerLetters;
    ArrayList<String> wordList;
    ArrayList<String> validAnswers;
    ArrayList<Row> rows;
    String answer;
    String guess;
    Label gameMessage;


    public UI(){
        this.guessLetters = new ArrayList<>();
        this.answerLetters = new ArrayList<>();
        this.wordList = new ArrayList<>();
        this.validAnswers = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.answer ="";
        this.gameMessage = new Label();
        this.generateAnswer();
        this.guess="";
    }

    public void gameOverMessage(){
        this.gameMessage.setText("       Game Over!\n     The answer is\n           " + this.answer);
        this.gameMessage.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 24));
        this.gameMessage.setAlignment(Pos.CENTER);
    }

    public void gameWonMessage(){
        this.gameMessage.setText("          Correct!\n     The answer is\n           " + this.answer);
        this.gameMessage.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 24));
        this.gameMessage.setAlignment(Pos.CENTER);
    }

    public void notValidInputMessage(ArrayList<Letter> letters){
        this.getGuessLetters(letters);
        this.gameMessage.setText("       " + this.guess + " is not\n     a valid choice\n         Try Again");
        this.gameMessage.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 24));
        this.gameMessage.setAlignment(Pos.CENTER);

    }

    public void checkBackSpaceInput(@NotNull ArrayList<Letter> letters) {
        System.out.println("Backspace");
        if (!letters.get(4).getLetter().equals("")) {
            letters.get(4).setLetter("");
        } else if (!letters.get(3).getLetter().equals("")) {
            letters.get(3).setLetter("");
        } else if (!letters.get(2).getLetter().equals("")) {
            letters.get(2).setLetter("");
        } else if (!letters.get(1).getLetter().equals("")) {
            letters.get(1).setLetter("");
        } else if (!letters.get(0).getLetter().equals("")) {
            letters.get(0).setLetter("");
        }
    }

    public int checkEnterKeyInput(@NotNull ArrayList<Letter> letters, int currentRow) {
        this.gameMessage.setText("");
        if (letters.get(4).getLetter().equals("")) {
            System.out.println("Enter pressed without full answer");
            return currentRow;
        }else{
            System.out.println("Enter pressed with full answer");
            this.getGuessLetters(letters);
            if(!this.checkIfValidAnswer(letters)){
                this.notValidInputMessage(letters);
                System.out.println("not a valid choice");
                return currentRow;
            }else {
                this.checkCorrectLocation(letters);
                this.checkCorrectLettersInIncorrectPlace(letters);
                this.printResults(letters);
                if (!this.checkForWin(letters)) {
                    this.gameWonMessage();
                    return 99;
                }
                System.out.println(currentRow);
                if (currentRow == 5) {
                    this.gameOverMessage();
                    return 999;
                }
                return currentRow + 1;
            }

        }
    }

    public boolean checkIfValidAnswer(ArrayList<Letter> letters){
        try {
            File myObj = new File("PossibleChoices.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine().toUpperCase();
                this.validAnswers.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            File myObj = new File("AnswerList.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine().toUpperCase();
                this.validAnswers.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        int i = 0;
        StringBuilder guessedAnswer = new StringBuilder();
        while (i < 5){
            guessedAnswer.append(letters.get(i).getLetter());
            i++;
        }

        System.out.println(guessedAnswer);
        return this.validAnswers.contains(guessedAnswer.toString());
    }

    public void getGuessLetters(ArrayList<Letter> letters){
        this.guess = "";
        int y =0;
        while (y < 5){
            System.out.println(letters.get(y).getLetter());
            this.guess=this.guess+letters.get(y).getLetter();
            System.out.println(this.guess);
            y ++;
        }
    }

    public void generateAnswer() {
        try {
            File myObj = new File("AnswerList.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                this.wordList.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        int length = this.wordList.size();
        Random random = new Random();
        int randomNumber = random.nextInt(length);
        this.answer = wordList.get((randomNumber)).toUpperCase();

        int y = 0;
        for (char answerLetter : this.answer.toCharArray()) {
            this.answerLetters.add(String.valueOf(answerLetter));
            System.out.println(this.answerLetters.get(y));
            y++;
        }

    }

    public void checkCorrectLocation(ArrayList<Letter> letters){
        int i = 0;
        while (i <5) {
            if (letters.get(i).getLetter().equals(this.answerLetters.get(i))) {
                System.out.println("Guess letter: " + letters.get(i).getLetter() + " Answer letter: " + this.answerLetters.get(i));
                letters.get(i).setInAnswer(true);
                letters.get(i).setInCorrectLocation(true);
                this.answerLetters.set(this.answerLetters.indexOf(letters.get(i).getLetter()), "0");
            }
            System.out.println("Guess letter: " + letters.get(i).getLetter() + " Answer letter: " + this.answerLetters.get(i));
            i++;
        }
    }

    public void checkCorrectLettersInIncorrectPlace(ArrayList<Letter> letters){
        int i = 0;
        while (i < 5) {
            if (this.answerLetters.contains(letters.get(i).getLetter())) {
                letters.get(i).setInAnswer(true);
                this.answerLetters.set(this.answerLetters.indexOf(letters.get(i).getLetter()), "0");
            }
            i++;
        }
    }

    public boolean checkForWin(ArrayList<Letter> letters){
        int i = 0;
        StringBuilder guessedAnswer = new StringBuilder();
        while (i < 5){
            guessedAnswer.append(letters.get(i).getLetter());
            i++;
        }
        System.out.println("Guess: " + guessedAnswer);
        System.out.println("Answer: " + this.answer);
        if (guessedAnswer.toString().equals(this.answer)){
            System.out.println("CORRECT!");
            return false;
        }
        return true;

    }

    public void printResults(ArrayList<Letter> letters){
                //PRINT THE RESULTS
        this.answerLetters.clear();
        for (char answerLetter : this.answer.toCharArray()) {
            this.answerLetters.add(String.valueOf(answerLetter));
        }

        int i = 0;
        while (i < 5){
            System.out.println("Guess letter: " + letters.get(i).getLetter() + ", Answer letter:  " +this.answerLetters.get(i) + ", In answer: " + letters.get(i).getInAnswer() + ", In correct location: " + letters.get(i).getInCorrectLocation());

            i++;


        }
    }

    public void checkLetterInput(@NotNull ArrayList<Letter> letters, String input) {
        if (letters.get(0).getLetter().equals("")){
            letters.get(0).setLetter(input);
        }else if (letters.get(1).getLetter().equals("")){
            letters.get(1).setLetter(input);
        }else if (letters.get(2).getLetter().equals("")){
            letters.get(2).setLetter(input);
        }else if (letters.get(3).getLetter().equals("")){
            letters.get(3).setLetter(input);
        }else if (letters.get(4).getLetter().equals("")){
            letters.get(4).setLetter(input);
        }
    }

    public void generateRows(){
        int rowNumber = 1;
        while (rowNumber<7){
            Row row = new Row(rowNumber);
            row.addRow();
            this.rows.add(row);
            rowNumber++;
        }
    }

    public void displayRows(GridPane gridPane){
        int i = 0;
        while (i<6){
            for(Letter letter:this.rows.get(i).getRow()) {
                gridPane.add(letter.getBlock(), letter.getX_pos(), letter.getY_pos());
            }
            i++;
        }
        gridPane.add(this.gameMessage, 0, 7,5,3);
    }

    public ArrayList<Row> getRows(){
        return this.rows;
    }

    public void clearRows(){
        this.rows.clear();
    }

    public void clearMessage(){
        this.gameMessage.setText("");
    }

    public void clearAnswerLetters(){
        this.answerLetters.clear();
    }

    public void clearGuessLetters(){
        this.guessLetters.clear();
    }

}
