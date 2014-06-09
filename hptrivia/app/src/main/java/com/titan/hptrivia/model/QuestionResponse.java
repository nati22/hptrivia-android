package com.titan.hptrivia.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ntessema on 6/8/14.
 */
public class QuestionResponse implements Parcelable {

    private Question question;
    private Answer answer;

    public QuestionResponse(Question question, Answer answer) {
        this.question = question;
        this.answer = answer;
    }

    public Question getQuestion() {
        return this.question;
    }

    public Answer getAnswer() {
        return this.answer;
    }

    public boolean isCorrect() {
        return question.getCorrectAnswer().equals(answer);
    }

    public QuestionResponse(Parcel in) {
        in.readParcelable(Question.class.getClassLoader());
        in.readParcelable(Answer.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(question, flags);
        dest.writeParcelable(answer, flags);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public QuestionResponse createFromParcel(Parcel in) {
            return new QuestionResponse(in);
        }

        public QuestionResponse[] newArray(int size) {
            return new QuestionResponse[size];
        }
    };

}
