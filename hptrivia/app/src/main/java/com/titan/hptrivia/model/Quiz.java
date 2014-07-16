package com.titan.hptrivia.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

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

    public static Quiz parseQuiz(String JSONString) throws JSONException {
        Log.d("Quiz.parseQuiz", "received " + JSONString);

        JSONObject responseJSON = new JSONObject(JSONString.trim());        // TODO should i trim? from (http://stackoverflow.com/questions/9151619/java-iterate-over-jsonobject)
        JSONObject quizJSON = new JSONObject(responseJSON.getString("content").replace("\\\"", "\""));
        Log.d("Quiz.parseQuiz", "content = " + responseJSON.getString("content").replace("\\\"", "\""));
        ArrayList<Question> questionList = new ArrayList<Question>();

        // TODO Can I be sure that every element of the Quiz JSON is a Question?
        // TODO (cont'd) See Keys.QUIZ_JSON

        // as we remove the questions, length will decrease
        int numQuestions = quizJSON.length();

        Iterator<?> iterator = quizJSON.keys();

        Log.d("Quiz", "entering while loop (jsonobj has " + numQuestions + " elements)");
        while( iterator.hasNext() ){
            String key = (String)iterator.next();
            if(quizJSON.get(key) instanceof JSONObject ){

                JSONObject questionJSON = (JSONObject) quizJSON.get(key);
                questionList.add(new Question(questionJSON));
            }
        }
        Log.d("Quiz", "done with while loop.");

        for (Question q : questionList) Log.d("question: ", q.getQuestionText());

    /*    for (int i = 1; i <= numQuestions; i++) {
            JSONObject questionJSON = (JSONObject) quizJSON.remove(Keys.QUIZ_JSON.QUESTION.name() + i);
            if (questionJSON == null) {
                Log.e("parseQuiz", "Could not find " + (Keys.QUIZ_JSON.QUESTION.name() + i));
                continue;
            }

            questionList.add(new Question(questionJSON));
    //        Log.i("parseQuiz", "added question");
        }*/

        return new Quiz(questionList);
    }
}
