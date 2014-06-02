package com.titan.hptrivia.activity;

import android.app.Activity;
import android.support.v4.app.*;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.titan.hptrivia.R;
import com.titan.hptrivia.model.Question;
import com.titan.hptrivia.model.Quiz;
import com.titan.hptrivia.model.QuizPersister;
import com.titan.hptrivia.util.Keys;
import com.titan.hptrivia.util.Utils;


public class QuizActivity extends ActionBarActivity {

    private static final String TAG = QuizActivity.class.getSimpleName();

    private QuizPersister quizPersister;

    // UI Elements
    private FrameLayout frameLayout;

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

        frameLayout = (FrameLayout) findViewById(R.id.fragment_container);

        Quiz quiz = quizPersister.getStoredQuiz();
        Log.d(TAG, "QuizActivity got Question from QuizPersister...");

        displayQuizFragment(quiz.getNextQuestion());
    }

    private void displayQuizFragment(Question question) {
        QuizFragment quizFragment = new QuizFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Keys.QUIZ_JSON.QUESTION.name(), "grr");
        quizFragment.setArguments(bundle);

        Log.d(TAG, "I added " + quizFragment.getArguments().getString(Keys.QUIZ_JSON.QUESTION.name()));

        // add the fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, quizFragment);
        fragmentTransaction.commit();

    //    getSupportFragmentManager().beginTransaction().add(R.id.container, quizFragment).commit();
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

        public QuizFragment() {}


        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            Bundle bundle = getArguments();
            if (bundle == null) {
                Log.e(TAG, "bundle == null");

            } else {
                String questionString = bundle.getString(Keys.QUIZ_JSON.QUESTION.name());

                if (questionString == null)
                    Utils.makeShortToast(getActivity(), "questionString == null");
                else Utils.makeShortToast(getActivity(), "questionString: " + questionString);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_quiz, container, false);
            return rootView;
        }
    }
}
