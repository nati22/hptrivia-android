package com.titan.hptrivia.network;

import android.content.Context;
import android.util.Log;

import com.titan.hptrivia.network.base.BasePostRequestAsyncTask;
import com.titan.hptrivia.model.OnUpdateStatusReceived;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ntessema on 8/16/14.
 */
public class GetAppInfoAsyncTask extends BasePostRequestAsyncTask<String> {

    private static final String TAG = GetAppInfoAsyncTask.class.getSimpleName();
    private static final String url = "/checkin";

    private OnUpdateStatusReceived listener;

    protected GetAppInfoAsyncTask(Context context, OnUpdateStatusReceived listener, List<NameValuePair> params) {
        super(context, url, params);
        this.listener = listener;
    }

    @Override
    public String call() throws Exception {
        // Execute the POST request
        super.call();
        Log.d(TAG, "call()");

        return responseString;
    }

    @Override
    protected void onSuccess(String s) throws Exception {
        super.onSuccess(s);
        Log.d(TAG, "got response: " + s);

        // get boolean
        JSONObject responseJSON = new JSONObject(s.trim());

        boolean needsToUpdate = false;
        try {
            needsToUpdate = responseJSON.getBoolean("NEEDS_UPDATE");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException when parsing: " + s);
        }

        Log.d(TAG, "need update = " + needsToUpdate);
        listener.onUpdateStatusReceived(needsToUpdate);

        String lastUpdateDate = "";
        String lastUpdatedChapter = "";
        try {
            lastUpdateDate = responseJSON.getString("last_updated_date");
            lastUpdatedChapter = responseJSON.getString("last_updated_chapter");
        } catch (JSONException e) {
            Log.e(TAG, "JSONException when parsing: " + s);
        }
        listener.onLastUpdateTimeReceived(lastUpdateDate, lastUpdatedChapter);
    }




}
