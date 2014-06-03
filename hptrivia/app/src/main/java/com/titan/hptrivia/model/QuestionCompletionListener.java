package com.titan.hptrivia.model;

/**
 * Created by ntessema on 6/2/14.
 */
public interface QuestionCompletionListener {

    public void onQuestionCompleted(Question question, Answer answer);
}
