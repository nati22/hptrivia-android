package com.titan.hptrivia.network;

import android.content.Context;

import com.titan.hptrivia.util.Keys;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ntessema on 6/3/14.
 */
public final class RestClientImpl implements RestClient {

    private static final String TAG = RestClientImpl.class.getSimpleName();

    private Context context;

    public RestClientImpl(Context context) {
        this.context = context;
    }

    @Override
    public void generateNewQuiz(int numQuestions) {

        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair(Keys.REST_API.NUM_QUESTIONS.name(), numQuestions + ""));
        new NewQuizAsyncTask(context, parameters).execute();

    }
}
