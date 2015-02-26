package com.titan.hptrivia.util;

/**
 * Created by ntessema on 6/1/14.
 */
public class Keys {

    public enum PREFS {
        QUIZ_EXISTS, ALL_QUESTIONS, LAST_QUESTION, AUTO_LOGIN, GOOGLE_PLUS_ID, GOOGLE_IMG_URL, GOOGLE_IMG_LOCAL_PATH, GOOGLE_IMG_EXISTS_LOCALLY
    }

    public enum QUESTION_JSON {
        QUESTION_TEXT, ANSWER_TEXT, WRONG1_TEXT, WRONG2_TEXT, WRONG3_TEXT, SEEN_BEFORE, id, BOOK, CHAPTER, CHEX
    }

    public enum QUIZ_JSON {
        QUESTION // TODO Am I sure that Quiz JSON objects will always only have Questions as elements?
                 // TODO (contd) See Quiz.parseQuiz()
    }

    public enum USER_JSON {
        _new, id
    }

    public enum REST_API {
        NUM_QUESTIONS, fn, ln
    }

    public static final String KEY_QUIZ_RESPONSE = "quiz_response";

}
