package com.titan.hptrivia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.titan.hptrivia.R;
import com.titan.hptrivia.model.QuizPersister;
import com.titan.hptrivia.util.Utils;


public class HomeActivity extends ActionBarActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();
//    private QuizManager quizManager;
    private QuizPersister quizPersister;

    // UI elements
    private Button buttonStartQuiz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_home);

//        quizManager = QuizManager.getInstance();
        quizPersister = QuizPersister.getInstance();

        fillQuizPersisterWithFakeQuestions();

        inflateXML();

    }

    private void fillQuizPersisterWithFakeQuestions() {

/*
        JSONObject jsonQuestion1 = new JSONObject();
        try {
            jsonQuestion1.put(Keys.JSON.QUESTION_TEXT.name(), "What is Harry's last name?");
            jsonQuestion1.put(Keys.JSON.ANSWER_TEXT.name(), "Potter");
            jsonQuestion1.put(Keys.JSON.WRONG1_TEXT.name(), "Granger");
            jsonQuestion1.put(Keys.JSON.WRONG2_TEXT.name(), "Evans");
            jsonQuestion1.put(Keys.JSON.WRONG3_TEXT.name(), "Black");
            jsonQuestion1.put(Keys.JSON.SEEN_BEFORE.name(), false);
        } catch (JSONException e) {
            Log.e(TAG, "Error constructing JSONObject.");
            Utils.makeShortToast(getApplicationContext(), "Error constructing JSONObject");
        }

        JSONObject jsonQuestion2 = new JSONObject();
        try {
            jsonQuestion2.put(Keys.JSON.QUESTION_TEXT.name(), "What is Hermione's last name?");
            jsonQuestion2.put(Keys.JSON.ANSWER_TEXT.name(), "Granger");
            jsonQuestion2.put(Keys.JSON.WRONG1_TEXT.name(), "Potter");
            jsonQuestion2.put(Keys.JSON.WRONG2_TEXT.name(), "Weasley");
            jsonQuestion2.put(Keys.JSON.WRONG3_TEXT.name(), "Jean");
            jsonQuestion2.put(Keys.JSON.SEEN_BEFORE.name(), true);
        } catch (JSONException e) {
            Log.e(TAG, "Error constructing JSONObject.");
            Utils.makeShortToast(getApplicationContext(), "Error constructing JSONObject");
        }

        JSONObject jsonQuestion3 = new JSONObject();
        try {
            jsonQuestion3.put(Keys.JSON.QUESTION_TEXT.name(), "What is Ron's last name?");
            jsonQuestion3.put(Keys.JSON.ANSWER_TEXT.name(), "Weasley");
            jsonQuestion3.put(Keys.JSON.WRONG1_TEXT.name(), "Potter");
            jsonQuestion3.put(Keys.JSON.WRONG2_TEXT.name(), "Percy");
            jsonQuestion3.put(Keys.JSON.WRONG3_TEXT.name(), "Scabbers");
            jsonQuestion3.put(Keys.JSON.SEEN_BEFORE.name(), false);
        } catch (JSONException e) {
            Log.e(TAG, "Error constructing JSONObject.");
            Utils.makeShortToast(getApplicationContext(), "Error constructing JSONObject");
        }
*/

        String result = "{" +
                "\"QUESTION\":{" +
                "\"QUESTION_TEXT\":\"What is Harry's last name?\"," +
                "\"ANSWER_TEXT\":\"Potter\"," +
                "\"WRONG1_TEXT\":\"James\"," +
                "\"WRONG2_TEXT\":\"Granger\"," +
                "\"WRONG3_TEXT\":\"Weasley\"," +
                "\"SEEN_BEFORE\":false" +
                "}," +
                "\"QUESTION\":{" +
                "\"QUESTION_TEXT\":\"What is Ron's last name?\"," +
                "\"WRONG3_TEXT\":\"Potter\"," +
                "\"WRONG1_TEXT\":\"James\"," +
                "\"WRONG2_TEXT\":\"Granger\"," +
                "\"ANSWER_TEXT\":\"Weasley\"," +
                "\"SEEN_BEFORE\":false" +
                "}," +
                "\"Question\":{" +
                "\"QUESTION_TEXT\":\"What is Hermione's last name?\"," +
                "\"WRONG2_TEXT\":\"Potter\"," +
                "\"WRONG1_TEXT\":\"James\"," +
                "\"ANSWER_TEXT\":\"Granger\"," +
                "\"WRONG3_TEXT\":\"Weasley\"," +
                "\"SEEN_BEFORE\":true" +
                "}" +
                "}";
        quizPersister.storeNewQuiz(result);

    //    quizManager.storeQuiz();
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

                Log.d(TAG, "button clicked.");

                if (quizPersister.hasQuiz()) {
                    // quizManager.startQuiz(HomeActivity.this);
                    Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                    startActivity(intent);

                } else {
                    // go get a quiz
                    // start loading quiz

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
