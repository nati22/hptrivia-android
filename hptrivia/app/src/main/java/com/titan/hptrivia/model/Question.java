package com.titan.hptrivia.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.titan.hptrivia.util.Keys;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ntessema on 6/1/14.
 */
public class Question implements Parcelable {

    private static final String TAG = Question.class.getSimpleName();

    //TODO change String to Answer
    private String questionText;
//    private boolean seenBefore;

    private Answer correctAnswer;
    private Answer wrongAnswer1;
    private Answer wrongAnswer2;
    private Answer wrongAnswer3;

    private Integer book = -1;
    private Integer chapter = -1;
    private boolean chExclusive = false;

    public Question(JSONObject questionJson) {
        Log.d(TAG, "Question JSON constructor");

        try {
            Question q = Question.convertJSONObjectToQuestion(questionJson);
            this.questionText = q.questionText;
            this.correctAnswer = q.correctAnswer;
            this.wrongAnswer1 = q.wrongAnswer1;
            this.wrongAnswer2 = q.wrongAnswer2;
            this.wrongAnswer3 = q.wrongAnswer3;
//            this.seenBefore = q.seenBefore;
            this.book = q.book;
            this.chapter = q.chapter;

        } catch (JSONException e) {
            Log.e(TAG, "Couldn't parse JSON string: " + questionJson.toString());
        }
    }

//    public Question(String questionText, Answer correctAnswer,
//                    Answer wrongAnswer1, Answer wrongAnswer2,
//                    Answer wrongAnswer3, boolean seenBefore) {
//
//        this.questionText = questionText;
//        this.correctAnswer = correctAnswer;
//        this.wrongAnswer1 = wrongAnswer1;
//        this.wrongAnswer2 = wrongAnswer2;
//        this.wrongAnswer3 = wrongAnswer3;
////        this.seenBefore = seenBefore;
//    }

    public Question(String questionText, Answer correctAnswer,
                    Answer wrongAnswer1, Answer wrongAnswer2,
                    Answer wrongAnswer3, int book, int chapter) {

        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.wrongAnswer1 = wrongAnswer1;
        this.wrongAnswer2 = wrongAnswer2;
        this.wrongAnswer3 = wrongAnswer3;
        this.chapter = chapter;
        this.book = book;
//        this.seenBefore = seenBefore;
    }

    /**
     * TODO This constructor is because "seenBefore" can't be implemented w/o user login.
     * @param questionText
     * @param correctAnswer
     * @param wrongAnswer1
     * @param wrongAnswer2
     * @param wrongAnswer3
     */
    public Question(String questionText, Answer correctAnswer,
                    Answer wrongAnswer1, Answer wrongAnswer2,
                    Answer wrongAnswer3) {

        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.wrongAnswer1 = wrongAnswer1;
        this.wrongAnswer2 = wrongAnswer2;
        this.wrongAnswer3 = wrongAnswer3;
//        this.seenBefore = false;
    }

    public Question(Parcel in) {
        questionText = in.readString();
//        seenBefore = in.readInt() == 1;
        correctAnswer = in.readParcelable(Answer.class.getClassLoader());
        wrongAnswer1 = in.readParcelable(Answer.class.getClassLoader());
        wrongAnswer2 = in.readParcelable(Answer.class.getClassLoader());
        wrongAnswer3 = in.readParcelable(Answer.class.getClassLoader());
        book = in.readInt();
        chapter = in.readInt();
    //    chExclusive = in.readByte() != 0;
    }

    public String getQuestionText() {
        return questionText;
    }

    public Answer getCorrectAnswer() { return correctAnswer; }

    public Answer getWrongAnswer1() { return wrongAnswer1; }

    public Answer getWrongAnswer2() { return wrongAnswer2; }

    public Answer getWrongAnswer3() { return wrongAnswer3; }

    public int getBook() {
        return book;
    }

    public int getChapter() {
        return chapter;
    }

    public boolean getIsExclusive() {
        return chExclusive;
    }

//    public boolean hasBeenSeenBefore() { return seenBefore; }

    public static Question convertJSONObjectToQuestion(JSONObject questionJSONObject) throws JSONException {

        Log.d(TAG, "convertJSONObjectToQuestion: " + questionJSONObject.toString());

        String questionText = (String) questionJSONObject.get(Keys.QUESTION_JSON.QUESTION_TEXT.name());
        Answer answer = new Answer((String)questionJSONObject.get(Keys.QUESTION_JSON.ANSWER_TEXT.name()));
        Answer wrong1 = new Answer ((String) questionJSONObject.get(Keys.QUESTION_JSON.WRONG1_TEXT.name()));
        Answer wrong2 = new Answer((String) questionJSONObject.get(Keys.QUESTION_JSON.WRONG2_TEXT.name()));
        Answer wrong3 = new Answer((String) questionJSONObject.get(Keys.QUESTION_JSON.WRONG3_TEXT.name()));
//        boolean seenBefore = (Boolean) questionJSONObject.get(Keys.QUESTION_JSON.SEEN_BEFORE.name());
        Integer book = Integer.parseInt(questionJSONObject.get(Keys.QUESTION_JSON.BOOK.name()).toString());
        Integer chapter = Integer.parseInt(questionJSONObject.get(Keys.QUESTION_JSON.CHAPTER.name()).toString());

        Log.d(TAG, "convert book " + book);
        Log.d(TAG, "convert chapter " + chapter);

        return new Question(questionText, answer, wrong1, wrong2, wrong3, book, chapter);
    }

    public static JSONObject convertQuestionToJsonObject(Question question) throws JSONException {

        JSONObject questionJSON = new JSONObject();
        questionJSON.put(Keys.QUESTION_JSON.QUESTION_TEXT.name(), question.questionText);
        questionJSON.put(Keys.QUESTION_JSON.ANSWER_TEXT.name(), question.getCorrectAnswer().getText());
        questionJSON.put(Keys.QUESTION_JSON.WRONG1_TEXT.name(), question.getWrongAnswer1().getText());
        questionJSON.put(Keys.QUESTION_JSON.WRONG2_TEXT.name(), question.getWrongAnswer2().getText());
        questionJSON.put(Keys.QUESTION_JSON.WRONG3_TEXT.name(), question.getWrongAnswer3().getText());
        questionJSON.put(Keys.QUESTION_JSON.BOOK.name(), question.getBook());
        questionJSON.put(Keys.QUESTION_JSON.CHAPTER.name(), question.getChapter());
//        questionJSON.put(Keys.QUESTION_JSON.SEEN_BEFORE.name(), question.seenBefore);
        return questionJSON;
    }

    @Override
    public String toString() {
        return String.format("Question text: \"%s\"\nCorrect answer: " +
                "\"%s\"\nWrong answer: \"%s\"\nWrong answer: \"%s\"\n" +
                "Wrong answer: \"%s\"\nBook: %d\nChapter: %d", questionText, getCorrectAnswer().getText(),
                getWrongAnswer1().getText(), getWrongAnswer2().getText(), getWrongAnswer3().getText(),
                getBook(), getChapter());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(questionText);
//        dest.writeInt(seenBefore ? 1 : 0);
        dest.writeParcelable(correctAnswer, flags);
        dest.writeParcelable(wrongAnswer1, flags);
        dest.writeParcelable(wrongAnswer2, flags);
        dest.writeParcelable(wrongAnswer3, flags);
        dest.writeInt(book);
        dest.writeInt(chapter);
    //    dest.writeByte((byte) (chExclusive ? 1 : 0));
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}
