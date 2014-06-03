package com.titan.hptrivia.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ntessema on 6/2/14.
 */
public class QuizManager {

    private static final String TAG = QuizManager.class.getSimpleName();

    /* Only one QuizManager for the entire app */
    private static QuizManager instance = new QuizManager();

    /* Tells whether the QuizManager has a quiz at all */
    private boolean hasQuiz;

    private boolean isLoaded = false;

    private Quiz quiz;

    /* The Question number that we're on in the list */
    private int questionNumber = 0;

    private List<QuestionCompletedListener> qcListeners = new ArrayList<QuestionCompletedListener>();

    public static final synchronized QuizManager getInstance() {
        return instance;
    }

    public void loadQuiz(Quiz quiz) {
        this.quiz = quiz;
        isLoaded = true;
    }

    public boolean addQuestionCompletedListener(QuestionCompletedListener listener) {
        return qcListeners.add(listener);
    }

    public boolean removeQuestionCompletedListener(QuestionCompletedListener listener) {
        return qcListeners.remove(listener);
    }

    public Question getNextQuestion() {
        Question question = new Question(null);

        if (questionNumber < quiz.size())
            question = quiz.getQuestion(++questionNumber);
        else
            Log.e(TAG, "No more questions.");

        return question;
    }

    public void questionComplete(Question question, Answer answer) {

        for (QuestionCompletedListener listener : qcListeners)
            listener.onQuestionCompleted(question, answer);
    }

}
