package com.titan.hptrivia.network;

import android.content.Context;
import android.util.Log;

import com.titan.hptrivia.util.PRIVATE_CONSTANTS;
import com.titan.hptrivia.util.SafeAsyncTask;
import com.titan.hptrivia.util.Utils;


/**
 * Created by ntessema on 5/30/14.
 */
abstract class BaseHttpRequest<ResultT> extends SafeAsyncTask<ResultT> {

    private static final String TAG = BaseHttpRequest.class.getSimpleName();

    static final String BASE_URL = PRIVATE_CONSTANTS.getSERVER_URL();

    protected String responseString = "";
    protected String uri = null;
    protected Context context;

    protected BaseHttpRequest(Context context, String uriSuffix) {
        super();
        this.uri = BASE_URL + uriSuffix;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        // Verify there is an Internet connection
        if (!Utils.isNetworkAvailable(context)) {
            final String errorMessage = "No internet connection detected";
            Log.e(TAG, errorMessage);
//            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
		/*	Log.e("BaseHttpRequestAsyncTask.onPreExecute",
					"No internet connection detected");*/

            // If there is no Internet connection, then don't run the AsyncTask.
            cancel(true);
        }
    }

    @Override
    protected void onException(Exception e) throws RuntimeException {
        super.onException(e);
/*		Log.e("BaseHttpRequestAsyncTask.onException", e.getMessage());
*/	}
}