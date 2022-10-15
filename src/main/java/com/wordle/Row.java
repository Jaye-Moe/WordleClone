package com.wordle;

import java.util.ArrayList;

public class Row {
    ArrayList<Letter> row;
    private final int rowNumber;

    public Row(int rowNumber){
        this.row = new ArrayList<>();
        this.rowNumber = rowNumber;
    }

    public void addRow(){
        int x = 0;

        while (x < 5){
            Letter letter = new Letter(x, this.rowNumber-1);
            this.row.add(letter);
            x++;
        }
    }

    public ArrayList<Letter> getRow(){
        return this.row;
    }
}
