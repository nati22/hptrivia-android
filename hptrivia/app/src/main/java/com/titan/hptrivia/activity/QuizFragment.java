package com.titan.hptrivia.activity;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.titan.hptrivia.R;
import com.titan.hptrivia.model.Question;
import com.titan.hptrivia.model.QuizManager;
import com.titan.hptrivia.util.Keys;
import com.titan.hptrivia.util.RegularBoldTextView;
import com.titan.hptrivia.util.RegularButton;
import com.titan.hptrivia.util.RegularTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ntessema on 6/2/14.
 */
public class QuizFragment extends Fragment {

    private static final String TAG = QuizFragment.class.getSimpleName();

    private QuizActivity activity;
    private QuizManager quizManager;

    private Question question;

    // UI elements
    private RegularTextView textView_questionInfo;
    private RegularBoldTextView textView_questionText;
    private RegularButton button_correct;
    private RegularButton button_wrong1;
    private RegularButton button_wrong2;
    private RegularButton button_wrong3;
    //    private ProgressBar progressBar;

    //    private CountDownTimer timer;

    public QuizFragment() {}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.activity = (QuizActivity) activity;
        quizManager = QuizManager.getInstance();

        Bundle bundle = getArguments();
        if (bundle == null) {
            Log.e(TAG, "bundle == null");
        } else {
            String questionString = bundle.getString(Keys.QUIZ_JSON.QUESTION.name());
    //        isLastQuestion = bundle.getBoolean(Keys.PREFS.LAST_QUESTION.name());
            try {
                question = Question.convertJSONObjectToQuestion(new JSONObject(questionString));
            } catch (JSONException e) {
                Log.e(TAG, "Can't parse the Question string: " + questionString + ".\n Error: " + e);
                return;
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView_questionInfo = (RegularTextView) view.findViewById(R.id.textView_questionInfo);
        textView_questionText = (RegularBoldTextView) view.findViewById(R.id.textView_questionText);
        //    progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        //    progressBar.setMax(10);

        // shuffle buttons
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add((Integer) R.id.button_answer1);
        arrayList.add((Integer) R.id.button_answer2);
        arrayList.add((Integer) R.id.button_answer3);
        arrayList.add((Integer) R.id.button_answer4);
        Collections.shuffle(arrayList);

        // assign buttons
        button_correct = (RegularButton) view.findViewById(arrayList.remove(0));
        button_wrong1 = (RegularButton) view.findViewById(arrayList.remove(0));
        button_wrong2 = (RegularButton) view.findViewById(arrayList.remove(0));
        button_wrong3 = (RegularButton) view.findViewById(arrayList.remove(0));

        if (question == null) {
            Log.d(TAG, "question == null");
        }

        // set values
        textView_questionText.setText(question.getQuestionText());
        button_correct.setText(question.getCorrectAnswer().getText());
        button_wrong1.setText(question.getWrongAnswer1().getText());
        button_wrong2.setText(question.getWrongAnswer2().getText());
        button_wrong3.setText(question.getWrongAnswer3().getText());
        // TODO add Book name, Movie name, etc

        // set question info
        textView_questionInfo.setText(String.format("Book %d, Chapter %d", question.getBook(), question.getChapter()));

        /*    timer = new CountDownTimer(Utils.CONSTANTS.MILLIS_PER_QUESTION, Utils.CONSTANTS.MILLIS_UPDATE_FREQUENCY) {

                private boolean finished = false;
                private int millisLeft = Utils.CONSTANTS.MILLIS_PER_QUESTION;

                public void onTick(long millisLeft) {

                    this.millisLeft = (int) millisLeft;

                    if ((millisLeft % 1000) >= 500) {
                        Log.d("onTick", "millisLeft = " + millisLeft + ", seconds left = " + ((millisLeft + 1000)/ 1000));
                        textView_questionInfo.setText("Seconds remaining: " + ((millisLeft + 1000)/ 1000));
                        progressBar.setProgress((int) (millisLeft + 1000)/ 1000);
                    } else {
                        Log.d("onTick", "millisLeft = " + millisLeft + ", seconds left = " + (millisLeft/ 1000));
                        textView_questionInfo.setText("Seconds remaining: " + (millisLeft / 1000));
                        progressBar.setProgress((int) (millisLeft / 1000));
                    }
                }

                public void onFinish() {
                    Log.d("onFinish called, ", "finished: " + finished + ", millisLeft: " + millisLeft);
                    if (finished || millisLeft > 0) return;
                    if (getParentFragment()!= null && getParentFragment().isDetached()) return;
                    textView_questionInfo.setText("TIME UP!");
                    textView_questionInfo.setTextColor(getResources().getColor(R.color.red));

                    Log.e(TAG, "TIME UP on question: " + question.getQuestionText());
                    if (activity.isActive) activity.displayNextQuestion();
                    else Log.e(TAG, "QuizActivity isActive = " + activity.isActive);
                    cancel();
                    finished = true;
                }
            }.start();
            Log.d(TAG, "Created new timer");*/

        setOnClickListeners();
    }

    private void setOnClickListeners() {
        button_correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO store "correctness"
                quizManager.questionComplete(question, question.getCorrectAnswer());
                //        timer.onFinish();
            }
        });

        button_wrong1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO store "incorrectness"
                quizManager.questionComplete(question, question.getWrongAnswer1());
            }
        });

        button_wrong2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO store "incorrectness"
                quizManager.questionComplete(question, question.getWrongAnswer2());
                //        timer.onFinish();
            }
        });

        button_wrong3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO store "incorrectness"
                quizManager.questionComplete(question, question.getWrongAnswer3());
                //        timer.onFinish();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_quiz, container, false);
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        //        timer.onFinish();
    }
}
