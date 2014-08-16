package com.titan.hptrivia.network;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.titan.hptrivia.util.Keys;
import com.titan.hptrivia.model.OnUpdateStatusReceived;
import com.titan.hptrivia.util.Utils;

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

        String id = PreferenceManager.getDefaultSharedPreferences(context).getString(Keys.PREFS.GOOGLE_PLUS_ID.name(), "");

        // Sanity check
        if (id.length() == 0) {
            Toast.makeText(context, "There was an error creating your quiz.", Toast.LENGTH_SHORT).show();
            // take user to login screen
            Log.d(TAG, "Shared Prefs has google+ id: \"" + id + "\"");
            return;
        }

        new PostQuizAuthenticatedAsyncTask(context, id, parameters).execute();
    }

    @Override
    public void generateNewAnonymousQuiz(int numQuestions) {

        new GetAnonQuizAsyncTask(context).execute();
    }

    public void createNewUser(String id, String firstName, String lastName) {

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(Keys.REST_API.fn.name(), firstName));
        params.add(new BasicNameValuePair(Keys.REST_API.ln.name(), lastName));

        if (id.length() == 0) {
            Toast.makeText(context, "There was an error setting up your account.", Toast.LENGTH_SHORT).show();
            // take user to login screen
            Log.d(TAG, "google+ returned id: \"" + id + "\"");
            return;
        }

        new PutUserAsyncTask(context, id, params).execute();
    }

    public void checkIn(OnUpdateStatusReceived listener) {

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("vcode", Utils.getVersionCode(context) + ""));
        new GetAppInfoAsyncTask(context, listener, params).execute();
    }
}
