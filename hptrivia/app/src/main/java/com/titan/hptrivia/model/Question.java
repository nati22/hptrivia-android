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
    private boolean seenBefore;

    private Answer correctAnswer;
    private Answer wrongAnswer1;
    private Answer wrongAnswer2;
    private Answer wrongAnswer3;

    public Question(JSONObject questionJson) {

        try {
            Question q = Question.convertJSONObjectToQuestion(questionJson);
            this.questionText = q.questionText;
            this.correctAnswer = q.correctAnswer;
            this.wrongAnswer1 = q.wrongAnswer1;
            this.wrongAnswer2 = q.wrongAnswer2;
            this.wrongAnswer3 = q.wrongAnswer3;
            this.seenBefore = q.seenBefore;

        } catch (JSONException e) {
            Log.e(TAG, "Couldn't parse JSON string: " + questionJson.toString());
        }
    }

    public Question(String questionText, Answer correctAnswer,
                    Answer wrongAnswer1, Answer wrongAnswer2,
                    Answer wrongAnswer3, boolean seenBefore) {

        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.wrongAnswer1 = wrongAnswer1;
        this.wrongAnswer2 = wrongAnswer2;
        this.wrongAnswer3 = wrongAnswer3;
        this.seenBefore = seenBefore;
    }

    public String getQuestionText() {
        return questionText;
    }

    public Answer getCorrectAnswer() { return correctAnswer; }

    public Answer getWrongAnswer1() { return wrongAnswer1; }

    public Answer getWrongAnswer2() { return wrongAnswer2; }

    public Answer getWrongAnswer3() { return wrongAnswer3; }

    public boolean hasBeenSeenBefore() { return seenBefore; }

/*    public static Question convertJSONObjectToQuestion(JSONObject questionJSONObject) throws JSONException {

        String questionText = (String) questionJSONObject.get(Keys.QUESTION_JSON.QUESTION_TEXT.name());
        String answerText = (String) questionJSONObject.get(Keys.QUESTION_JSON.ANSWER_TEXT.name());
        String wrong1Text = (String) questionJSONObject.get(Keys.QUESTION_JSON.WRONG1_TEXT.name());
        String wrong2Text = (String) questionJSONObject.get(Keys.QUESTION_JSON.WRONG2_TEXT.name());
        String wrong3Text = (String) questionJSONObject.get(Keys.QUESTION_JSON.WRONG3_TEXT.name());
        boolean seenBefore = (Boolean) questionJSONObject.get(Keys.QUESTION_JSON.SEEN_BEFORE.name());

        return new Question(questionText, answerText, wrong1Text, wrong2Text, wrong3Text, seenBefore);
    }*/

    public static Question convertJSONObjectToQuestion(JSONObject questionJSONObject) throws JSONException {

        String questionText = (String) questionJSONObject.get(Keys.QUESTION_JSON.QUESTION_TEXT.name());
        Answer answer = new Answer((String)questionJSONObject.get(Keys.QUESTION_JSON.ANSWER_TEXT.name()));
        Answer wrong1 = new Answer ((String) questionJSONObject.get(Keys.QUESTION_JSON.WRONG1_TEXT.name()));
        Answer wrong2 = new Answer((String) questionJSONObject.get(Keys.QUESTION_JSON.WRONG2_TEXT.name()));
        Answer wrong3 = new Answer((String) questionJSONObject.get(Keys.QUESTION_JSON.WRONG3_TEXT.name()));
        boolean seenBefore = (Boolean) questionJSONObject.get(Keys.QUESTION_JSON.SEEN_BEFORE.name());

        return new Question(questionText, answer, wrong1, wrong2, wrong3, seenBefore);
    }

    public static JSONObject convertQuestionToJsonObject(Question question) throws JSONException {

        JSONObject questionJSON = new JSONObject();
        questionJSON.put(Keys.QUESTION_JSON.QUESTION_TEXT.name(), question.questionText);
        questionJSON.put(Keys.QUESTION_JSON.ANSWER_TEXT.name(), question.getCorrectAnswer().getText());
        questionJSON.put(Keys.QUESTION_JSON.WRONG1_TEXT.name(), question.getWrongAnswer1().getText());
        questionJSON.put(Keys.QUESTION_JSON.WRONG2_TEXT.name(), question.getWrongAnswer2().getText());
        questionJSON.put(Keys.QUESTION_JSON.WRONG3_TEXT.name(), question.getWrongAnswer3().getText());
        questionJSON.put(Keys.QUESTION_JSON.SEEN_BEFORE.name(), question.seenBefore);
        return questionJSON;
    }

    @Override
    public String toString() {
        return String.format("Question text: \"%s\"\nCorrect answer: " +
                "\"%s\"\nWrong answer: \"%s\"\nWrong answer: \"%s\"\n" +
                "Wrong answer: \"%s\"", questionText, getCorrectAnswer().getText(),
                getWrongAnswer1().getText(), getWrongAnswer2().getText(), getWrongAnswer3().getText());
    }
}
