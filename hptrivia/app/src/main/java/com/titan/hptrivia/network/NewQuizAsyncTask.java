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
final class NewQuizAsyncTask extends BasePostRequestAsyncTask<String> {

    private static final String TAG = NewQuizAsyncTask.class.getSimpleName();
    private static final String uriSuffix = "/questionset";

    protected NewQuizAsyncTask(Context context, List<NameValuePair> parameters) {
        super(context, uriSuffix, parameters);
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
