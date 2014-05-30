package com.titan.hptrivia.utils;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import java.util.Map;

/**
 * Created by ntessema on 5/26/14.
 *
 * This is the very root of the app.
 */
public class BaseApplication extends Application {

    private static final String SERVER_URL_KEY = "SERVER_URL";
    private static final String TAG = BaseApplication.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();

        // get environment variables
        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            System.out.format("%s=%s%n",
                    envName,
                    env.get(envName));
            Log.e(TAG, "envName = " + envName);
        }

        Log.e(TAG, "PATH = " + env.get("PATH"));

        String url = System.getenv(SERVER_URL_KEY);
        Constants.setSERVER_URL(url);
        Log.i(TAG, "url = " + url);
        Toast.makeText(getBaseContext(), System.getenv(SERVER_URL_KEY), Toast.LENGTH_LONG).show();


    }
}
