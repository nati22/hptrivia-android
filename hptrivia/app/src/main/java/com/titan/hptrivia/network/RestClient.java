package com.titan.hptrivia.network;

import com.titan.hptrivia.model.OnUpdateStatusReceived;

/**
 * Created by ntessema on 6/3/14.
 */
public interface RestClient {

    // TODO this should generally have parameters, a QuizSpecification object perhaps?
    public void generateNewQuiz(int numQuestions);

    public void generateNewAnonymousQuiz(int numQuestions);

    public void createNewUser(String id, String firstName, String lastName);

    public void checkIn(OnUpdateStatusReceived listener);

}
