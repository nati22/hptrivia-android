package com.titan.hptrivia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.titan.hptrivia.R;
import com.titan.hptrivia.model.Answer;
import com.titan.hptrivia.model.Question;
import com.titan.hptrivia.model.QuestionCompletionListener;
import com.titan.hptrivia.model.QuizManager;
import com.titan.hptrivia.model.QuizPersister;
import com.titan.hptrivia.util.Keys;
import com.titan.hptrivia.util.Utils;

import org.json.JSONException;


public class QuizActivity extends ActionBarActivity implements QuestionCompletionListener {

    private static final String TAG = QuizActivity.class.getSimpleName();

    private QuizManager quizManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        quizManager = QuizManager.getInstance();

        // TODO ////////////////////////////////////////////////
            // TODO -  there appears to be a bug here when the back button is pressed from
            // TODO -  ResultsActivity. find out why QuizActivity is being called instead of
            // TODO -  having the app go back to HomeActivity
        // TODO ////////////////////////////////////////////////
        // Kickstarts start quiz
        if (!quizManager.hasStarted())
            displayNextQuestion(quizManager.getNextQuestion());
    }

    private void displayNextQuestion(Question question) {

        Log.d(TAG, "displayNextQuestion called with " + question);

        QuizFragment quizFragment = new QuizFragment();
        Bundle bundle = new Bundle();
        try {
            bundle.putString(Keys.QUIZ_JSON.QUESTION.name(), Question.convertQuestionToJsonObject(question).toString());
            quizFragment.setArguments(bundle);
        } catch (JSONException e) {
            Log.e(TAG, "Couldn't convert Question object to JSONString.");
            return;
        }

        // add the fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);   // TODO remove unnecessary anim files
        fragmentTransaction.add(R.id.fragment_container, quizFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        quizManager.addQuestionCompletedListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        quizManager.removeQuestionCompletedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    //    getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_quiz_settings:
                Utils.makeShortToast(getApplicationContext(), "Settings");
                return true;
            case R.id.action_quiz_report:
                Utils.makeShortToast(getApplicationContext(), "Not implemented yet :(");
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onQuestionCompleted(Question question, Answer answer) {

        Log.d(TAG, "User selected answer " + answer.getText());
        if (quizManager.hasMoreQuestions()) {
            displayNextQuestion(quizManager.getNextQuestion());
        } else { // last question was completed
            Log.d(TAG, "Quiz complete.");
            Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
            intent.putExtra(Keys.KEY_QUIZ_RESPONSE, quizManager.getQuizResponse());
            startActivity(intent);
            quizManager.reset();
            QuizPersister.getInstance().deleteStoredQuiz();
            // TODO deleting the local quiz data should happen in onSuccess of an AsyncTask that returns the QuizResult to the server
            finish();
        }
    }

}
