package com.titan.hptrivia.network;


import android.content.Context;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.List;

/**
 * Created by ntessema on 5/30/14.
 */
abstract class BasePutRequestAsyncTask<ResultT> extends
        BaseHttpRequest<ResultT> {

    protected List<NameValuePair> parameters = null;

    protected BasePutRequestAsyncTask(Context context, String uriSuffix,
                                      List<NameValuePair> parameters) {
        super(context, uriSuffix);
        this.parameters = parameters;
    }

    @Override
    public ResultT call() throws Exception {

        HttpClient client = new DefaultHttpClient();
        HttpPut putRequest = new HttpPut(uri);

        // Add the parameters in.
        if (parameters != null) {
            putRequest.setEntity(new UrlEncodedFormEntity(parameters));
        }

		Log.v("BasePutRequestAsyncTask.call", "Sending PUT request with URI: "
                + uri);
        String responseString = EntityUtils.toString(client.execute(putRequest)
                .getEntity());

        if (responseString != null) {
			Log.v("BasePutRequestAsyncTask.call", "Got HTTP result: "
					+ responseString);
        } else {
            throw new Exception("PUT request receieved null response string.");
        }

        // Save the responseString internally, for inheriting classes to use
        // (e.g. most classes will parse this string).
        this.responseString = responseString;

        return null;
    }
}
