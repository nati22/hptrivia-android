package com.titan.hptrivia.model;

import android.util.Log;

import com.titan.hptrivia.util.Keys;

import org.json.JSONException;
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

        try {
            Question q = Question.parseQuestion(questionJson.toString());
            this.questionText = q.questionText;
            this.correctAnswerText = q.correctAnswerText;
            this.wrongAnswer1Text = q.wrongAnswer1Text;
            this.wrongAnswer2Text = q.wrongAnswer2Text;
            this.wrongAnswer3Text = q.wrongAnswer3Text;
            this.seenBefore = q.seenBefore;

        } catch (JSONException e) {
            Log.e(TAG, "Couldn't parse JSON string: " + questionJson.toString());
        }
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

    public static Question parseQuestion(String questionJSONString) throws JSONException {

        JSONObject questionJSON = new JSONObject(questionJSONString);

        String questionText = (String) questionJSON.get(Keys.QUESTION_JSON.QUESTION_TEXT.name());
        String answerText = (String) questionJSON.get(Keys.QUESTION_JSON.ANSWER_TEXT.name());
        String wrong1Text = (String) questionJSON.get(Keys.QUESTION_JSON.WRONG1_TEXT.name());
        String wrong2Text = (String) questionJSON.get(Keys.QUESTION_JSON.WRONG2_TEXT.name());
        String wrong3Text = (String) questionJSON.get(Keys.QUESTION_JSON.WRONG3_TEXT.name());
        boolean seenBefore = (Boolean) questionJSON.get(Keys.QUESTION_JSON.SEEN_BEFORE.name());

        Question question = new Question(questionText, answerText, wrong1Text, wrong2Text, wrong3Text, seenBefore);
        return question;
    }

    public static String convertQuestionToJsonString(Question question) throws JSONException {

        JSONObject questionJSON = new JSONObject();
        questionJSON.put(Keys.QUESTION_JSON.QUESTION_TEXT.name(), question.questionText);
        questionJSON.put(Keys.QUESTION_JSON.ANSWER_TEXT.name(), question.correctAnswerText);
        questionJSON.put(Keys.QUESTION_JSON.WRONG1_TEXT.name(), question.wrongAnswer1Text);
        questionJSON.put(Keys.QUESTION_JSON.WRONG2_TEXT.name(), question.wrongAnswer2Text);
        questionJSON.put(Keys.QUESTION_JSON.WRONG3_TEXT.name(), question.wrongAnswer3Text);
        questionJSON.put(Keys.QUESTION_JSON.SEEN_BEFORE.name(), question.seenBefore);

        return questionJSON.toString();
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
