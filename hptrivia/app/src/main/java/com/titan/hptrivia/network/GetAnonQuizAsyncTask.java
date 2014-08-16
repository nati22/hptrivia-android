package com.titan.hptrivia.network;

import android.content.Context;
import android.util.Log;

import com.titan.hptrivia.model.QuizManager;
import com.titan.hptrivia.model.QuizPersister;
import com.titan.hptrivia.network.base.BaseGetRequestAsyncTask;

/**
 * Created by ntessema on 6/3/14.
 */
final class GetAnonQuizAsyncTask extends BaseGetRequestAsyncTask<String> {

    private static final String TAG = GetAnonQuizAsyncTask.class.getSimpleName();
    private static final String url = "/public/quiz/random";

    protected GetAnonQuizAsyncTask(Context context) {
        super(context, url);
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
