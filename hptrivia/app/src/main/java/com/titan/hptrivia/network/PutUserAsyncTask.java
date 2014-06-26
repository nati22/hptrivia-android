package com.titan.hptrivia.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;

import com.titan.hptrivia.activity.HomeActivity;
import com.titan.hptrivia.network.base.BasePutRequestAsyncTask;
import com.titan.hptrivia.util.Keys;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * Created by ntessema on 6/24/14.
 */
public final class PutUserAsyncTask extends BasePutRequestAsyncTask<String> {

    private static final String TAG = PutUserAsyncTask.class.getSimpleName();
    private static final String uriSuffix = "/users/";

    // create progress dialog
    private ProgressDialog pDialog;
    // try to create a new user

    public PutUserAsyncTask(Context context, String id, List<NameValuePair> params) {
        super(context, uriSuffix + id, params);

        // initialize ProgressDialog
        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
        pDialog.setMessage("Logging in...");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog.show();
    }

    @Override
    public String call() throws Exception {

        // Execute the PUT request
        super.call();

        return responseString;
    }

    @Override
    protected void onSuccess(String s) throws Exception {
        super.onSuccess(s);
        Log.v(TAG, "Server response: " + s);

        // store in SharedPrefs that user should auto-login next time
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(Keys.PREFS.AUTO_LOGIN.name(), true).commit();
        // TODO remove this from SharedPrefs when the user signs out

        pDialog.dismiss();

        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  // remove MyLoginActivity from backstack
        context.startActivity(intent);

    }

}
