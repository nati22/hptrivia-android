package com.titan.hptrivia.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.titan.hptrivia.activity.QuizActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ntessema on 6/1/14.
 */
public class QuizManager {

    /* Only one QuizManager for the entire app */
    private static QuizManager instance = new QuizManager();

    private SharedPreferences prefs;

    /* Tells whether the QuizManager has a quiz at all */
    private boolean hasQuiz;

    private List<Question> quizQuestions = new ArrayList<Question>();


    /* QuizManager cannot be instantiated */
    private QuizManager() {}

    public static final synchronized QuizManager getInstance() {
        return instance;
    }

    /**
     * This method is called only once, from {@link com.titan.hptrivia.util.BaseApplication}.
     */
    public void initialize(Context context) {
        this.prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void startQuiz(Context context, Activity activity) {
        hasQuiz = true; // just for now.
        if (hasQuiz) {
            activity.startActivity(new Intent(context, QuizActivity.class));
        }

    }

    public Intent getNextQuestionIntent(Context context) {
        Intent intent = new Intent(context, QuizActivity.class);

        return intent;
    }


    public boolean hasQuiz() {
        return hasQuiz;
    }
}