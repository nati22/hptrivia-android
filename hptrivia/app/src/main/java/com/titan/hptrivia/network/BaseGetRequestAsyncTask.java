package com.titan.hptrivia.network;

import android.content.Context;
import android.util.Log;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by ntessema on 5/30/14.
 */
abstract class BaseGetRequestAsyncTask<ResultT> extends
        BaseHttpRequest<ResultT> {

    private static final String TAG = BaseGetRequestAsyncTask.class.getSimpleName();

    protected BaseGetRequestAsyncTask(Context context, String uriSuffix) {
        super(context, uriSuffix);
    }

    @Override
    public ResultT call() throws Exception {

        HttpClient client = new DefaultHttpClient();
        HttpUriRequest getRequest = new HttpGet(uri);

		Log.v("BaseGetRequestAsyncTask.call", "Sending GET request with URI: "
                + uri);

        // The actual network call
        String responseString = EntityUtils.toString(client.execute(getRequest)
                .getEntity());

        if (responseString != null) {
			Log.v("BaseGetRequestAsyncTask.call", "Got HTTP result: "
					+ responseString);
        } else {
            throw new Exception("GET request receieved null response string.");
        }

        // Save the responseString internally, for inheriting classes to use
        // (e.g. most classes will parse this string).
        this.responseString = responseString;

        return null;
    }

}