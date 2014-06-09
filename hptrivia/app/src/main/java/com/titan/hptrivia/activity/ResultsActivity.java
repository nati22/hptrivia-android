package com.titan.hptrivia.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
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
import com.titan.hptrivia.model.Answer;
import com.titan.hptrivia.model.Question;
import com.titan.hptrivia.model.QuestionResponse;
import com.titan.hptrivia.model.Quiz;
import com.titan.hptrivia.model.QuizManager;
import com.titan.hptrivia.model.QuizPersister;
import com.titan.hptrivia.model.QuizResponse;

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

        QuizResponse datResponse = getIntent().getParcelableExtra("doz_quiz_results_doe");
        Log.d(TAG, "response length = " + datResponse.size());

        // get questions from stored quiz
        Quiz quiz = QuizPersister.getInstance().getStoredQuiz();
        arrayListQuestionResponses = QuizManager.getInstance().getQuizResponse().getAllQuestionResponses();

        Log.d(TAG, "ResultsActivity received a questionResponse with size " + arrayListQuestionResponses.size());

//        for (int i = 0; i < quiz.size(); i++) {
//            arrayListQuestionResponses.add(quiz.getQuestion(i));
//        }

        Answer ans = quiz.getQuestion(0).getCorrectAnswer();
        Parcel parcelAns = Parcel.obtain();
        ans.writeToParcel(parcelAns, 1);

        parcelAns.setDataPosition(0);

        Answer sameAnswer = new Answer(parcelAns);
        Log.d(TAG, "Answer : " + ans.getText());
        Log.d(TAG, "Parcelled Answer : " + sameAnswer.getText());

        Question question = quiz.getQuestion(0);
        Parcel pQ = Parcel.obtain();
        question.writeToParcel(pQ, 1);

        pQ.setDataPosition(0);

        Question sameQuestion = new Question(pQ);
        Log.d(TAG, "same question = " + sameQuestion.getQuestionText());
        Log.d(TAG, "same question ans = " + sameQuestion.getCorrectAnswer().getText());

        QuestionResponse qResp = new QuestionResponse(sameQuestion, sameQuestion.getCorrectAnswer());
        Parcel pQR = Parcel.obtain();
        qResp.writeToParcel(pQR, 1);

        pQR.setDataPosition(0);

        QuestionResponse sameQR = new QuestionResponse(pQR);

        if (sameQR != null) {
            if (sameQR.getQuestion() != null) {
                Log.d(TAG, "! sameQuestionResponse q = " + sameQR.getQuestion().getQuestionText());
            } else Log.e(TAG, "sameQR question == null");

            if (sameQR.getAnswer() != null) {
                Log.d(TAG, "same QuestiongResponse...a: " + sameQR.getAnswer().getText());
            } else Log.e(TAG, "sameQR ans == null");
        } else Log.e(TAG, "sameQR == null");


        QuizResponse quizResp = new QuizResponse();
        quizResp.addNewResponse(sameQR);
        quizResp.addNewResponse(new QuestionResponse(quiz.getQuestion(4), quiz.getQuestion(4).getCorrectAnswer()));
        quizResp.addNewResponse(new QuestionResponse(quiz.getQuestion(2), quiz.getQuestion(2).getCorrectAnswer()));

        Log.d(TAG, "original quizResp size = " + quizResp.size());

        for (QuestionResponse resp : quizResp.getAllQuestionResponses())
            Log.d(TAG, "\tquestion : " + resp.getQuestion().getQuestionText());

        Parcel quizR = Parcel.obtain();

        quizResp.writeToParcel(quizR, 1);

        quizR.setDataPosition(0);

        QuizResponse sameQuizResp = new QuizResponse(quizR);

        Log.d(TAG, "same QuizResp size = " + sameQuizResp.size());

        for (QuestionResponse resp : sameQuizResp.getAllQuestionResponses()) {
            if (resp != null) {
                if (resp.getQuestion() != null) {
                    Log.d(TAG, "\tquestion : " + resp.getQuestion().getQuestionText());
                } else Log.d(TAG, "question = NULL");
            }
            else Log.d(TAG, "questionResponse : NULLL");
        }

        // get views
        listViewResults = (ListView) findViewById(R.id.listViewResults);

        // set up list adapter
        adapter = new ResultsAdapter(getApplicationContext(), arrayListQuestionResponses);
        listViewResults.setAdapter(adapter);
        listViewResults.setEmptyView(findViewById(android.R.id.empty));

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
            Log.d(TAG, "getCount called");
            return questionResponses.size();
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

            QuestionResponse questionResponse = questionResponses.get(position);

            TextView textViewQuestionNumber =  ((TextView) convertView.findViewById(R.id.textView_questionNumber));
            TextView textViewQuestionText = ((TextView) convertView.findViewById(R.id.textView_question));
            TextView textViewUserAnswer = ((TextView) convertView.findViewById(R.id.textView_yourAnsText));

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.cell_question_result, null);

                // set the question number
                textViewQuestionNumber.setText("Question " + (position + 1));

                // set the question text
                textViewQuestionText.setText(questionResponse.getQuestion().getQuestionText());

                // set the user's answer
                textViewUserAnswer.setText(questionResponse.getAnswer().getText());
                textViewUserAnswer.setBackgroundColor(getResources().getColor(questionResponse.isCorrect() ? R.color.green : R.color.red));

            } else {
                Log.d(TAG, "ListView view at index " + position + " != null");
            }

            Log.d(TAG, "getView called. got question " + questionResponse.getQuestion().getQuestionText());
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
