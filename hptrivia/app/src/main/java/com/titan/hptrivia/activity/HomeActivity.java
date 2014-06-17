package com.titan.hptrivia.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.titan.hptrivia.R;
import com.titan.hptrivia.model.NewQuizListener;
import com.titan.hptrivia.model.Quiz;
import com.titan.hptrivia.model.QuizPersister;
import com.titan.hptrivia.network.RestClientImpl;
import com.titan.hptrivia.util.CustomTextView;
import com.titan.hptrivia.util.Utils;


public class HomeActivity extends ActionBarActivity implements NewQuizListener {

    private static final String TAG = HomeActivity.class.getSimpleName();

    private QuizPersister quizPersister;
    private RestClientImpl restClient;

    // UI elements
    private Button buttonStartQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_home_new);

        quizPersister = QuizPersister.getInstance();
        restClient = new RestClientImpl(getApplicationContext());

        inflateXML();

        setTitleFont();

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void inflateXML() {

        // set title font
        TextView titleText = ((TextView) findViewById(R.id.textView_appTitle));
        titleText.setTypeface(Utils.getPotterTypeface(getApplicationContext()));
        titleText.setTextSize(80);

        buttonStartQuiz = (Button) findViewById(R.id.buttonStartQuiz);
        if (!quizPersister.hasQuiz()) buttonStartQuiz.setBackgroundColor(getResources().getColor(R.color.gray));
        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

/*                if (quizPersister.hasQuiz()) {
                    QuizManager.getInstance().loadQuiz(quizPersister.getStoredQuiz());
                    Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                    startActivity(intent);
                } else {
                    setProgressBarIndeterminateVisibility(true);
                    restClient.generateNewQuiz(5);
                }*/
                setProgressBarIndeterminateVisibility(true);
                restClient.generateNewQuiz(5);
            }
        });

/*        buttonCreateQuiz = (Button) findViewById(R.id.buttonCreateQuiz);
        buttonCreateQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (quizPersister.hasQuiz()) quizPersister.deleteStoredQuiz();

                setProgressBarIndeterminateVisibility(true);

                restClient.generateNewQuiz(5);
            }
        });

        buttonClearDB = (Button) findViewById(R.id.buttonClearDB);
        buttonClearDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizPersister.deleteStoredQuiz();
                buttonStartQuiz.setBackgroundColor(getResources().getColor(R.color.gray));
            }
        });

        buttonContinueQuiz = (Button) findViewById(R.id.buttonContinueQuiz);
        buttonContinueQuiz.setClickable(false);
        //TODO setActivated is API 11        buttonContinueQuiz.setActivated(false);
    */
    }

    private void setTitleFont() {
        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.custom_title, null);

        // if you need to customize anything else about the text, do it here.
        ((CustomTextView)v.findViewById(R.id.customTitle)).setText(this.getTitle());

        // assign the view to the actionbar
        this.getSupportActionBar().setCustomView(v);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_home_settings:
                Utils.makeShortToast(getApplicationContext(), "Home Settings");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(0, 0);
        quizPersister.addNewQuizListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        quizPersister.removeNewQuizListener(this);
    }

    @Override
    public void onNewQuizStored(Quiz quiz) {
    //    buttonStartQuiz.setActivated(true);
    //    buttonStartQuiz.setBackgroundColor(getResources().getColor(R.color.blue));
        setProgressBarIndeterminateVisibility(false);
        Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
        startActivity(intent);
    }
}
