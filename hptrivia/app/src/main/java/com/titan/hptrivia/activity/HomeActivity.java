package com.titan.hptrivia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.*;
import android.view.*;
import android.widget.*;

import com.titan.hptrivia.R;
import com.titan.hptrivia.model.QuizPersister;
import com.titan.hptrivia.util.*;


public class HomeActivity extends ActionBarActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();

    /** Manages the storage and retrieval of Quiz data. */
    private QuizPersister quizPersister;

    // UI elements
    private Button buttonStartQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_home);

        quizPersister = QuizPersister.getInstance();
        makeSureQuizPersisterHasQuestions();

        inflateXML();

        setTitleFont();
    }

    private void makeSureQuizPersisterHasQuestions() {

        if (quizPersister.hasQuiz()) return;

        String result = "{" +
                "\"QUESTION1\":{" +
                "\"QUESTION_TEXT\":\"What is Harry's last name?\"," +
                "\"ANSWER_TEXT\":\"Potter\"," +
                "\"WRONG1_TEXT\":\"James\"," +
                "\"WRONG2_TEXT\":\"Granger\"," +
                "\"WRONG3_TEXT\":\"Weasley\"," +
                "\"SEEN_BEFORE\":false" +
                "}," +
                "\"QUESTION2\":{" +
                "\"QUESTION_TEXT\":\"What is Ron's last name?\"," +
                "\"WRONG3_TEXT\":\"Potter\"," +
                "\"WRONG1_TEXT\":\"James\"," +
                "\"WRONG2_TEXT\":\"Granger\"," +
                "\"ANSWER_TEXT\":\"Weasley\"," +
                "\"SEEN_BEFORE\":false" +
                "}," +
                "\"QUESTION3\":{" +
                "\"QUESTION_TEXT\":\"What is Hermione's last name?\"," +
                "\"WRONG2_TEXT\":\"Potter\"," +
                "\"WRONG1_TEXT\":\"James\"," +
                "\"ANSWER_TEXT\":\"Granger\"," +
                "\"WRONG3_TEXT\":\"Weasley\"," +
                "\"SEEN_BEFORE\":true" +
                "}" +
                "}";
        quizPersister.storeNewQuiz(result);
    }

    private void inflateXML() {
        // set title font
        TextView titleText = ((TextView) findViewById(R.id.textView_appTitle));
        titleText.setTypeface(Utils.getPotterTypeface(getApplicationContext()));
        titleText.setTextSize(80);

        buttonStartQuiz = (Button) findViewById(R.id.buttonStartQuiz);
        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (quizPersister.hasQuiz()) {
                    // quizManager.startQuiz(HomeActivity.this);
                    Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                    startActivity(intent);

                } else {
                        // order should be:
                            // start loading spinner
                            // send request to server
                            // stop spinner on response
                    setProgressBarIndeterminateVisibility(true);
    //                quizManager.startQuiz(HomeActivity.this);
                }
            }
        });
    }

    private void setTitleFont() {
        SpannableString s = new SpannableString(getResources().getString(R.string.app_name));
        s.setSpan(new TypefaceSpan(this, "HARRYP.TTF"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Update the action bar title with the TypefaceSpan instance
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(s);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

/*    public void startQuizButtonClicked(View view) {
        quizManager.startQuiz(this);
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(0, 0);
    }
}
