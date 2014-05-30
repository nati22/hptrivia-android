package com.titan.hptrivia.network;


import android.content.Context;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.List;

/**
 * Created by ntessema on 5/30/14.
 */
abstract class BasePostRequestAsyncTask<ResultT> extends
        BaseHttpRequest<ResultT> {

    private static final String TAG = BasePostRequestAsyncTask.class.getSimpleName();
    protected List<NameValuePair> parameters = null;

    protected BasePostRequestAsyncTask(Context context, String uriSuffix,
                                       List<NameValuePair> parameters) {
        super(context, uriSuffix);
        this.parameters = parameters;
    }

    @Override
    public ResultT call() throws Exception {

        HttpClient client = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost(uri);

        // Add the parameters in.
        if (parameters != null) {
            postRequest.setEntity(new UrlEncodedFormEntity(parameters));
        }

		Log.v("BasePostRequestAsyncTask.call",
                "Sending POST request with URI: " + uri);
        String responseString = EntityUtils.toString(client
                .execute(postRequest).getEntity());

        if (responseString != null) {
			Log.v("BasePostRequestAsyncTask.call", "Got HTTP result: "
					+ responseString);
        } else {
            throw new Exception("POST request receieved null response string.");
        }

        // Save the responseString internally, for inheriting classes to use
        // (e.g. most classes will parse this string).
        this.responseString = responseString;

        return null;
    }
}
