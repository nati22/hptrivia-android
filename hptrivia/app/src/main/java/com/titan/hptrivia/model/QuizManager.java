package com.titan.hptrivia.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by ntessema on 6/1/14.
 */
public class QuizManager {

    // Only one QuizManager for the entire app
    private static QuizManager instance = new QuizManager();

    private SharedPreferences prefs;

    // QuizManager cannot be instantiated
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

}
