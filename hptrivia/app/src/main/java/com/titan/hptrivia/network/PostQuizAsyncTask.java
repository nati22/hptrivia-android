package com.titan.hptrivia.network;

import android.content.Context;
import android.util.Log;

import com.titan.hptrivia.model.QuizManager;
import com.titan.hptrivia.model.QuizPersister;
import com.titan.hptrivia.network.base.BasePostRequestAsyncTask;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * Created by ntessema on 6/3/14.
 */
final class PostQuizAsyncTask extends BasePostRequestAsyncTask<String> {

    private static final String TAG = PostQuizAsyncTask.class.getSimpleName();
    private static final String uriPrefix = "/users/";
    private static final String uriSuffix = "/quiz/random";

    protected PostQuizAsyncTask(Context context, String id, List<NameValuePair> parameters) {
        super(context, uriPrefix + id + uriSuffix, parameters);
    }

    @Override
    public String call() throws Exception {
        // Execute the POST request
        super.call();

        return responseString;
    }

    @Override
    protected void onSuccess(String s) throws Exception {
        super.onSuccess(s);
        Log.v(TAG, "Storing new quiz in the QuizPersister");
        QuizPersister qp = QuizPersister.getInstance();
        qp.storeNewQuiz(s);
        Log.d(TAG, "about to load quiz");
        QuizManager.getInstance().loadQuiz(qp.getStoredQuiz());
    }
}
