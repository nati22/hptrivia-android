package com.titan.hptrivia.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.titan.hptrivia.util.Keys;
import com.titan.hptrivia.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will handle storage and retrieval of Quiz data on the device.
 * Created by ntessema on 6/1/14.
 */
public class QuizPersister {

    private static final String TAG = QuizPersister.class.getSimpleName();

    /* Only one QuizPersister for the entire app */
    private static QuizPersister instance = new QuizPersister();

    private SharedPreferences prefs;

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

    // TODO this actually stores everything (not just the quiz)
    public void storeQuizData(String everything) {

        Log.d(TAG + ".storeQuizData", "Storing Quiz: " + everything);

        // TODO i should store the time that i received the quiz
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(Keys.PREFS.ALL_QUESTIONS.name(), everything);
        editor.putBoolean(Keys.PREFS.QUIZ_EXISTS.name(), true);

        editor.commit();

        // TODO this code is trying to pass an actual Quiz object to anyone waiting for a new quiz
//        Quiz q;
//        try {
//            q = Quiz.parseQuiz(jsonString);
//        } catch (JSONException e) {
//            Log.e(TAG, "Couldn't notify the NewQuizListeners");
//            return;
//        }
//        for (NewQuizListener listener : newQuizListeners) {
//            if (q.size() != 0) { listener.onNewQuizStored(q); }
//            else { Log.e(TAG, "quiz wasn't inflated properly from the json string: \n" + jsonString); }
//        }
    }

    public Quiz getStoredQuiz() {
        Log.d(TAG, "getting stored quiz");

        String entireQuiz = prefs.getString(Keys.PREFS.ALL_QUESTIONS.name(), null);
        if (entireQuiz == null) {
            Log.e(TAG, "There is no stored Quiz");
            return new Quiz(null);
        }

        Quiz inflatedQuiz = null;
        inflatedQuiz = Utils.getQuizFromFullJson(entireQuiz);
        Log.d(TAG, "returning stored quiz");
        return inflatedQuiz;
    }

    public void deleteStoredQuiz() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(Keys.PREFS.ALL_QUESTIONS.name());
        editor.putBoolean(Keys.PREFS.QUIZ_EXISTS.name(), false);
        editor.commit();
    }

    public boolean hasQuiz() {
        return prefs.getBoolean(Keys.PREFS.QUIZ_EXISTS.name(), false);
    }


    //////////*     NewQuizListener code    *///////////
    List<NewQuizListener> newQuizListeners = new ArrayList<NewQuizListener>();

    public boolean addNewQuizListener(NewQuizListener listener) {
        return newQuizListeners.add(listener);
    }

    public boolean removeNewQuizListener(NewQuizListener listener) {
        return newQuizListeners.remove(listener);
    }


}
