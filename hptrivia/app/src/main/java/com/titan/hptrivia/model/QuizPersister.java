package com.titan.hptrivia.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.titan.hptrivia.util.Keys;

import org.json.JSONException;

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
//        Log.d(TAG, "QuizPersister just stored a new Quiz.");
    }

    public Quiz getStoredQuiz() {

        String entireQuiz = prefs.getString(Keys.PREFS.ALL_QUESTIONS.name(), null);
        if (entireQuiz == null) {
            Log.e(TAG, "There is no stored Quiz");
            return new Quiz(null);
        }
        Log.d(TAG, "Got entireQuiz.");

        Quiz inflatedQuiz = new Quiz(null);
        try {
            inflatedQuiz = Quiz.parseQuiz(entireQuiz);
            Log.d(TAG, "inflatedQuiz has size " + inflatedQuiz.size());
//            Log.d(TAG, "Retrieved the stored Quiz.");

        } catch (JSONException e ) {
            Log.e(TAG, "Could not parse stored Quiz.");
        }

        return inflatedQuiz;
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
