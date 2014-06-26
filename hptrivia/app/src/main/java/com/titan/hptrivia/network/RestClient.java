package com.titan.hptrivia.network;

/**
 * Created by ntessema on 6/3/14.
 */
public interface RestClient {

    // TODO this should generally have parameters, a QuizSpecification object perhaps?
    public void generateNewQuiz(int numQuestions);

    public void createNewUser(String id, String firstName, String lastName);

}
