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
    private Context context;

    protected GetAnonQuizAsyncTask(Context context) {
        super(context, url);
        this.context = context;
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
        Log.e(TAG, "**************Storing new quiz locally************");

        // Get quiz from content
//        JSONObject responseJSON = new JSONObject(s.trim());        // TODO should i trim? from (http://stackoverflow.com/questions/9151619/java-iterate-over-jsonobject)
//        String quizString = responseJSON.getString("content").replace("\\\"", "\"");

//        Log.d(TAG, "quiz content = " + quizString);

        Log.d(TAG, "persisting quiz to QuizPersister");

        QuizPersister qp = QuizPersister.getInstance();
        qp.storeQuizData(s);
        Log.d(TAG, "about to load quiz");
        QuizManager.getInstance().loadQuiz(qp.getStoredQuiz());
    }
}
