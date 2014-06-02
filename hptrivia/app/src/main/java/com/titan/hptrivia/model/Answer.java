package com.titan.hptrivia.model;

/**
 * Created by ntessema on 6/1/14.
 */
public class Answer {

    private String answerText;
    private String answerExplanation;

    public Answer(String answerText) {
        this.answerText = answerText;
        this.answerExplanation = null;
    }

    public Answer(String answerText, String answerExplanation) {
        this.answerText = answerText;
        this.answerExplanation = null;
    }

    public String getText() {
        return answerText;
    }

    public boolean hasExplanation() {
        return answerExplanation != null;
    }

}