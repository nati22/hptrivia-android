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

    private QuizResponse quizResponse;
    private Quiz quiz;

    /* Tells whether the QuizManager has been loaded with a Quiz */
    private boolean isLoaded = false;
    private boolean isQuizComplete = false;
    private boolean quizHasStarted = false;


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
        quizHasStarted = false;
        questionNumber = 0;
        isQuizComplete = false;
        isLoaded = true;
        quizResponse = new QuizResponse();
    }

    /** Resets the QuizManager. Duhhh. */
    public void reset() {
        this.quiz = null;
        questionNumber = 0;
        isQuizComplete = false;
        quizHasStarted = false;
        isLoaded = false;
        quizResponse.clear();
    }


    /** Returns null if there are no more questions. */
    public Question getNextQuestion() {

        Log.d(TAG, "QuizManager has " + quiz.size() + " elements");
        Log.d(TAG, "we're on question # " + questionNumber);

        if (!isLoaded()) {
            Log.e(TAG, "QuizManager isn't loaded.");
            return null;
        }

        Log.d(TAG, "next question is " + quiz.getQuestion(questionNumber));

        quizHasStarted = true;

        if (questionNumber < quiz.size()) {
            return quiz.getQuestion(questionNumber++);
        } else {
            Log.e(TAG, "This shouldn't be happening. getNextQuestion called when there are no more questions.");
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

    public QuizResponse getQuizResponse() {
        return this.quizResponse;
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
//        Log.d(TAG, "\nQuestion " + (questionNumber) + ": " + question.getQuestionText()
//                + "\n\tUser answer: " + answer.getText()  + ((answer.equals(question.getCorrectAnswer())) ? "Correct" : "Wrong"));

        quizResponse.addNewResponse(new QuestionResponse(question, answer));
//        Log.d(TAG, "quizResponse has size " + quizResponse.size());

        // check if we're done
        isQuizComplete = (questionNumber == quiz.size());

        for (QuestionCompletionListener listener : qcListeners)
            listener.onQuestionCompleted(question, answer);
    }

}
