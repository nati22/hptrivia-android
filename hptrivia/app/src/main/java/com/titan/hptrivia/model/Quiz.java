package com.titan.hptrivia.model;

import android.util.Log;

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

    public Question getQuestion(int x) { return questions.get(x); }

    public int size() { return questions.size(); }

    public static Quiz parseQuiz(String quizJSONString) throws JSONException {

        JSONObject quizJSON = new JSONObject(quizJSONString);
        ArrayList<Question> questionList = new ArrayList<Question>();

        // TODO Can I be sure that every element of the Quiz JSON is a Question?
        // TODO (cont'd) See Keys.QUIZ_JSON

        // as we remove the questions, length will decrease
        int numQuestions = quizJSON.length();

        for (int i = 1; i <= numQuestions; i++) {
            JSONObject questionJSON = (JSONObject) quizJSON.remove(Keys.QUIZ_JSON.QUESTION.name() + i);
            if (questionJSON == null) {
                Log.e("parseQuiz", "Could not find " + (Keys.QUIZ_JSON.QUESTION.name() + i));
                continue;
            }

            questionList.add(new Question(questionJSON));
    //        Log.i("parseQuiz", "added question");
        }

        return new Quiz(questionList);
    }
}
