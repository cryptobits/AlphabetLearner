package com.example.asuper.alphabetlearner.Objects;

/**
 * Created by Super on 6/23/2017.
 */

// This class accumulates the various properties of each letter, facilitating
// the tracking of progress in the database
public class Letter {
    private Long id;
    private String letter;
    private int correct;
    private int incorrect;
    private String ratio;


    public Letter() {

    }

    public Letter(String letter, int correct, int incorrect) {
        this.letter = letter;
        this.correct = correct;
        this.incorrect = incorrect;
        updateRatioString();
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(int incorrect) {
        this.incorrect = incorrect;
        updateRatioString();
    }

    public int getCorrect() {

        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
        updateRatioString();
    }

    public String getLetter() {

        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public void incrementCorrect() {
        correct++;
        updateRatioString();
    }

    public void incrementIncorrect() {
        incorrect++;
        updateRatioString();
    }

    public void setRatioString(String string) {
        updateRatioString();
    }

    private void updateRatioString() {
        ratio = String.valueOf(correct) + "/" + String.valueOf(correct + incorrect);
    }

    public String getRatioString() {
        return ratio;
    }
}
