package com.titan.hptrivia.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ntessema on 6/1/14.
 */
public class Answer implements Parcelable {

    private static final String TAG = Answer.class.getSimpleName();

    private String answerText;
    private String answerExplanation;

    public Answer(String answerText) {
        this.answerText = answerText;
        this.answerExplanation = null;
    }

    public Answer(String answerText, String answerExplanation) {
        this.answerText = answerText;
        this.answerExplanation = answerExplanation;
    }

    public String getText() {
        return answerText;
    }

    public String getExplanation() { return answerExplanation; }

    public boolean hasExplanation() {
        return answerExplanation != null;
    }

    @Override
    public boolean equals(Object o) {
        Answer otherAnswer = (Answer) o;

        // compare text
        if (!answerText.equals(otherAnswer.getText()))
            return false;

        // compare explanations
        if (answerExplanation != null && otherAnswer.getExplanation() != null) {
            return (answerExplanation.equals(otherAnswer.getExplanation()));
        } else if ((answerExplanation != null && otherAnswer.getExplanation() == null)
                || (answerExplanation == null && otherAnswer.getExplanation() != null)) {
            return false;
        }

        return true;

    }

    public Answer(Parcel in) {
        answerText = in.readString();
        answerExplanation = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(answerText);
        dest.writeString(answerExplanation);
    }

    // Android apparently needs this
    public static final Creator CREATOR = new Parcelable.Creator() {

        public Answer createFromParcel(Parcel in) {
            return new Answer(in);
        }

        public Answer[] newArray(int size) {
            return new Answer[size];
        }

    };
}
