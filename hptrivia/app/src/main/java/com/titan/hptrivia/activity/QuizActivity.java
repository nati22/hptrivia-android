package com.titan.hptrivia.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.titan.hptrivia.R;
import com.titan.hptrivia.model.Question;
import com.titan.hptrivia.model.Quiz;
import com.titan.hptrivia.model.QuizPersister;
import com.titan.hptrivia.util.Keys;
import com.titan.hptrivia.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;


public class QuizActivity extends ActionBarActivity {

    private static final String TAG = QuizActivity.class.getSimpleName();

    private QuizPersister quizPersister;
    private Quiz quiz;
    private int questionNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        quizPersister = QuizPersister.getInstance();
        quiz = quizPersister.getStoredQuiz();
        Log.d(TAG, "quizPersister had a stored quiz of size " + quiz.size());

        // start quiz
    //    displayQuizFragment(quiz.getQuestion(questionNumber));
        displayNextQuestion();
    }

    private void displayNextQuestion() {

        if (questionNumber == quiz.size()) {
            Utils.makeShortToast(getApplicationContext(), "Quiz complete");
            finish(); return;
        }

        QuizFragment quizFragment = new QuizFragment();
        Bundle bundle = new Bundle();
        try {
            bundle.putString(Keys.QUIZ_JSON.QUESTION.name(), Question.convertQuestionToJsonObject(quiz.getQuestion(questionNumber++)).toString());
            bundle.putBoolean(Keys.PREFS.LAST_QUESTION.name(), questionNumber == quiz.size() ? true : false);
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

        private Question question;
        private boolean isLastQuestion = false;

        // UI elements
        private TextView textView_questionInfo;
        private TextView textView_questionText;
        private Button button_correct;
        private Button button_wrong1;
        private Button button_wrong2;
        private Button button_wrong3;

        public QuizFragment() {}

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);

            Bundle bundle = getArguments();
            if (bundle == null) {
                Log.e(TAG, "bundle == null");
            } else {
                String questionString = bundle.getString(Keys.QUIZ_JSON.QUESTION.name());
                isLastQuestion = bundle.getBoolean(Keys.PREFS.LAST_QUESTION.name());
                try {
                    question = Question.convertJSONObjectToQuestion(new JSONObject(questionString));
                } catch (JSONException e) {
                    Log.e(TAG, "Can't parse the Question string: " + questionString);
                    return;
                }
            }
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            textView_questionInfo = (TextView) view.findViewById(R.id.textView_questionInfo);
            textView_questionText = (TextView) view.findViewById(R.id.textView_questionText);

            // shuffle buttons
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            arrayList.add((Integer) R.id.button_answer1);
            arrayList.add((Integer) R.id.button_answer2);
            arrayList.add((Integer) R.id.button_answer3);
            arrayList.add((Integer) R.id.button_answer4);
            Collections.shuffle(arrayList);

            // assign buttons
            button_correct = (Button) view.findViewById(arrayList.remove(0));
            button_wrong1 = (Button) view.findViewById(arrayList.remove(0));
            button_wrong2 = (Button) view.findViewById(arrayList.remove(0));
            button_wrong3 = (Button) view.findViewById(arrayList.remove(0));

            // set values
            textView_questionText.setText(question.getQuestionText());
            button_correct.setText(question.getCorrectAnswer().getText());
            button_wrong1.setText(question.getWrongAnswer1().getText());
            button_wrong2.setText(question.getWrongAnswer2().getText());
            button_wrong3.setText(question.getWrongAnswer3().getText());
            // TODO add Book name, Movie name, etc

            setOnClickListeners();
        }

        private void setOnClickListeners() {
            button_correct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO store "correctness"
                    Utils.makeShortToast(getActivity().getApplicationContext(), "correct answer");
                    ((QuizActivity) getActivity()).displayNextQuestion();
                }
            });

            button_wrong1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO store "incorrectness"
                    Utils.makeShortToast(getActivity().getApplicationContext(), "wrong answer");
                    ((QuizActivity) getActivity()).displayNextQuestion();
                }
            });

            button_wrong2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO store "incorrectness"
                    Utils.makeShortToast(getActivity().getApplicationContext(), "wrong answer");
                    ((QuizActivity) getActivity()).displayNextQuestion();
                }
            });

            button_wrong3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO store "incorrectness"
                    Utils.makeShortToast(getActivity().getApplicationContext(), "wrong answer");
                    ((QuizActivity) getActivity()).displayNextQuestion();
                }
            });
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_quiz, container, false);
            return rootView;
        }
    }
}
