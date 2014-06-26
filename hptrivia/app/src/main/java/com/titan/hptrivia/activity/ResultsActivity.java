package com.titan.hptrivia.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.titan.hptrivia.R;
import com.titan.hptrivia.model.QuestionResponse;
import com.titan.hptrivia.model.QuizResponse;
import com.titan.hptrivia.util.Keys;

import java.util.ArrayList;

public class ResultsActivity extends ActionBarActivity {

    private final String TAG = ResultsActivity.class.getSimpleName();
    private ResultsAdapter adapter;
    private ListView listViewResults;
    private ArrayList<QuestionResponse> arrayListQuestionResponses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // get questions from stored quiz
        QuizResponse datResponse = getIntent().getParcelableExtra(Keys.KEY_QUIZ_RESPONSE);

        // get views
        listViewResults = (ListView) findViewById(R.id.listViewResults);

        // set ListView header (title)
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.cell_results_listview_header, null);
        listViewResults.addHeaderView(v);

        // set up list adapter
        adapter = new ResultsAdapter(getApplicationContext(), datResponse.getAllQuestionResponses());
        listViewResults.setAdapter(adapter);
        listViewResults.setEmptyView(findViewById(android.R.id.empty));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.results, menu);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
    }

    private class ResultsAdapter extends BaseAdapter {

        private final String TAG = ResultsAdapter.class.getSimpleName();
        private Context context;
        private LayoutInflater inflater;
        private ArrayList<QuestionResponse> questionResponses;

        public ResultsAdapter(Context context, ArrayList<QuestionResponse> questionResponses) {
            Log.d(TAG, "constructor called");
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.questionResponses = questionResponses;
        }

        @Override
        public int getCount() {
            return questionResponses.size();
        }

        @Override
        public Object getItem(int position) {
            return questionResponses.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Log.d(TAG, "position: " + position);

            QuestionResponse questionResponse = questionResponses.get(position);

            if (convertView == null) {

                convertView = inflater.inflate(R.layout.cell_question_result, null);

                TextView textViewQuestionNumber =  ((TextView) convertView.findViewById(R.id.textView_questionNumber));
                TextView textViewQuestionText = ((TextView) convertView.findViewById(R.id.textView_question));
                TextView textViewUserAnswer = ((TextView) convertView.findViewById(R.id.textView_yourAnsText));

                // set the question number
                textViewQuestionNumber.setText("Question " + (position + 1));

                // set the question text
                textViewQuestionText.setText(questionResponse.getQuestion().getQuestionText());

                // set the user's answer
                textViewUserAnswer.setText(questionResponse.getAnswer().getText());
                textViewUserAnswer.setBackgroundColor(getResources().getColor(questionResponse.isCorrect() ? R.color.green : R.color.red));

            } else {
            //    Log.d(TAG, "ListView view at index " + position + " != null");
            }

            return convertView;
        }
    }
}
