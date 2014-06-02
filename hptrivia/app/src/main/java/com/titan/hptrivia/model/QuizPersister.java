package com.titan.hptrivia.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.titan.hptrivia.util.Keys;

/**
 * Created by ntessema on 6/1/14.
 */
public class QuizPersister {

    private static final String TAG = QuizPersister.class.getSimpleName();

    /* Only one QuizPersister for the entire app */
    private static QuizPersister instance = new QuizPersister();

    private SharedPreferences prefs;

    /* Tells whether the QuizManager has a quiz at all */
    private boolean hasQuiz;

    /* QuizPersister cannot be instantiated */
    private QuizPersister() {}

    public static final synchronized QuizPersister getInstance() {
        return instance;
    }

    /**
     * This method is called only once, from {@link com.titan.hptrivia.util.BaseApplication}.
     */
    public void initialize(Context context) {
        this.prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void storeNewQuiz(String jsonString) {
        // TODO i should store the time that i received the quiz
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(Keys.PREFS.ALL_QUESTIONS.name(), jsonString);
        editor.putBoolean(Keys.PREFS.QUIZ_EXISTS.name(), true);

        editor.commit();
        Log.d(TAG, "QuizPersister just stored a new Quiz.");
    }

    public void getStoredQuiz() {

        String entireQuiz = prefs.getString(Keys.PREFS.ALL_QUESTIONS.name(), null);
        Log.i(TAG, "entireQuiz: " + entireQuiz + "");
    }

    public void deleteStoredQuiz() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(Keys.PREFS.ALL_QUESTIONS.name());
        editor.putBoolean(Keys.PREFS.QUIZ_EXISTS.name(), true);
        editor.commit();
    }

    public boolean hasQuiz() {
        return prefs.getBoolean(Keys.PREFS.QUIZ_EXISTS.name(), false);
    }


}
