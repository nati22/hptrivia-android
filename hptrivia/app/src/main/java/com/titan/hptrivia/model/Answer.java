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
        this.answerExplanation = answerExplanation;
    }

    public String getText() {
        return answerText;
    }

    public String getExplanation() { return answerExplanation; }

    public boolean hasExplanation() {
        return answerExplanation != null;
    }

    @Override
    public boolean equals(Object o) {
        Answer otherAnswer = (Answer) o;

        return this.answerText.equals(otherAnswer.getText())
                && (this.answerExplanation != null && otherAnswer.answerExplanation != null) ? (this.answerExplanation.equals(otherAnswer.getExplanation())) : true;
    }
}
