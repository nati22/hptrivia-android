/*
package com.titan.hptrivia.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.titan.hptrivia.activity.QuizActivity;
import com.titan.hptrivia.util.Keys;

*/
/**
 * Created by ntessema on 6/1/14.
 *//*

public class QuizManager {

    private static final String TAG = QuizManager.class.getSimpleName();

    */
/* Only one QuizManager for the entire app *//*

    private static QuizManager instance = new QuizManager();

    private SharedPreferences prefs;

    */
/* Tells whether the QuizManager has a quiz at all *//*

    private boolean hasQuiz;


    */
/* QuizManager cannot be instantiated *//*

    private QuizManager() {}

    public static final synchronized QuizManager getInstance() {
        return instance;
    }

    */
/**
     * This method is called only once, from {@link com.titan.hptrivia.util.BaseApplication}.
     *//*

    public void initialize(Context context) {
        this.prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }


*/
/*    public void storeNewQuiz(List<JSONObject> newQuestions) {

        // TODO i should store the time that i received the quiz
        SharedPreferences.Editor editor = prefs.edit();

        for (JSONObject question : newQuestions) {

        }

        //editor.putString(question key, next question);
		editor.commit();

    }*//*


    public void startQuiz(Activity activity) {
        if (hasQuiz()) {
            Intent intent = new Intent(activity.getApplicationContext(), QuizActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        }
    }

    public boolean hasQuiz() {
        return prefs.getBoolean(Keys.PREFS.QUIZ_EXISTS.name(), false);
    }


*/
/*    *//*
*/
/**
     * This will be called after a question is completed.
     *//*
*/
/*
    public void questionComplete() {

        // This will be called either when a question is answered
        // or is rendered unanswerable by a device state change.

    }*//*


    public Intent getNextQuestionIntent(Context context) {
        Intent intent = new Intent(context, QuizActivity.class);

        // tell whether it's the last question
        // intent.putExtra(Keys.PREFS.LAST_QUESTION, (number of remaining questions > 1) );

        return intent;
    }

    */
/*
            When a quiz is deleted:
                - need to set the QUIZ_EXISTS field to false
                - remove all questions

     *//*

}
*/
