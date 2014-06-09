package com.titan.hptrivia.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by ntessema on 6/8/14.
 */
public class QuizResponse implements Parcelable {


    private ArrayList<QuestionResponse> questionResponses;

    public QuizResponse() {
        questionResponses = new ArrayList<QuestionResponse>();
    }

    public void addNewResponse(QuestionResponse response) {
        this.questionResponses.add(response);
    }

    public ArrayList<QuestionResponse> getAllQuestionResponses() {
        return this.questionResponses;
    }

    public int size() {
        return questionResponses.size();
    }

    public void clear() {
        questionResponses.clear();
    }

    public QuizResponse(Parcel in) {
        questionResponses = new ArrayList<QuestionResponse>();
        in.readTypedList(questionResponses, QuestionResponse.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(questionResponses);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public QuizResponse createFromParcel(Parcel in) {
            return new QuizResponse(in);
        }

        public QuizResponse[] newArray(int size) {
            return new QuizResponse[size];
        }
    };
}
