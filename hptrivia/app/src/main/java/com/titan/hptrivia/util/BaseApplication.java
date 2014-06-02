package com.titan.hptrivia.util;

import android.app.Application;

import com.titan.hptrivia.model.QuizManager;
import com.titan.hptrivia.model.QuizPersister;

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

        // Initialize the QuizManager
        QuizManager qMgr = QuizManager.getInstance();
        qMgr.initialize(getApplicationContext());

        //
        QuizPersister quizPersister = QuizPersister.getInstance();
        quizPersister.initialize(getApplicationContext());

    }
}
