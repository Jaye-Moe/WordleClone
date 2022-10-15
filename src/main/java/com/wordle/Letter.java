package com.wordle;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Letter {
    Boolean inAnswer;
    Boolean inCorrectLocation;
    String letter;
    Label block;
    int x_pos;
    int y_pos;

    public Letter(int x_pos, int y_pos){
        this.inAnswer = false;
        this.inCorrectLocation = false;
        this.letter = "";
        this.block = new Label();
        this.block.setMinSize(50,50);
        this.block.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        this.block.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 36));
        this.block.setAlignment(Pos.CENTER);
        this.x_pos = x_pos;
        this.y_pos = y_pos;
    }

    public void setInAnswer(Boolean inAnswer) {
        this.inAnswer = inAnswer;
        this.block.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void setInCorrectLocation(Boolean inCorrectLocation){
        this.inCorrectLocation = inCorrectLocation;
        this.block.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void setLetter(String letter){
        this.letter = letter;
        this.block.setText(letter);
        System.out.println(letter);
    }

    public boolean getInAnswer(){
        return this.inAnswer;
    }

    public boolean getInCorrectLocation(){
        return this.inCorrectLocation;
    }

    public String getLetter(){
        return this.letter;
    }

    public int getX_pos(){
        return this.x_pos;
    }

    public int getY_pos(){
        return this.y_pos;
    }

    public Label getBlock(){
        return this.block;
    }
}
