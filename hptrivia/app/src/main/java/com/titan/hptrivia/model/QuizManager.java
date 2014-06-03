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

    /* Tells whether the QuizManager has been loaded with a Quiz */
    private boolean isLoaded = false;
    private boolean isQuizComplete = false;
    private boolean quizHasStarted = false;

    private Quiz quiz;

    /* The Question element that we're on in the list */
    private int questionNumber = 0;

    public static final synchronized QuizManager getInstance() {
        return instance;
    }

    public void loadQuiz(Quiz quiz) {
        if (quiz == null) {
            Log.e(TAG, "QuizManager loaded with a null Quiz.");
            return;
        }
        this.quiz = quiz;
        questionNumber = 0;
        isQuizComplete = false;
        isLoaded = true;
    }

    /** Resets the QuizManager. Duhhh. */
    public void reset() {
        this.quiz = null;
        questionNumber = 0;
        isQuizComplete = false;
        quizHasStarted = false;
        isLoaded = false;
    }


    /** Returns null if there are no more questions. */
    public Question getNextQuestion() {

        if (!isLoaded()) {
            Log.e(TAG, "QuizManager isn't loaded.");
            return null;
        }

        quizHasStarted = true;

        if (questionNumber < quiz.size()) {
            return quiz.getQuestion(questionNumber++);
        } else {
            Log.e(TAG, "This shouldn't be happeining. getNextQuestion called when there are no more questions.");
            return null;
        }
    }


    /** Tells whether the QuizManager has been loaded with a Quiz */
    public boolean isLoaded() {
        return isLoaded;
    }

    public boolean hasStarted() {
        return quizHasStarted;
    }

    public int getNumberOfQuestions() {
        if (isLoaded())
            return quiz.size();
        else {
            Log.e(TAG, "QuizManager isn't loaded.");
            return -1;
        }
    }

    public boolean hasMoreQuestions() {
        if (isLoaded())
            return !isQuizComplete;
        else {
            Log.e(TAG, "QuizManager isn't loaded.");
            return false;
        }
    }

    ///////*  QuestionCompletionListener code  *////////

    private List<QuestionCompletionListener> qcListeners = new ArrayList<QuestionCompletionListener>();

    public boolean addQuestionCompletedListener(QuestionCompletionListener listener) {
        return qcListeners.add(listener);
    }

    public boolean removeQuestionCompletedListener(QuestionCompletionListener listener) {
        return qcListeners.remove(listener);
    }

    public void questionComplete(Question question, Answer answer) {

        if (isQuizComplete) Log.e(TAG, "THIS SHOULDN'T HAPPEN! questionComplete shouldn't be called on a complete Quiz.");

        // store the data about the User's feedback
        Log.d(TAG, "\nQuestion " + (questionNumber - 1) + ": " + question.getQuestionText()
                + "\nCorrect answer:" + question.getCorrectAnswer().getText()
                + "\nUser answer: " + answer.getText()
                + "\nCorrect: " + (answer.equals(question.getCorrectAnswer())));

        // check if we're done
        isQuizComplete = (questionNumber == quiz.size());

        for (QuestionCompletionListener listener : qcListeners)
            listener.onQuestionCompleted(question, answer);
    }

}
