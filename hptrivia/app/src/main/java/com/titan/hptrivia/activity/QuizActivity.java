package com.titan.hptrivia.activity;

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
import com.titan.hptrivia.util.Keys;

import org.json.JSONException;


public class QuizActivity extends ActionBarActivity implements QuestionCompletionListener {

    private static final String TAG = QuizActivity.class.getSimpleName();

    private QuizManager quizManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        quizManager = QuizManager.getInstance();

        Log.d(TAG, "QuizActivity started with a quiz of size " + quizManager.getNumberOfQuestions());

        // Kickstarts start quiz
        if (!quizManager.hasStarted())
            displayNextQuestion(quizManager.getNextQuestion());
    }

    private void displayNextQuestion(Question question) {
        QuizFragment quizFragment = new QuizFragment();
        Bundle bundle = new Bundle();
        try {
            bundle.putString(Keys.QUIZ_JSON.QUESTION.name(), Question.convertQuestionToJsonObject(question).toString());
    //        bundle.putBoolean(Keys.PREFS.LAST_QUESTION.name(), questionNumber == quiz.size() ? true : false);
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


//    private void displayNextQuestion() {
//
//        if (questionNumber == quiz.size()) {
//            Utils.makeShortToast(getApplicationContext(), "Quiz complete");
//            finish(); return;
//        }
//
//        QuizFragment quizFragment = new QuizFragment();
//        Bundle bundle = new Bundle();
//        try {
//            bundle.putString(Keys.QUIZ_JSON.QUESTION.name(), Question.convertQuestionToJsonObject(quiz.getQuestion(questionNumber++)).toString());
//            bundle.putBoolean(Keys.PREFS.LAST_QUESTION.name(), questionNumber == quiz.size() ? true : false);
//            quizFragment.setArguments(bundle);
//        } catch (JSONException e) {
//            Log.e(TAG, "Couldn't convert Question object to JSONString.");
//            return;
//        }
//
//        // add the fragment
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);   // TODO remove unnecessary anim files
//        fragmentTransaction.add(R.id.fragment_container, quizFragment);
//        fragmentTransaction.commit();
//
//    }

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

    @Override
    public void onQuestionCompleted(Question question, Answer answer) {

        Log.d(TAG, "QuizActivity detected a question was completed.");
        Log.d(TAG, "User selected answer " + answer.getText());
        if (quizManager.hasMoreQuestions()) {
            displayNextQuestion(quizManager.getNextQuestion());
        } else { // last question was completed
            Log.d(TAG, "Quiz complete.");
            quizManager.reset();
            finish();
        }
    }

}
