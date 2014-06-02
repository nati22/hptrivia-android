package com.titan.hptrivia.model;

/**
 * Created by ntessema on 6/2/14.
 */
public class QuizManager {

    private static final String TAG = QuizManager.class.getSimpleName();

    /* Only one QuizManager for the entire app */
    private static QuizManager instance = new QuizManager();


    /* Tells whether the QuizManager has a quiz at all */
    private boolean hasQuiz;

    public static final synchronized QuizManager getInstance() {
        return instance;
    }

    public void startQuiz(Quiz quiz) {

    }

}
