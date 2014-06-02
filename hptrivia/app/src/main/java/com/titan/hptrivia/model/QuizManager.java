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

    private static final String TAG = QuizManager.class.getSimpleName();

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

    public void startQuiz(Activity activity) {
        if (hasQuiz()) {
            Intent intent = new Intent(activity.getApplicationContext(), QuizActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        }

    }

    public Intent getNextQuestionIntent(Context context) {
        Intent intent = new Intent(context, QuizActivity.class);

        return intent;
    }


    public boolean hasQuiz() {
        // TODO return hasQuiz;
        return true;
    }
}
