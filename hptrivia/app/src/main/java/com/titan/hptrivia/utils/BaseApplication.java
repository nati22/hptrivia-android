package com.titan.hptrivia.utils;

import android.app.Application;

/**
 * Created by ntessema on 5/26/14.
 *
 * This is the very root of the app.
 */
public class BaseApplication extends Application {

    private static final String SERVER_URL_KEY = PRIVATE_CONSTANTS.getSERVER_URL();

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
