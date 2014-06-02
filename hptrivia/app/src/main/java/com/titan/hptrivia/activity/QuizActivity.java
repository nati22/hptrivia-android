package com.titan.hptrivia.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.titan.hptrivia.R;
import com.titan.hptrivia.model.Quiz;
import com.titan.hptrivia.model.QuizPersister;


public class QuizActivity extends ActionBarActivity {

    private static final String TAG = QuizActivity.class.getSimpleName();

    private QuizPersister quizPersister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Log.d(TAG, "onCreate called");
    /*    if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new QuizFragment())
                    .commit();
        }*/

        quizPersister = QuizPersister.getInstance();

        Quiz quiz = quizPersister.getStoredQuiz();
        Log.d(TAG, "QuizActivity got Question from QuizPersister...");
        getResources().
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class QuizFragment extends Fragment {

        public QuizFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_quiz, container, false);
            return rootView;
        }
    }
}
