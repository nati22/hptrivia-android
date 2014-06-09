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
import com.titan.hptrivia.model.Question;
import com.titan.hptrivia.model.Quiz;
import com.titan.hptrivia.model.QuizPersister;

import java.util.ArrayList;

public class ResultsActivity extends ActionBarActivity {

    private final String TAG = ResultsActivity.class.getSimpleName();
    private ResultsAdapter adapter;
    private ListView listViewResults;
    private ArrayList<Question> arrayListQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // get questions from stored quiz
        Quiz quiz = QuizPersister.getInstance().getStoredQuiz();
        arrayListQuestions = new ArrayList<Question>();

        for (int i = 0; i < quiz.size(); i++) {
            arrayListQuestions.add(quiz.getQuestion(i));
        }

        // get views
        listViewResults = (ListView) findViewById(R.id.listViewResults);

        // set up list adapter
        adapter = new ResultsAdapter(getApplicationContext(), arrayListQuestions);
        listViewResults.setAdapter(adapter);
        listViewResults.setEmptyView(findViewById(android.R.id.empty));

    }


    private class ResultsAdapter extends BaseAdapter {

        private final String TAG = ResultsAdapter.class.getSimpleName();
        private Context context;
        private LayoutInflater inflater;
        private ArrayList<Question> questions;

        public ResultsAdapter(Context context, ArrayList<Question> questions) {
            Log.d(TAG, "constructor called");
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.questions = questions;
        }

        @Override
        public int getCount() {
            Log.d(TAG, "getCount called");
            return questions.size();
        }

        @Override
        public Object getItem(int position) {
            Log.d(TAG, "getItem called");
            return null;
        }

        @Override
        public long getItemId(int position) {
            Log.d(TAG, "getItemId called");
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final Question question = questions.get(position);

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.cell_question_result, null);

                // set the question number
                ((TextView) convertView.findViewById(R.id.textView_questionNumber)).setText("Question " + (position + 1));

                // set the question text
                ((TextView) convertView.findViewById(R.id.textView_question)).setText(question.getQuestionText());

                // set the correct answer
                ((TextView) convertView.findViewById(R.id.textView_correctAns)).setText(question.getCorrectAnswer().getText());

                // set the user's answer
                ((TextView) convertView.findViewById(R.id.textView_yourAnsText)).setText("need to implement this");

            } else {
                Log.d(TAG, "ListView view at index " + position + " != null");
            }

            Log.d(TAG, "getView called. got question " + question.getQuestionText());
            return convertView;
        }
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
}
