package com.titan.hptrivia.util;

/**
 * Created by ntessema on 6/1/14.
 */
public class Keys {

    public enum PREFS {
        QUIZ_EXISTS, ALL_QUESTIONS
    }

    public enum QUESTION_JSON {

        QUESTION_TEXT, ANSWER_TEXT, WRONG1_TEXT, WRONG2_TEXT, WRONG3_TEXT, SEEN_BEFORE
    }

    public enum QUIZ_JSON {
        QUESTION // TODO Am I sure that Quiz JSON objects will always only have Questions as elements?
                 // TODO (contd) See Quiz.parseQuiz()
    }

}
