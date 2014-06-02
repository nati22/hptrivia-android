package com.titan.hptrivia.model;

import com.titan.hptrivia.util.Keys;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ntessema on 6/1/14.
 */
public class Quiz {

    private ArrayList<Question> questions;

    public Quiz(ArrayList questions) {
        this.questions = questions;
    }

    public Question getNextQuestion() { return questions.get(0); }

    public static Quiz parseQuiz(String quizJSONString) throws JSONException {

        JSONObject quizJSON = new JSONObject(quizJSONString);
        ArrayList<Question> questionList = new ArrayList<Question>();

        // TODO Can I be sure that every element of the Quiz JSON is a Question?
        // TODO (cont'd) See Keys.QUIZ_JSON
        // TODO Do I really not care about the order of the questions?
        JSONObject questionJSON = (JSONObject) quizJSON.remove(Keys.QUIZ_JSON.QUESTION.name());

        while (questionJSON != null) {

            String questionText = (String) questionJSON.get(Keys.QUESTION_JSON.QUESTION_TEXT.name());
            String answerText = (String) questionJSON.get(Keys.QUESTION_JSON.ANSWER_TEXT.name());
            String wrong1Text = (String) questionJSON.get(Keys.QUESTION_JSON.WRONG1_TEXT.name());
            String wrong2Text = (String) questionJSON.get(Keys.QUESTION_JSON.WRONG2_TEXT.name());
            String wrong3Text = (String) questionJSON.get(Keys.QUESTION_JSON.WRONG3_TEXT.name());
            boolean seenBefore = (Boolean) questionJSON.get(Keys.QUESTION_JSON.SEEN_BEFORE.name());

            Question question = new Question(questionText, answerText, wrong1Text, wrong2Text, wrong3Text, seenBefore);
            questionList.add(question);

            questionJSON = (JSONObject) quizJSON.remove(Keys.QUIZ_JSON.QUESTION.name());
        }

        Quiz quiz = new Quiz(questionList);
        return quiz;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
