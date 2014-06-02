package com.titan.hptrivia.model;

import org.json.JSONObject;

/**
 * Created by ntessema on 6/1/14.
 */
public class Question {

    private static final String TAG = Question.class.getSimpleName();

    //TODO change String to Answer
    private String questionText;
    private String correctAnswerText;
    private String wrongAnswer1Text;
    private String wrongAnswer2Text;
    private String wrongAnswer3Text;
    private boolean seenBefore;

    public Question(JSONObject questionJson) {

    }

    public Question(String questionText, String correctAnswerText,
                    String wrongAnswer1Text, String wrongAnswer2Text,
                    String wrongAnswer3Text, boolean seenBefore) {

        this.questionText = questionText;
        this.correctAnswerText = correctAnswerText;
        this.wrongAnswer1Text = wrongAnswer1Text;
        this.wrongAnswer2Text = wrongAnswer2Text;
        this.wrongAnswer3Text = wrongAnswer3Text;
        this.seenBefore = seenBefore;

    }


    public String getQuestionText() {
        return questionText;
    }

    public String getCorrectAnswerText() {
        return correctAnswerText;
    }

    public String getWrongAnswer1Text() {
        return wrongAnswer1Text;
    }

    public String getWrongAnswer2Text() {
        return wrongAnswer2Text;
    }

    public String getWrongAnswer3Text() {
        return wrongAnswer3Text;
    }

    public boolean isSeenBefore() {
        return seenBefore;
    }


    @Override
    public String toString() {
        return String.format("Question text: \"%s\"\nCorrect answer: " +
                "\"%s\"\nWrong answer: \"%s\"\nWrong answer: \"%s\"\n" +
                "Wrong answer: \"%s\"", questionText, correctAnswerText,
                wrongAnswer1Text, wrongAnswer2Text, wrongAnswer3Text);
        //return super.toString();
    }
}
