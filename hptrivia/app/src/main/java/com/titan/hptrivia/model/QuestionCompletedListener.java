package com.titan.hptrivia.model;

/**
 * Created by ntessema on 6/2/14.
 */
public interface QuestionCompletedListener {

    public void onQuestionCompleted(Question question, Answer answer);
}
