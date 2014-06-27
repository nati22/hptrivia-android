package com.titan.hptrivia.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v4.content.IntentCompat;
import android.widget.Toast;

import com.titan.hptrivia.activity.HomeActivity;
import com.titan.hptrivia.network.base.BasePutRequestAsyncTask;
import com.titan.hptrivia.util.Keys;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

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

        Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();


        // get server response
        JSONObject jsonObject = new JSONObject(s);
        String id = (String) jsonObject.get(Keys.USER_JSON.id.name());
        String newUser = (String) jsonObject.get(Keys.USER_JSON._new.name());

        // if user was created successfully, let's close the login page
        // notify

        // store in SharedPrefs that user should auto-login next time
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(Keys.PREFS.AUTO_LOGIN.name(), true).commit();
        // TODO remove this from SharedPrefs when the user signs out

        // store user id in SharedPrefs
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(Keys.PREFS.GOOGLE_PLUS_ID.name(), id).commit();

        pDialog.dismiss();

        Intent intent = new Intent(context, HomeActivity.class);
        // remove MyLoginActivity from backstack
        intent.addFlags(IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);

    }

}
